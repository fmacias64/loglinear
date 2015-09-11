package com.github.keenon.loglinear;

import com.github.keenon.loglinear.learning.AbstractBatchOptimizer;
import com.github.keenon.loglinear.learning.LogLikelihoodFunction;
import com.github.keenon.loglinear.inference.CliqueTree;
import com.github.keenon.loglinear.learning.BacktrackingAdaGradOptimizer;
import com.github.keenon.loglinear.model.ConcatVector;
import com.github.keenon.loglinear.model.GraphicalModel;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by keenon on 8/26/15.
 *
 * This loads the CoNLL dataset and 300 dimensional google word embeddings and trains a model on the data using binary
 * and unary factors. This is a nice explanation of why it is key to have ConcatVector as a datastructure, since there
 * is no need to specify the number of words in advance anywhere, and data structures will happily resize with a minimum
 * of GCC wastage.
 */
public class CoNLLBenchmark {
    Map<String, double[]> embeddings = new HashMap<>();

    public static void main(String[] args) throws Exception {
        new CoNLLBenchmark().benchmarkOptimizer();
    }

    public void benchmarkOptimizer() throws Exception {
        String prefix = System.getProperty("user.dir")+"/";
        if (prefix.endsWith("platform")) prefix = prefix+"learning/";

        List<CoNLLSentence> train = getSentences(prefix + "src/benchmark/data/conll.iob.4class.train");
        List<CoNLLSentence> testA = getSentences(prefix + "src/benchmark/data/conll.iob.4class.testa");
        List<CoNLLSentence> testB = getSentences(prefix + "src/benchmark/data/conll.iob.4class.testb");

        List<CoNLLSentence> allData = new ArrayList<>();
        allData.addAll(train);
        allData.addAll(testA);
        allData.addAll(testB);

        Set<String> tagsSet = new HashSet<>();
        for (CoNLLSentence sentence : allData) for (String nerTag : sentence.ner) tagsSet.add(nerTag);
        List<String> tags = new ArrayList<>();
        tags.addAll(tagsSet);

        embeddings = getEmbeddings(prefix + "src/benchmark/data/google-300-trimmed.ser.gz", allData);

        System.err.println("Making the training set...");

        int trainSize = train.size();
        GraphicalModel[] trainingSet = new GraphicalModel[trainSize];
        for (int i = 0; i < trainSize; i++) {
            if (i % 10 == 0) {
                System.err.println(i+"/"+trainSize);
            }
            trainingSet[i] = generateSentenceModel(train.get(i), tags);
        }

        System.err.println("Training system...");

        AbstractBatchOptimizer opt = new BacktrackingAdaGradOptimizer();

        // This training call is basically what we want the benchmark for. It should take 99% of the wall clock time
        ConcatVector weights = opt.optimize(trainingSet, new LogLikelihoodFunction(), new ConcatVector(0), 0.1);

        System.err.println("Testing system...");

        // Evaluation method lifted from the CoNLL 2004 perl script

        Map<String,Double> correctChunk = new HashMap<>();
        Map<String,Double> foundCorrect = new HashMap<>();
        Map<String,Double> foundGuessed = new HashMap<>();
        double correct = 0.0;
        double total = 0.0;

        for (CoNLLSentence sentence : testA) {
            GraphicalModel model = generateSentenceModel(sentence, tags);
            int[] guesses = new CliqueTree(model, weights).calculateMAP();
            String[] nerGuesses = new String[guesses.length];
            for (int i = 0; i < guesses.length; i++) {
                nerGuesses[i] = tags.get(guesses[i]);
                if (nerGuesses[i].equals(sentence.ner.get(i))) {
                    correct++;
                    correctChunk.put(nerGuesses[i], correctChunk.getOrDefault(nerGuesses[i], 0.) + 1);
                }
                total++;
                foundCorrect.put(sentence.ner.get(i), foundCorrect.getOrDefault(sentence.ner.get(i), 0.) + 1);
                foundGuessed.put(nerGuesses[i], foundGuessed.getOrDefault(nerGuesses[i], 0.) + 1);
            }
        }

        System.err.println("\nSystem results:\n");

        System.err.println("Accuracy: "+(correct / total)+"\n");

        for (String tag : tags) {
            double precision = foundGuessed.getOrDefault(tag, 0.0) == 0 ? 0.0 : correctChunk.getOrDefault(tag, 0.0) / foundGuessed.get(tag);
            double recall = foundCorrect.getOrDefault(tag, 0.0) == 0 ? 0.0 : correctChunk.getOrDefault(tag, 0.0) / foundCorrect.get(tag);
            double f1 = (precision * recall * 2) / (precision + recall);
            System.err.println(tag);
            System.err.println("\tP:"+precision);
            System.err.println("\tR:"+recall);
            System.err.println("\tF1:"+f1);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // GENERATING MODELS
    ////////////////////////////////////////////////////////////////////////////////////////////

    public GraphicalModel generateSentenceModel(CoNLLSentence sentence, List<String> tags) {
        GraphicalModel model = new GraphicalModel();

        for (int i = 0; i < sentence.token.size(); i++) {

            // Add the training label

            model.getVariableMetaDataByReference(i).put(LogLikelihoodFunction.VARIABLE_TRAINING_VALUE, ""+tags.indexOf(sentence.ner.get(i)));

            final int iFinal = i;

            // Add the unary factor

            GraphicalModel.Factor f = model.addFactor(new int[]{iFinal}, new int[]{tags.size()}, (assignment) -> {

                // This is the anonymous function that generates a feature vector for each assignment to the unary
                // factor

                String tag = tags.get(assignment[0]);

                ConcatVector features = new ConcatVector(tags.size());
                if (embeddings.get(sentence.token.get(iFinal)) != null) {
                    features.setDenseComponent(assignment[0], embeddings.get(sentence.token.get(iFinal)));
                }

                return features;
            });

            assert(f.neigborIndices.length == 1);
            assert(f.neigborIndices[0] == iFinal);

            // If this is not the last variable, add a binary factor

            if (i < sentence.token.size() - 1) {
                GraphicalModel.Factor jf = model.addFactor(new int[]{iFinal, iFinal + 1}, new int[]{tags.size(), tags.size()}, (assignment) -> {

                    // This is the anonymous function that generates a feature vector for every joint assignment to the
                    // binary factor

                    String thisTag = tags.get(assignment[0]);
                    String nextTag = tags.get(assignment[1]);

                    ConcatVector features = new ConcatVector(2 * tags.size() * tags.size());

                    if (embeddings.containsKey(sentence.token.get(iFinal)) || embeddings.containsKey(sentence.token.get(iFinal + 1))) {

                        int index = assignment[0] * tags.size() + assignment[1];
                        if (embeddings.get(sentence.token.get(iFinal)) != null) {
                            features.setDenseComponent(index, embeddings.get(sentence.token.get(iFinal)));
                        }
                        if (embeddings.get(sentence.token.get(iFinal + 1)) != null) {
                            features.setDenseComponent((tags.size() * tags.size()) + index, embeddings.get(sentence.token.get(iFinal + 1)));
                        }

                    }

                    return features;
                });

                assert(jf.neigborIndices.length == 2);
                assert(jf.neigborIndices[0] == iFinal);
                assert(jf.neigborIndices[1] == iFinal + 1);
            }
        }

        assert(model.factors != null);
        for (GraphicalModel.Factor f : model.factors) {
            assert(f != null);
        }

        return model;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // LOADING DATA FROM FILES
    ////////////////////////////////////////////////////////////////////////////////////////////

    public static class CoNLLSentence {
        public List<String> token = new ArrayList<>();
        public List<String> ner = new ArrayList<>();

        public CoNLLSentence(List<String> token, List<String> ner) {
            this.token = token;
            this.ner = ner;
        }
    }

    public List<CoNLLSentence> getSentences(String filename) throws IOException {
        List<CoNLLSentence> sentences = new ArrayList<>();
        List<String> tokens = new ArrayList<>();
        List<String> ners = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filename));

        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length == 2) {
                tokens.add(parts[0]);
                ners.add(parts[1]);
                if (parts[0].equals(".")) {
                    sentences.add(new CoNLLSentence(tokens, ners));
                    tokens = new ArrayList<>();
                    ners = new ArrayList<>();
                }
            }
        }

