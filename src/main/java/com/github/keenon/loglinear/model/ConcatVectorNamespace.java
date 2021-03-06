package com.github.keenon.loglinear.model;

import com.carrotsearch.hppc.*;
import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.github.keenon.loglinear.ConcatVectorNamespaceProto;

import java.io.*;
import java.util.*;

/**
 * Created by keenon on 10/20/15.
 *
 * This is a wrapper function to keep a namespace of namespace of recognized features, so that building a set of
 * ConcatVectors for featurizing a model is easier and more intuitive. It's actually quite simple, and threadsafe.
 */
public class ConcatVectorNamespace implements Serializable {
    /** A serialversionuid so we can save this robustly */
    private static final long serialVersionUID = 8993759749908519745L;

    // This is the name of a feature that we expect all weight vectors to set to 1.0
    static final String ALWAYS_ONE_FEATURE = "__lense__.ALWAYS_ONE";

    final ObjectIntMap<String> featureToIndex = new ObjectIntHashMap<>();
    final ObjectObjectMap<String, ObjectIntMap<String>> sparseFeatureIndex = new ObjectObjectHashMap<>();
    final ObjectObjectMap<String, IntObjectMap<String>> reverseSparseFeatureIndex = new ObjectObjectHashMap<>();

    /**
     * Creates a new vector that is appropriately sized to accommodate all the features that have been named so far.
     * @return a new, empty ConcatVector
     */
    public ConcatVector newVector() {
        return new ConcatVector(featureToIndex.size());
    }

    public ConcatVector newWeightsVector() {
        return newWeightsVector(true);
    }

    /**
     * This constructs a fresh vector that is sized correctly to accommodate all the known sparse values for vectors
     * that are possibly sparse.
     *
     * @param presize a flag for whether or not to create all the dense double arrays for our sparse features
     *
     * @return a new, internally correctly sized ConcatVector that will work correctly as weights for features from
     *         this namespace;
     */
    public ConcatVector newWeightsVector(boolean presize) {
        ConcatVector vector = new ConcatVector(featureToIndex.size());
        if (presize) {
            for (ObjectCursor<String> s : sparseFeatureIndex.keys()) {
                int size = sparseFeatureIndex.get(s.value).size();
                vector.setDenseComponent(ensureFeature(s.value), new double[size]);
            }
        }
        setAlwaysOneFeature(vector, 1);
        return vector;
    }

    /**
     * An optimization, this lets clients inform the ConcatVectorNamespace of how many features to expect, so
     * that we can avoid resizing ConcatVectors.
     * @param featureName the feature to add to our index
     */
    public int ensureFeature(String featureName) {
        int feature = featureToIndex.getOrDefault(featureName, -1);
        if (feature == -1) {
            synchronized (featureToIndex) {
                feature = featureToIndex.getOrDefault(featureName, -1);
                if (feature == -1) {
                    feature = featureToIndex.size();
                    featureToIndex.put(featureName, feature);
                }
            }
        }
        return feature;
    }

    /**
     * An optimization, this lets clients inform the ConcatVectorNamespace of how many sparse feature components to
     * expect, again so that we can avoid resizing ConcatVectors.
     * @param featureName the feature to use in our index
     * @param index the sparse value to ensure is available
     */
    public int ensureSparseFeature(String featureName, String index) {
        ensureFeature(featureName);
        ObjectIntMap<String> sparseIndex = sparseFeatureIndex.get(featureName);
        IntObjectMap<String> reverseSparseIndex = reverseSparseFeatureIndex.get(featureName);
        if (sparseIndex == null || reverseSparseIndex == null) {
            synchronized (sparseFeatureIndex) {
                sparseIndex = sparseFeatureIndex.get(featureName);
                reverseSparseIndex = reverseSparseFeatureIndex.get(featureName);
                if (sparseIndex == null || reverseSparseIndex == null) {
                    sparseIndex = new ObjectIntHashMap<>();
                    reverseSparseIndex = new IntObjectHashMap<>();
                    sparseFeatureIndex.put(featureName, sparseIndex);
                    reverseSparseFeatureIndex.put(featureName, reverseSparseIndex);
                }
            }
        }
        Integer rtn = sparseIndex.getOrDefault(index, -1);
        if (rtn == -1) {
            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (sparseIndex) {
                rtn = sparseIndex.getOrDefault(index, -1);
                if (rtn == -1) {
                    rtn = sparseIndex.size();
                    reverseSparseIndex.put(rtn, index);
                    sparseIndex.put(index, rtn);
                }
            }
        }
        return rtn;
    }

