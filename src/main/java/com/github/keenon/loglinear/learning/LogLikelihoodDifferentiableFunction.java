package com.github.keenon.loglinear.learning;

import com.github.keenon.loglinear.inference.CliqueTree;
import com.github.keenon.loglinear.model.ConcatVector;
import com.github.keenon.loglinear.model.GraphicalModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by keenon on 8/23/15.
 * <p>
 * Generates (potentially noisy, no promises about exactness) gradients from a batch of examples that were provided to
 * the system.
 */
public class LogLikelihoodDifferentiableFunction extends AbstractDifferentiableFunction<GraphicalModel> {
  // This sets a gold observation for a model to use as training gold data
  public static final String VARIABLE_TRAINING_VALUE = "learning.LogLikelihoodDifferentiableFunction.VARIABLE_TRAINING_VALUE";

  /**
   * Gets a summary of the log-likelihood of a singe model at a point
   * <p>
   * It assumes that the models have observations for training set as metadata in
   * LogLikelihoodDifferentiableFunction.OBSERVATION_FOR_TRAINING. The models can also have observations fixed in
   * CliqueTree.VARIABLE_OBSERVED_VALUE, but these will be considered fixed and will not be learned against.
   *
   * @param model   the model to find the log-likelihood of
   * @param weights the weights to use
   * @return the gradient and value of the function at that point
   */
  @Override
  public double getSummaryForInstance(GraphicalModel model, ConcatVector weights, ConcatVector gradient) {
    double logLikelihood = 0.0;

    CliqueTree.MarginalResult result = new CliqueTree(model, weights).calculateMarginals();

    // Select just the VectorFactors, since those are the only ones that generate gradients

    List<GraphicalModel.VectorFactor> vectorFactors = new ArrayList<>();
    for (GraphicalModel.Factor factor : model.factors) {
      if (factor instanceof GraphicalModel.VectorFactor) {
        vectorFactors.add((GraphicalModel.VectorFactor)factor);
      }
    }

    // Cache everything in preparation for multiple redundant requests for feature vectors

    for (GraphicalModel.VectorFactor factor : vectorFactors) {
      factor.featuresTable.cacheVectors();
    }

    // Subtract log partition function

    logLikelihood -= Math.log(result.partitionFunction);

    // Quit if we have an infinite partition function

    if (Double.isInfinite(logLikelihood)) return 0.0;

    // Add the determined assignment by training values

    for (GraphicalModel.VectorFactor factor : vectorFactors) {
      // Find the assignment, taking both fixed and training observed variables into account
      int[] assignment = new int[factor.neigborIndices.length];
      for (int i = 0; i < assignment.length; i++) {
        int deterministicValue = getDeterministicAssignment(result.marginals[factor.neigborIndices[i]]);
        if (deterministicValue != -1) {
          assignment[i] = deterministicValue;
        } else {
          int trainingObservation = Integer.parseInt(model.getVariableMetaDataByReference(factor.neigborIndices[i]).get(LogLikelihoodDifferentiableFunction.VARIABLE_TRAINING_VALUE));
          assignment[i] = trainingObservation;
        }
        if (assignment[i] < 0 || assignment[i] >= factor.featuresTable.getDimensions()[i]) {
          throw new IllegalStateException("Model isn't fully or correctly labeled. Have that variable " + i + " has assignment " + assignment[i] + " (-1 means no label was present).\n" +
              "To solve this problem, make sure you set model.getVariableMetaDataByReference(i).put(LogLikelihoodDifferentiableFunction.VARIABLE_TRAINING_VALUE, \"\"+variableTrainingLabel) for all the" +
              " variables represented in the model before using it as a training example.");
        }
      }
      ConcatVector features = factor.featuresTable.getAssignmentValue(assignment).get();
      // Add the log-likelihood from this observation to the log-likelihood
      logLikelihood += features.dotProduct(weights);
      // Add the vector from this observation to the gradient
      gradient.addVectorInPlace(features, 1.0);
    }

    // Take expectations over features given marginals
    // NOTE: This is extremely expensive. Not sure what to do about that

    for (GraphicalModel.VectorFactor factor : vectorFactors) {
      // OPTIMIZATION:
      // Rather than use the standard iterator, which creates lots of int[] arrays on the heap, which need to be GC'd,
      // we use the fast version that just mutates one array. Since this is read once for us here, this is ideal.
      Iterator<int[]> fastPassByReferenceIterator = factor.featuresTable.fastPassByReferenceIterator();
      int[] assignment = fastPassByReferenceIterator.next();
      while (true) {
        // calculate assignment prob
        double assignmentProb = result.jointMarginals.get(factor).getAssignmentValue(assignment);
        // subtract this feature set, weighted by the probability of the assignment
        if (assignmentProb > 0) {
          gradient.addVectorInPlace(factor.featuresTable.getAssignmentValue(assignment).get(), -assignmentProb);
        }
        // This mutates the assignment[] array, rather than creating a new one
        if (fastPassByReferenceIterator.hasNext()) fastPassByReferenceIterator.next();
        else break;
      }
    }

    // Uncache everything, now that the computations have completed

    for (GraphicalModel.VectorFactor factor : vectorFactors) {
      factor.featuresTable.releaseCache();
    }

    return logLikelihood;
  }

  /**
   * Finds the deterministic assignment forced by a distribution, or if none exists returns -1
   *
   * @param distribution the potentially deterministic distribution
   * @return the assignment given by the distribution with probability 1, if one exists, else -1
   */
  private static int getDeterministicAssignment(double[] distribution) {
    int assignment = -1;
    for (int i = 0; i < distribution.length; i++) {
      if (distribution[i] == 1.0) {
        if (assignment == -1) assignment = i;
        else return -1;
      } else if (distribution[i] != 0.0) return -1;
    }
    return assignment;
  }
}
