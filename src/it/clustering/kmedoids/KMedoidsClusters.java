package it.clustering.kmedoids;

import it.clustering.Clusters;
import it.clustering.Instance;
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
public class KMedoidsClusters<I extends Instance> extends Clusters<I,KMedoidsCluster<I>>{

	/** Log4j logger used: */
	private static Logger log = Logger.getLogger(KMedoidsClusters.class);

	private DistanceFunction<I> distanceFunc;

	/**
	 * Builds clusters from the medoids passed; in the following way: Every
	 * cluster built is composed exactly by one medoid.
	 * 
	 * @param medoids
	 *            The list of medoids from which the clusters are built.
	 * @param distanceFunction
	 *            The distanceFunction used for the single cluster constructor.
	 */
	public KMedoidsClusters(List<I> medoids, DistanceFunction<I> distanceFunction) {
		super(new ArrayList<KMedoidsCluster<I>>(medoids.size()));
	
		log.debug("Clusters constructor medoids:\n" + medoids.toString());

		this.distanceFunc = distanceFunction;

		for (int i = 0; i < medoids.size(); i++) {
			clusters.add(new KMedoidsCluster<I>(medoids.get(i), distanceFunction));
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
	 * Add every element of the list passed to the closest cluster. The closeness is
	 * measured through the distance function passed to the constructor. If the
	 * element is already a medoid of one of the clusters passed then it will be
	 * not added to any cluster.
	 * 
	 * @param elements
	 *            The list of elements that will be assigned to the closest
	 *            cluster.
	 * @return The sum of the squared distance between each element and the
	 *         medoid of the cluster which was been assigned.
	 */
	public double assignToTheNearestCluster(List<I> elements) {
		double sum = 0;
		for (I element : elements) {
			// Let's scan all the clusters
			// for identify the closest medoid
			
			KMedoidsCluster<I> selectedCluster = clusters.get(0);
			I closestMedoid = selectedCluster.getMedoid();
			double closestMedoidDistance = this.distanceFunc.distance(element, closestMedoid);
			
			
			for (KMedoidsCluster<I> cluster : this.clusters) {
				
				I iterationMedoid = cluster.getMedoid();
				double iterationMedoidDistance = this.distanceFunc.distance(element, iterationMedoid);
				
				if(iterationMedoidDistance < closestMedoidDistance){
					selectedCluster = cluster;
					closestMedoid = iterationMedoid;
					closestMedoidDistance = iterationMedoidDistance;
				}
			}
			
			//adding the element to the closest cluster
			selectedCluster.addElement(element);
			
			// summing the squared of the distance
			sum +=Math.pow(closestMedoidDistance, 2);;
			
			
		}
		return sum;
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
	 * 
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