    /**
     * Sets the special "always one" feature slot to something. For weight vectors, this should always be set to 1.0.
     * For everyone else, this can be set to whatever people want.
     *
     * @param vector the vector we'd like to set
     * @param value the value we'd like to set it to
     */
    public void setAlwaysOneFeature(ConcatVector vector, double value) {
        setDenseFeature(vector, ALWAYS_ONE_FEATURE, new double[]{value});
    }

    /**
     * This adds a dense feature to a vector, setting the appropriate component of the given vector to the passed in
     * value.
     * @param vector the vector
     * @param featureName the feature whose value to set
     * @param value the value we want to set this vector to
     */
    public void setDenseFeature(ConcatVector vector, String featureName, double[] value) {
        vector.setDenseComponent(ensureFeature(featureName), value);
    }

    /**
     * This adds a sparse feature to a vector, setting the appropriate component of the given vector to the passed in
     * value.
     * @param vector the vector
     * @param featureName the feature whose value to set
     * @param index the index of the one-hot vector to set, as a string, which we will translate into a mapping
     * @param value the value we want to set this one-hot index to
     */
    public void setSparseFeature(ConcatVector vector, String featureName, String index, double value) {
        vector.setSparseComponent(ensureFeature(featureName), ensureSparseFeature(featureName, index), value);
    }

    /**
     * This adds a sparse set feature to a vector, setting the appropriate components of the given vector to the passed
     * in value.
     * @param vector the vector
     * @param featureName the feature whose value to set
     * @param sparseFeatures the indices we wish to set, and their values
     */
    public void setSparseFeature(ConcatVector vector, String featureName, Map<String,Double> sparseFeatures) {
        int[] indices = new int[sparseFeatures.size()];
        double[] values = new double[sparseFeatures.size()];
        int offset = 0;
        for (String index : sparseFeatures.keySet()) {
            indices[offset] = ensureSparseFeature(featureName, index);
            values[offset] = sparseFeatures.get(index);
            offset++;
        }
        vector.setSparseComponent(ensureFeature(featureName), indices, values);
    }

    /**
     * This adds a sparse set feature to a vector, setting the appropriate components of the given vector to the passed
     * in value.
     * @param vector the vector
     * @param featureName the feature whose value to set
     * @param sparseFeatures the indices we wish to set, whose values will all be set to 1.0
     */
    public void setSparseFeature(ConcatVector vector, String featureName, Collection<String> sparseFeatures) {
        int[] indices = new int[sparseFeatures.size()];
        double[] values = new double[sparseFeatures.size()];
        int offset = 0;
        for (String index : sparseFeatures) {
            indices[offset] = ensureSparseFeature(featureName, index);
            values[offset] = 1.0;
            offset++;
        }
        vector.setSparseComponent(ensureFeature(featureName), indices, values);
    }

    /**
     * Writes the protobuf version of this vector to a stream. reversible with readFromStream().
     *
     * @param stream the output stream to write to
     * @throws IOException passed through from the stream
     */
    public void writeToStream(OutputStream stream) throws IOException {
        getProtoBuilder().build().writeDelimitedTo(stream);
    }

