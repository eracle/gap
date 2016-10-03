package it.ci.SimilarityMeasures.MI;

import it.ci.JavaMlAdaptors.NotAVectorInstance;
import net.sf.javaml.core.Instance;

import java.util.Arrays;

/**
 * Created by eracle on 03/10/16.
 */
public class DoubleArrayInstance extends NotAVectorInstance {

    public double[] array;

    public int id;

    public DoubleArrayInstance(double[] array, int id){
        this.array=array;
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public Instance copy() {
        return new DoubleArrayInstance(this.array.clone(),this.id);
    }

}
