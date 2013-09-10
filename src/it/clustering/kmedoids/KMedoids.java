package it.clustering.kmedoids;

import it.Instance;
import it.clustering.ClusteringAlgorithm;
import it.clustering.distanceFunction.DistanceFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

public class KMedoids<I extends Instance> implements ClusteringAlgorithm {

	private static Logger log = Logger.getLogger("KMedoids");

	private static int MAX_ITERATIONS = 30;

	private static double MIN_CHANGED_MEDOIDS_PERCENTAGE = 0;

	private static double MIN_AVERAGE_DISTANCE_BETWEEN_MEDOIDS = .000000000001;

	private int k;

	private List<I> elements;

	private int iteration;

	private DistanceFunction<I> distanceFunction;

	public List<Cluster<I>> getClusters() {
		return clusters;
	}

	private List<Cluster<I>> clusters;

	private double changed_medoids_percentage;

	private double average_distance_between_medoids;

	private double sum_of_squared_error;

	public double getSum_of_squared_error() {
		return sum_of_squared_error;
	}

	public KMedoids(int k, List<I> elements, DistanceFunction<I> distanceFunction) {
		if (k == 0)
			throw new IllegalArgumentException("K cannot be zero");
		if (k > elements.size())
			throw new IllegalArgumentException(
					"K cannot be more than n (n numbers of element to cluster)");
		this.k = k;
		this.elements = elements;
		this.distanceFunction = distanceFunction;
	}

	@Override
	public void doClustering() {

		log.info("doClustering");

		// random medoids initialization
		List<I> medoids = generateRandomMedoids(k, elements);

		clusters = buildClusters(medoids, this.distanceFunction);
		
		iteration = 0;

		boolean shouldStop = false;

		do {

			iteration++;

			log.info("Iteration: " + iteration);

			if(iteration!=1)
				for (int i = 0; i < clusters.size(); i++) {
					clusters.get(i).cleanCluster();
				}

			// ELEMENTS CLUSTER'S MEMBERSHIP CHECKING
			this.sum_of_squared_error = 0.0;
			for (int u = 0; u < elements.size(); u++)
				// gathering squared scatters
				this.sum_of_squared_error += addToNearest(elements.get(u),
						this.clusters, this.distanceFunction);

			log.info("SSE " + this.sum_of_squared_error);

			// SHADOW CLUSTERS REMOVING PHASE//
			boolean someShadowSwapHappened;
			if (iteration == 1) {
				someShadowSwapHappened = true;
			} else {
				someShadowSwapHappened = removeShadowClusters(clusters,
						distanceFunction);
			}
			// NEW MEDOIDS COMPUTING PHASE: //

			// new medoids will be part of clustering algorithm if
			// they pass the stop criterion phase
			List<I> new_medoids = new ArrayList<I>(this.k);
			for (int i = 0; i < clusters.size(); i++) {
				// for each cluster i compute the new medoid
				clusters.get(i).computeMedoid();
				new_medoids.add(i, clusters.get(i).getMedoid());
			}

			// STOPPING CRITERION PHASE //

			// I count how many different medoids there are between
			// medoids and new_medoids and the mean distance between them
			int different_medoids_count = 0;
			double distance_sum = 0;
			for (int i = 0; i < this.k; i++) {
				I current_medoid = medoids.get(i);
				I new_medoid = new_medoids.get(i);
				if (!current_medoid.equals(new_medoid)) {
					different_medoids_count++;
					log.info("Medoid Changed:\nOld: " + current_medoid
							+ "\n New: " + new_medoid);

				}
				distance_sum += distanceFunction.distance(current_medoid,
						new_medoid);
			}

			// I update the average distance between new medoids and medoids
			this.average_distance_between_medoids = distance_sum
					/ ((double) this.k);

			// I update the number of medoids changed between old_medoids and
			// new_medoids
			this.changed_medoids_percentage = ((double) different_medoids_count)
					/ ((double) this.k);

			log.info("Average distance between medoids "
					+ this.average_distance_between_medoids);

			log.info("changed_medoids_percentage "
					+ this.changed_medoids_percentage);

			if (iteration > MAX_ITERATIONS) {
				log.info("TERMINATION BY MAX ITERATIONS: " + iteration);
				shouldStop = true;
			}
			
			if (!someShadowSwapHappened) {
				
				if (this.changed_medoids_percentage < MIN_CHANGED_MEDOIDS_PERCENTAGE) {
					log.info("TERMINATION BY LESS CHANGED MEDOIDS PERCENTAGE ALLOWED: "
							+ this.changed_medoids_percentage);
					shouldStop = true;
				}

				if (this.average_distance_between_medoids < MIN_AVERAGE_DISTANCE_BETWEEN_MEDOIDS) {
					log.info("TERMINATION BY: LESS THAN MIN AVERAGE DISTANCE BETWEEN MEDOIDS ALLOWED: "
							+ this.average_distance_between_medoids);
					shouldStop = true;
				}
			}
			medoids = new_medoids;

		} while (!shouldStop);

	}

	/**
	 * Add the "element" passed to the closer cluster that belongs to the
	 * "clusters" list of cluster passed. The closeness is measured by the
	 * distance function passed. 
	 * If the element is already a medoid of one of the clusters passed then it
	 * will be not added to any cluster and the method returns null value.
	 * 
	 * @param element
	 *            The element to be assigned to the closer cluster passed.
	 * @param clusters
	 *            The list of clusters.
	 * @param distanceFunction
	 *            The distance function that compute the distance between object
	 *            of typer E.
	 * @return 
	 */
	private double addToNearest(I element,
			List<Cluster<I>> clusters, DistanceFunction<I> distanceFunction) {

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
				double distance = distanceFunction.distance(element, ithMedoid);

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

	/**
	 * 
	 * @param medoids
	 * @return
	 */
	private List<Cluster<I>> buildClusters(
			List<I> medoids, DistanceFunction<I> distanceFunction) {

		log.info("buildingClusters \nmedoids: " + medoids.toString());

		List<Cluster<I>> clusters = new ArrayList<Cluster<I>>();
		for (int i = 0; i < medoids.size(); i++) {
			clusters.add(new Cluster<I>(medoids.get(i), distanceFunction));
		}
		return clusters;
	}

	/**
	 * Generate a list k uniformly random generated medoid.
	 * 
	 * @param k
	 *            The number of medoids to be generated.
	 * @param elements
	 * @return
	 */
	private List<I> generateRandomMedoids(int k,
			List<I> elements) {
		TreeSet<I> set = new TreeSet<I>();
		int inseriti = 0;
		while (inseriti < k) {
			I ele = null;
			do {
				int rand = (int) (Math.random() * (double) elements.size());
				ele = elements.get(rand);
			} while (!set.add(ele));
			inseriti++;
		}

		log.info("generatedRandomMedoids: " + set.toString());

		return new ArrayList<I>(set);
	}

	private boolean removeShadowClusters(
			List<Cluster<I>> clusters, DistanceFunction<I> distanceFunction) {
		boolean someSwapHappened = false;
		for (int i = 0; i < clusters.size(); i++) {
			if (clusters.get(i).isShadow()) {
				for (int j = 0; j < clusters.size(); j++) {
					if (i != j) {
						if (clusters.get(j).isLessDistantOfTheFarestSwapThem(
								clusters.get(i))) {
							j = clusters.size();
							someSwapHappened = true;
						}
					}
				}
			}
		}
		return someSwapHappened;
	}

}