package it.ci.DistanceMatrix;


import it.ci.SimilarityMeasures.MI.DoubleArrayInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.AbstractSimilarity;
import weka.core.Instances;

import java.util.Arrays;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Is a distance matrix based on mutual information.
 * Created by eracle on 21/09/16.
 */
public class DistanceMatrix {

    private static final Logger LOGGER = Logger.getLogger( DistanceMatrix.class.getName() );
    static{
        LOGGER.setLevel(Level.INFO);
    }

    public double[][] distanceMatrix;


    /**
     * Constructor which compute the distance matrix betweeen the attributes of the instances passed.
     * @param data
     * @return
     */
    public DistanceMatrix(Instances data, AbstractSimilarity sim){
        double[][] similarityMatrix = new double[data.numAttributes()][data.numAttributes()];

        double max_similarity = 0;
        // Computing the similarity matrix from the mutual information between couples of attributes

        for(int i = 0; i<data.numAttributes();i++) {
            LOGGER.log( Level.INFO, "Computing mutual information values of the attribute: {0}",data.attribute(i).name());

            double[] first = data.attributeToDoubleArray(i);

            LOGGER.log(Level.FINE,
                    "Attribute's values:" +
                            "\n" + Arrays.toString(first));


            for (int j = 0; j < data.numAttributes(); j++) {

                Object[] log_params = new Object[2];
                log_params[0] = data.attribute(i).name();
                log_params[1] = data.attribute(j).name();
                LOGGER.log( Level.INFO, "Computing mutual information between the attributes: {0}, {1}", log_params);

                double [] second = data.attributeToDoubleArray(j);
                LOGGER.log(Level.FINE,
                        "Attribute's values:" +
                                "\n" + Arrays.toString(second));

                DoubleArrayInstance x = new DoubleArrayInstance(first,i);
                DoubleArrayInstance y = new DoubleArrayInstance(second,j);

                double mi = sim.measure(x, y);

                similarityMatrix[i][j] = mi;
                if(max_similarity < mi)
                    max_similarity = mi;

                LOGGER.log( Level.FINER, "Mutual Information similarity: {0}", similarityMatrix[i][j]);
            }




        }

        // Now I must normalize the values in the similarity matrix in order to obtain a distance matrix
        // as this posts points out:
        // http://stackoverflow.com/questions/4064630/how-do-i-convert-between-a-measure-of-similarity-and-a-measure-of-difference-di

        this.distanceMatrix = new double[data.numAttributes()][data.numAttributes()];
        for(int i = 0; i<data.numAttributes();i++) {
            for (int j = 0; j < data.numAttributes(); j++) {
                if(i<j) {
                    this.distanceMatrix[i][j] = 1.0 - (similarityMatrix[i][j] / max_similarity);
                }else if ( i == j ){
                    this.distanceMatrix[i][j] = 0.0 ;
                }else{ // i>j
                    this.distanceMatrix[i][j] = this.distanceMatrix[j][i];
                }

            }
        }
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        Formatter formatter = new Formatter(ret);

        for (int i = 0; i < this.distanceMatrix.length; i++) {
            for (int j = 0; j < this.distanceMatrix[i].length; j++) {
                formatter.format("%.2f\t", this.distanceMatrix[i][j]);
            }
            ret.append("\n");
        }
        return ret.toString();
    }


}
