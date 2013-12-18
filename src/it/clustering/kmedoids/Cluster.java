package it.clustering.kmedoids;

import it.Instance;
import it.clustering.distanceFunction.DistanceFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * 
 * This class represents a Cluster in the K-Medoids algorithm.
 * 
 * @author Antonio Ercole De Luca
 *
 * @param <I> The type of the elements to be clustered.
 */
public class Cluster<I extends Instance> {

	// medoiod of the cluster
	private I medoid;

	private I farestElement;
	private double farestElementDistance;

	// the elements associated with the cluster
	private List<I> elements;

	private DistanceFunction<I> distanceFunction;

	//TODO: contracts of the methods:
	// - the constructor
	// - cleanCluster
	// - isLessDistantOf...
	// - computeMedoid
	// - isShadow
	
	public Cluster(I medoid, DistanceFunction<I> distanceFunction) {
		reSetCluster(medoid, distanceFunction);
	}

	public void cleanCluster() {
		reSetCluster(this.medoid, this.distanceFunction);
	}

	private void reSetCluster(I medoid, DistanceFunction<I> distanceFunction) {

		this.medoid = medoid;
		this.farestElement = medoid;
		this.farestElementDistance = 0.0;
		this.distanceFunction = distanceFunction;
		this.elements = new ArrayList<I>();
		this.elements.add(medoid);
	}

	public I getMedoid() {
		return medoid;
	}

	/**
	 * Add the instance passed to the cluster, if is not already the Medoid.
	 * 
	 * @param instance
	 *            The instance to be added to the cluster.
	 * @return <code>true</code> if the instance passed was correctly added.<br>
	 *         <code>false</code> otherwise.
	 */
	public boolean addElement(I instance) {
		if (this.getMedoid().equals(instance))
			return false;
		else {
			addElement(instance,
					this.distanceFunction.distance(instance, this.medoid));
			return true;
		}
	}

	private void addElement(I obj, double distanceToMedoid) {
		if (distanceToMedoid > farestElementDistance) {
			this.farestElement = obj;
			this.farestElementDistance = distanceToMedoid;
		}

		this.elements.add(obj);
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("Size:" + this.elements.size() + "\n");
		ret.append("Medoid:\t" + medoid.toString() + "\n");

		ret.append("Elements:\n");
		for (int i = 0; i < elements.size(); i++) {
			ret.append("\tElement:\t" + (i + 1) + "\t"
					+ elements.get(i).toString() + "\n");
		}

		ret.append("\n");
		return ret.toString();
	}

	public boolean isLessDistantOfTheFarestSwapThem(Cluster<I> shadowCluster) {
		if (this.distanceFunction.distance(shadowCluster.getMedoid(),
				this.medoid) < this.farestElementDistance) {
			I m0 = shadowCluster.medoid;
			I xm = this.farestElement;

			shadowCluster.elements.remove(m0);
			shadowCluster.medoid = xm;
			shadowCluster.elements.add(xm);
			shadowCluster.recomputeFarest();

			this.elements.remove(xm);
			this.elements.add(m0);
			this.recomputeFarest();

			return true;
		} else
			return false;
	}

	private I removeFarest() {
		if (this.elements.size() > 1) {
			I ret = this.farestElement;
			this.elements.remove(ret);

			// recompute farest
			recomputeFarest();
			return ret;
		} else
			return null;
	}

	private void recomputeFarest() {
		this.farestElement = this.medoid;
		this.farestElementDistance = 0;
		for (int i = 0; i < this.elements.size(); i++) {
			double tmpFarValue = distanceFunction.distance(
					this.elements.get(i), this.medoid);
			if (tmpFarValue > this.farestElementDistance) {
				this.farestElement = this.elements.get(i);
				this.farestElementDistance = tmpFarValue;
			}
		}
	}

	public void computeMedoid() {
		I oldMedoid = this.medoid;
		double smallest_mean_distance_value = Double.MAX_VALUE;
		// I save the cluster's element which have
		// the smallest mean distance value to all the others cluster's
		// element
		for (int j = 0; j < this.elements.size(); j++) {
			// for each cluster's element I compute
			// the mean distance to all the others cluster's elements
			double mean_distance_j_sum = 0;

			for (int k = 0; k < this.elements.size(); k++)
				mean_distance_j_sum += distanceFunction.distance(
						this.elements.get(j), this.elements.get(k));

			if (mean_distance_j_sum == smallest_mean_distance_value) {
				if (medoid.hashCode() < this.elements.get(j).hashCode()) {
					smallest_mean_distance_value = mean_distance_j_sum;
					medoid = this.elements.get(j);
				}
			} else if (mean_distance_j_sum < smallest_mean_distance_value) {
				smallest_mean_distance_value = mean_distance_j_sum;
				medoid = this.elements.get(j);
			}
		}

		if (!oldMedoid.equals(this.medoid))
			recomputeFarest();
	}

	public boolean isShadow() {
		return this.elements.size() == 1;
	}

	private Iterator<I> getElementsIterator() {
		return this.elements.iterator();

	}

}
