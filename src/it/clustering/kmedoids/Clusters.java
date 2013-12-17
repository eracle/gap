package it.clustering.kmedoids;

import it.Instance;
import it.clustering.distanceFunction.DistanceFunction;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Represents a set of clusters used by k-medoids clustering algorithm.
 * 
 * @author Antonio Ercole De Luca
 * 
 * @param <I>
 *            The concrete type of the objects to be clustered.
 */
public class Clusters<I extends Instance> {

	/** Log4j logger used: */
	private static Logger log = Logger.getLogger(Clusters.class);

	private List<Cluster<I>> clusters;

	private DistanceFunction<I> distanceFunc;

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("Clusters:\n ");
		for (Cluster<I> elem : this.clusters) {
			ret.append(elem.toString());
		}
		return ret.toString();
	}

	/**
	 * Builds clusters from the medoids passed; in the following way: Every
	 * cluster built is composed exactly by one medoid.
	 * 
	 * @param medoids
	 *            The list of medoids from which the clusters are built.
	 * @param distanceFunction
	 *            The distanceFunction used for the single cluster constructor.
	 */
	public Clusters(List<I> medoids, DistanceFunction<I> distanceFunction) {

		log.debug("Clusters constructor medoids:\n" + medoids.toString());

		this.distanceFunc = distanceFunction;

		this.clusters = new ArrayList<Cluster<I>>(medoids.size());
		for (int i = 0; i < medoids.size(); i++) {
			clusters.add(new Cluster<I>(medoids.get(i), distanceFunction));
		}
	}

	/**
	 * Modifies the clusters letting be everyone of them a cluster with only the
	 * medoid element.
	 */
	public void stripClusters() {

		log.debug("Stripping clusters");

		for (int i = 0; i < clusters.size(); i++) {
			clusters.get(i).cleanCluster();
		}

		log.trace("Stripped clusters:\n" + this.toString());

	}

	/**
	 * Add the "element" passed to the closer cluster. The closeness is measured
	 * by the distance function passed in the constructor. If the element is
	 * already a medoid of one of the clusters passed then it will be not added
	 * to any cluster and the zero value is returned.
	 * 
	 * @param element
	 *            The element to be assigned to the closer cluster passed.
	 * @return The squared distance between the element passed and the medoid of
	 *         the cluster which is been assigned to. Zero if the element passed
	 *         is exactly one of the medoids of the clusters.
	 */
	public double addToNearest(I element) {

		// used to determine the closest medoid
		Cluster<I> min = null;
		double min_distance = Double.MAX_VALUE;

		// distance between the element and the i-th medoid
		double element_scatter = 0;

		// i don't need to insert an element that is already a medoids
		// because in the build phase i already put it on his cluster
		boolean notInsertedYet = true;

		for (int i = 0; (i < clusters.size()) && notInsertedYet; i++) {
			I ithMedoid = clusters.get(i).getMedoid();

			// I check if the element is a medoid
			if (element.equals(ithMedoid)) {
				notInsertedYet = false;
			} else {
				double distance = this.distanceFunc
						.distance(element, ithMedoid);

				if (distance == min_distance) {
					if (min.hashCode() < clusters.get(i).hashCode()) {
						min_distance = distance;
						min = clusters.get(i);
					}
				} else if (distance < min_distance) {
					min_distance = distance;
					min = clusters.get(i);
				}
			}
		}

		// Finally i must have a cluster in min variable
		// i put the element in the cluster with min distance
		if (notInsertedYet) {
			min.addElement(element);
			element_scatter = Math.pow(min_distance, 2);
			return element_scatter;
		} else
			return 0;
	}

	// TODO: write shadow clusters contract
	public boolean removeShadowClusters() {
		boolean someSwapHappened = false;
		for (int i = 0; i < clusters.size(); i++) {
			if (clusters.get(i).isShadow()) {
				for (int j = 0; j < clusters.size(); j++) {
					if (i != j) {
						if (clusters.get(j).isLessDistantOfTheFarestSwapThem(
								clusters.get(i))) {
							j = clusters.size();
							someSwapHappened = true;
							// TODO: verify, by using a test case, what happen
							// if there are more than one clusters that are good
							// swapping candidates, because I suppose there is a
							// bug in the absence of a continue statement here
						}
					}
				}
			}
		}
		return someSwapHappened;
	}

	/**
	 * Returns a list made by all the medoids of the clusters.
	 * @return A list of the medoids of the clusters.
	 */
	public List<I> getMedoids() {
		// new medoids will be part of clustering algorithm if
		// they pass the stop criterion phase
		List<I> new_medoids = new ArrayList<I>(this.clusters.size());
		for (int i = 0; i < clusters.size(); i++) {
			// for each cluster i compute the new medoid
			clusters.get(i).computeMedoid();
			new_medoids.add(i, clusters.get(i).getMedoid());
		}
		return new_medoids;
	}

}