    /**
     * Static function to deserialize a concat vector from an input stream.
     *
     * @param stream the stream to read from, assuming protobuf encoding
     * @return a new concat vector
     * @throws IOException passed through from the stream
     */
    public static ConcatVectorNamespace readFromStream(InputStream stream) throws IOException {
        return readFromProto(ConcatVectorNamespaceProto.ConcatVectorNamespace.parseDelimitedFrom(stream));
    }

    /**
     * @return a Builder for proto serialization
     */
    public ConcatVectorNamespaceProto.ConcatVectorNamespace.Builder getProtoBuilder() {
        ConcatVectorNamespaceProto.ConcatVectorNamespace.Builder m = ConcatVectorNamespaceProto.ConcatVectorNamespace.newBuilder();

        // Add the outer layer features
        for (ObjectCursor<String> feature : featureToIndex.keys()) {
            ConcatVectorNamespaceProto.ConcatVectorNamespace.FeatureToIndexComponent.Builder component = ConcatVectorNamespaceProto.ConcatVectorNamespace.FeatureToIndexComponent.newBuilder();

            component.setKey(feature.value);
            component.setData(featureToIndex.getOrDefault(feature.value, -1));

            m.addFeatureToIndex(component);
        }

        for (ObjectCursor<String> feature : sparseFeatureIndex.keys()) {
            ConcatVectorNamespaceProto.ConcatVectorNamespace.SparseFeatureIndex.Builder sparseFeature = ConcatVectorNamespaceProto.ConcatVectorNamespace.SparseFeatureIndex.newBuilder();

            sparseFeature.setKey(feature.value);
            for (ObjectCursor<String> sparseFeatureName : sparseFeatureIndex.get(feature.value).keys()) {
                ConcatVectorNamespaceProto.ConcatVectorNamespace.FeatureToIndexComponent.Builder component = ConcatVectorNamespaceProto.ConcatVectorNamespace.FeatureToIndexComponent.newBuilder();
                component.setKey(sparseFeatureName.value);
                component.setData(sparseFeatureIndex.get(feature.value).getOrDefault(sparseFeatureName.value, -1));
                sparseFeature.addFeatureToIndex(component);
            }

            m.addSparseFeatureIndex(sparseFeature);
        }

        return m;
    }

    /**
     * Recreates an in-memory concat vector object from a Proto serialization.
     *
     * @param m the concat vector proto
     * @return an in-memory concat vector object
     */
    public static ConcatVectorNamespace readFromProto(ConcatVectorNamespaceProto.ConcatVectorNamespace m) {
        ConcatVectorNamespace namespace = new ConcatVectorNamespace();

        for (ConcatVectorNamespaceProto.ConcatVectorNamespace.FeatureToIndexComponent component : m.getFeatureToIndexList()) {
            namespace.featureToIndex.put(component.getKey(), component.getData());
        }

        for (ConcatVectorNamespaceProto.ConcatVectorNamespace.SparseFeatureIndex sparseFeature : m.getSparseFeatureIndexList()) {
            String key = sparseFeature.getKey();
            ObjectIntMap<String> sparseMap = new ObjectIntHashMap<>();
            IntObjectMap<String> reverseSparseMap = new IntObjectHashMap<>();
            for (ConcatVectorNamespaceProto.ConcatVectorNamespace.FeatureToIndexComponent component : sparseFeature.getFeatureToIndexList()) {
                sparseMap.put(component.getKey(), component.getData());
                reverseSparseMap.put(component.getData(), component.getKey());
            }
            namespace.sparseFeatureIndex.put(key, sparseMap);
            namespace.reverseSparseFeatureIndex.put(key, reverseSparseMap);
        }

        return namespace;
    }

