package it.ci.JavaMlAdaptors;

import net.sf.javaml.clustering.KMedoids;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by eracle on 09/09/16.
 */
public class DMKMedoids extends KMedoids{

    private static final Logger LOGGER = Logger.getLogger( KMedoids.class.getName() );
    static{
        LOGGER.setLevel(Level.OFF);
    }

    public DMKMedoids(int numberOfClusters, int maxIterations, DistanceMeasure dm) {
        super(numberOfClusters, maxIterations, dm);
    }


    @Override
    /**
     *
     * @param medoids
     *            the current set of cluster medoids, will be modified to fit
     *            the new assignment
     * @param assignment
     *            the new assignment of all instances to the different medoids
     * @param output
     *            the cluster output, this will be modified at the end of the
     *            method
     * @return the
     */
    public boolean recalculateMedoids(int[] assignment, Instance[] medoids,
                                      Dataset[] output, Dataset data) {
        LOGGER.log( Level.INFO, "Recomputing Medoids phase");

        boolean changed = false;
        for (int i = 0; i < numberOfClusters; i++) {
            output[i] = new DefaultDataset();
            for (int j = 0; j < assignment.length; j++) {
                if (assignment[j] == i) {
                    output[i].add(data.instance(j));
                }
            }
            if (output[i].size() == 0) { // new random, empty medoid
                medoids[i] = data.instance(rg.nextInt(data.size()));
                changed = true;
            } else {
                Instance oldMedoid = medoids[i];

                // I have to assign to medoids[i] a new medoid Instance

                double smallest_mean_distance_value = Double.MAX_VALUE;
                // I save the cluster's element which have
                // the smallest mean distance value to all the others cluster's
                // element
                for (int j = 0; j < output[i].size(); j++) {
                    // for each cluster's element I compute
                    // the mean distance to all the others cluster's elements
                    double mean_distance_j_sum = 0;

                    for (int k = 0; k < output[i].size(); k++)
                        mean_distance_j_sum += dm.measure(output[i].get(j),output[i].get(k));

                    if (mean_distance_j_sum < smallest_mean_distance_value) {
                        smallest_mean_distance_value = mean_distance_j_sum;
                        medoids[i] = output[i].get(j);
                    }
                }

                if (!medoids[i].equals(oldMedoid))
                    changed = true;
            }
        }
        DMDataset data_cast = (DMDataset) data;
        data_cast.medoids = medoids;

        return changed;
    }

}
