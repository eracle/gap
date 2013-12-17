package it.clustering.kmedoids;

import it.Instance;
import it.clustering.distanceFunction.DistanceFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

public class KMedoids<I extends Instance> {

	/** Log4j logger used: */
	private static Logger log = Logger.getLogger(KMedoids.class);

	/** Execution parameters from the KMedoids.properties file */
	private int MAX_ITERATIONS = Integer.parseInt(CustomPropertiesReader
			.getString("KMedoids.MAX_ITERATIONS"));
	// TODO: ending the comments in the KMedoids properties file.
	private double MIN_CHANGED_MEDOIDS_PERCENTAGE = Double
			.parseDouble(CustomPropertiesReader
					.getString("KMedoids.MIN_CHANGED_MEDOIDS_PERCENTAGE"));

	private double MIN_AVERAGE_DISTANCE_BETWEEN_MEDOIDS = Double
			.parseDouble(CustomPropertiesReader
					.getString("KMedoids.MIN_AVERAGE_DISTANCE_BETWEEN_MEDOIDS"));

	/** Execution variables: */
	private int k;

	//private List<I> elements;

	private int iteration;

	private DistanceFunction<I> distanceFunction;

	private Clusters<I> clusters;

	private double changed_medoids_percentage;

	private double average_distance_between_medoids;

	private double sum_of_squared_error;

	public double getSum_of_squared_error() {
		return sum_of_squared_error;
	}

	/**
	 * This method perform a clustering task. The k parameter must be in the
	 * [0,elements.size()] closed interval. Otherwise IllegalArgoumentException
	 * is thrown. If the parameter k is zero a null value is returned.
	 * 
	 * @param k
	 *            The number of clusters to be created.
	 * @param elements
	 *            The elements to cluster.
	 * @param distanceFunction
	 *            The distance function defined between pairs of elements.
	 * @return The {@code}Clusters{@code} result object.
	 */
	public Clusters<I> doClustering(int k, List<I> elements,
			DistanceFunction<I> distanceFunction) {
		if (k < 0) {
			String msg = "K cannot be less than 0";
			log.fatal(msg);
			throw new IllegalArgumentException(msg);
		}
		if (k > elements.size()) {
			String msg = "K argument cannot be more than the number of elements";
			log.fatal(msg);
			throw new IllegalArgumentException(msg);
		}
		if (k == 0) {
			log.fatal("The K argument passed is 0, empty Clusters object returned");
			return null;
		}

		// continues the execution, maybe could be implemented with a tailored
		// case handling.
		// TODO: implement a tailored case handling?
		if (k == elements.size())
			log.error("The K argument passed is equals to the numbers of elements");

		this.k = k;
		//this.elements = elements;
		this.distanceFunction = distanceFunction;

		log.info("Starting the clustering with k: " + this.k + " Elements: " + elements.size()); //$NON-NLS-2$

		// random medoids initialization
		List<I> medoids = generateRandomMedoids(k, elements);

		// build the clusters from the medoids passed
		clusters = new Clusters<I>(medoids, this.distanceFunction);

		iteration = 1;

		boolean shouldStop = false;

		do {

			log.info("Iteration: " + iteration);

			// set every cluster only to its medoid.
			if (iteration != 1)
				clusters.stripClusters();

			// CHECKING THE CLUSTER'S MEMBERSHIP OF ALL THE ELEMENTS
			this.sum_of_squared_error = 0.0;
			for (int u = 0; u < elements.size(); u++)
				// gathering squared scatters
				this.sum_of_squared_error += clusters.addToNearest(elements
						.get(u));

			log.info("SSE " + this.sum_of_squared_error);

			// SHADOW CLUSTERS REMOVING PHASE//
			boolean shadowSwapChanges = (iteration == 1);
			if (!shadowSwapChanges) 
				shadowSwapChanges = clusters.removeShadowClusters();
			

			// NEW MEDOIDS COMPUTING PHASE: //
			List<I> new_medoids = clusters.getMedoids();

			// STOPPING CRITERION PHASE //
			updateStats(medoids, new_medoids);
			if (!shadowSwapChanges)
				shouldStop = stopCheck(medoids, new_medoids);

			medoids = new_medoids;

			iteration++;
		} while (!shouldStop);

		return this.clusters;
	}

	private boolean stopCheck(List<I> old_medoids, List<I> new_medoids) {
		boolean shouldStop = false;

		if (iteration > MAX_ITERATIONS) {
			log.info("TERMINATION BY MAX ITERATIONS: " + iteration);
			shouldStop = true;
		}

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

		return shouldStop;
	}

	private void updateStats(List<I> old_medoids, List<I> new_medoids) {
		// counting how many different medoids there are between
		// old_medoids and new_medoids passed and the mean distance between them
		int different_medoids_count = 0;
		double distance_sum = 0;
		for (int i = 0; i < this.k; i++) {
			I current_medoid = old_medoids.get(i);
			I new_medoid = new_medoids.get(i);
			if (!current_medoid.equals(new_medoid)) {
				different_medoids_count++;
				log.trace("Medoid Changed:\n" + current_medoid + "\t---->\t"
						+ new_medoid);

			}
			distance_sum += this.distanceFunction.distance(current_medoid,
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
	}

	/**
	 * Generates a list of k uniformly random chosen medoids.
	 * 
	 * @param k
	 *            The number of medoids to generate.
	 * @param elements
	 *            The list of elements from
	 * @return
	 */
	private List<I> generateRandomMedoids(int k, List<I> elements) {
		log.info("Generating Random Medoids");

		TreeSet<I> set = new TreeSet<I>();
		int inserted = 0;
		while (inserted < k) {
			I ele = null;
			do {
				int rand = (int) (Math.random() * (double) elements.size());
				ele = elements.get(rand);
			} while (!set.add(ele));
			inserted++;
		}

		log.debug("generatedRandomMedoids: " + set.toString());

		return new ArrayList<I>(set);
	}

}