    /**
     * WARNING: this will change if your namespace registers any new feature names
     * ANOTHER WARNING: if you have a bunch of sparse components, this will be huge and mostly zeros
     *
     * That said, if you really know what you're doing, here's how you convert a ConcatVector into a flat array, if for
     * example you want to use it with another library.
     *
     * @param vector the vector to convert to a double array
     * @return a dense array
     */
    /*
    public double[] dangerousFlatArrayConversion(ConcatVector vector) {
        Map<Integer, String> indexToFeature = new HashMap<>();
        for (String key : featureToIndex.keySet()) {
            indexToFeature.put(featureToIndex.get(key), key);
        }

        int numComponents = vector.getNumberOfComponents();
        int totalSize = 0;
        for (int i = 0; i < numComponents; i++) {
            if (!vector.isComponentSparse(i)) {
                totalSize += vector.getDenseComponent(i).length;
            }
            if (indexToFeature.containsKey(i)) {
                if (sparseFeatureIndex.containsKey(indexToFeature.get(i))) {
                    totalSize += sparseFeatureIndex.get(indexToFeature.get(i)).size();
                }
            }
        }
    }
    */

    /**
     * This prints out a ConcatVector by mapping to the namespace, to make debugging learning algorithms easier.
     *
     * @param vector the vector to print
     * @param bw the output stream to write to
     */
    public void debugVector(ConcatVector vector, BufferedWriter bw) throws IOException {
        List<String> features = new ArrayList<>();
        Map<String, List<Integer>> sortedFeatures = new HashMap<>();

        for (ObjectCursor<String> key : featureToIndex.keys()) {
            features.add(key.value);
            int i = featureToIndex.getOrDefault(key.value,  -1);

            List<Integer> featureIndices = new ArrayList<>();
            if (vector.isComponentSparse(i)) {
                int[] indices = vector.getSparseIndices(i);
                for (int j : indices) {
                    featureIndices.add(j);
                }
            }
            else {
                double[] arr = vector.getDenseComponent(i);
                for (int j = 0; j < arr.length; j++) {
                    featureIndices.add(j);
                }
            }
            featureIndices.sort((a,b) -> {
                if (Math.abs(vector.getValueAt(i, a)) < Math.abs(vector.getValueAt(i, b))) {
                    return 1;
                }
                else if (Math.abs(vector.getValueAt(i, a)) > Math.abs(vector.getValueAt(i, b))) {
                    return -1;
                }
                else {
                    return 0;
                }
            });

            sortedFeatures.put(key.value, featureIndices);
        }

        features.sort((a,b) -> {
            double bestAValue = sortedFeatures.get(a).size() == 0 ? 0.0 : Math.abs(vector.getValueAt(featureToIndex.getOrDefault(a, -1), sortedFeatures.get(a).get(0)));
            double bestBValue = sortedFeatures.get(b).size() == 0 ? 0.0 : Math.abs(vector.getValueAt(featureToIndex.getOrDefault(b, -1), sortedFeatures.get(b).get(0)));
            if (bestAValue < bestBValue) {
                return 1;
            }
            else if (bestAValue > bestBValue) {
                return -1;
            }
            else return 0;
        });

        for (String key : features) {
            bw.write("FEATURE: \""+key);
            bw.write("\"\n");
            for (int j : sortedFeatures.get(key)) {
                debugFeatureValue(key, j, vector, bw);
            }
        }
        
        // Flush the writer
        bw.flush();
    }

    /**
     * This writes a feature's individual value, using the human readable name if possible, to a StringBuilder
     */
    private void debugFeatureValue(String feature, int index, ConcatVector vector, BufferedWriter bw) throws IOException {
        bw.write("\t");
        if (sparseFeatureIndex.containsKey(feature) && sparseFeatureIndex.get(feature).values().contains(index)) {
            // we can map this index to an interpretable string, so we do
            bw.write("SPARSE VALUE \"");
            bw.write(reverseSparseFeatureIndex.get(feature).get(index));
            bw.write("\"");
        }
        else {
            // we can't map this to a useful string, so we default to the number
            bw.write(Integer.toString(index));
        }
        bw.write(": ");
        bw.write(Double.toString(vector.getValueAt(featureToIndex.getOrDefault(feature, -1), index)));
        bw.write("\n");
    }
}
