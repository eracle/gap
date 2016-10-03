package it.ci.JavaMlAdaptors;

import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;

import java.util.ArrayList;

/**
 * Created by eracle on 09/09/16.
 */
public class DMDataset extends DefaultDataset{

    /**
     * Create a customized dataset from the distance matrix passed
     * @param matrix    distance matrix
     */
    public DMDataset(double[][] matrix) {

        for(int i=0; i<matrix.length;i++) {
            this.add(new DMInstance(i));
        }
    }

    public Instance[] medoids;

}
