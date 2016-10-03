package it.ci.SimilarityMeasures.MI;

import it.ci.DistanceMatrix.DistanceMatrix;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.AbstractSimilarity;

import JavaMI.MutualInformation;

/**
 * Created by eracle on 03/10/16.
 */
public class MutualInformationSimilarity extends AbstractSimilarity {
    @Override
    public double measure(Instance x, Instance y) {
        DoubleArrayInstance first = (DoubleArrayInstance)x;
        DoubleArrayInstance second = (DoubleArrayInstance)y;

        return MutualInformation.calculateMutualInformation(first.array, second.array);
    }

    public double measure(double[] x, double[] y){
        return this.measure(new DoubleArrayInstance(x,0), new DoubleArrayInstance(y,1));
    }
}