        return sentences;
    }

    @SuppressWarnings("unchecked")
    public Map<String,double[]> getEmbeddings(String cacheFilename, List<CoNLLSentence> sentences) throws IOException, ClassNotFoundException {
        File f = new File(cacheFilename);
        Map<String,double[]> trimmedSet;

        if (!f.exists()) {
            trimmedSet = new HashMap<>();

            Map<String,double[]> massiveSet = loadEmbeddingsFromFile("../google-300.txt");
            System.err.println("Got massive embedding set size "+massiveSet.size());

            for (CoNLLSentence sentence : sentences) {
                for (String token : sentence.token) {
                    if (massiveSet.containsKey(token)) {
                        trimmedSet.put(token, massiveSet.get(token));
                    }
                }
            }
            System.err.println("Got trimmed embedding set size "+trimmedSet.size());

            f.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(cacheFilename)));
            oos.writeObject(trimmedSet);
            oos.close();

            System.err.println("Wrote trimmed set to file");
        }
        else {
            ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(cacheFilename)));
            trimmedSet = (Map<String,double[]>)ois.readObject();
        }

        return trimmedSet;
    }

    public Map<String,double[]> loadEmbeddingsFromFile(String filename) throws IOException {
        Map<String, double[]> embeddings = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(filename));

        int readLines = 0;

        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");

            if (parts.length == 302) {
                String token = parts[0];
                double[] embedding = new double[300];
                for (int i = 1; i < parts.length - 1; i++) {
                    embedding[i - 1] = Double.parseDouble(parts[i]);
                }
                embeddings.put(token, embedding);
            }

            readLines++;
            if (readLines % 10000 == 0) {
                System.err.println("Read "+readLines+" lines");
            }
        }

        return embeddings;
    }
}