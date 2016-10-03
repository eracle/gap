package it.ci.JavaMlAdaptors;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.AbstractDistance;

/**
 * Created by eracle on 09/09/16.
 */
public class DMDistance extends AbstractDistance {

    private double[][] distance_matrix;

    public DMDistance(double[][] distance_matrix){
        this.distance_matrix = distance_matrix;
    }

    public double measure(Instance instance, Instance instance1) {
        DMInstance i1 = (DMInstance) instance;
        DMInstance i2 = (DMInstance) instance1;

        return this.distance_matrix[i1.matrix_index][i2.matrix_index];
    }



}