package it.clustering.kmedoids;

import it.clustering.Cluster;
import it.clustering.Instance;
import it.clustering.distanceFunction.DistanceFunction;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * This class represents a Cluster in the K-Medoids algorithm.
 * 
 * @author Antonio Ercole De Luca
 * 
 * @param <I>
 *            The type of the elements to be clustered.
 */
public class KMedoidsCluster<I extends Instance> extends Cluster<I> {

	// medoiod of the cluster
	public I medoid;

	private I farestElement;
	private double farestElementDistance;

	private DistanceFunction<I> distanceFunction;

	// TODO: contracts of the methods:
	// - the constructor
	// - cleanCluster
	// - isLessDistantOf...
	// - computeMedoid
	// - isShadow

	public KMedoidsCluster(I medoid, DistanceFunction<I> distanceFunction) {
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
		this.setElements(new ArrayList<I>());
		this.getElements().add(medoid);
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

		this.getElements().add(obj);
	}

	public boolean isLessDistantOfTheFarestSwapThem(
			KMedoidsCluster<I> shadowCluster) {
		if (this.distanceFunction.distance(shadowCluster.getMedoid(),
				this.medoid) < this.farestElementDistance) {
			I m0 = shadowCluster.medoid;
			I xm = this.farestElement;

			shadowCluster.getElements().remove(m0);
			shadowCluster.medoid = xm;
			shadowCluster.getElements().add(xm);
			shadowCluster.recomputeFarest();

			this.getElements().remove(xm);
			this.getElements().add(m0);
			this.recomputeFarest();

			return true;
		} else
			return false;
	}

	private I removeFarest() {
		if (this.getElements().size() > 1) {
			I ret = this.farestElement;
			this.getElements().remove(ret);

			// recompute farest
			recomputeFarest();
			return ret;
		} else
			return null;
	}

	private void recomputeFarest() {
		this.farestElement = this.medoid;
		this.farestElementDistance = 0;
		for (int i = 0; i < this.getElements().size(); i++) {
			double tmpFarValue = distanceFunction.distance(this.getElements()
					.get(i), this.medoid);
			if (tmpFarValue > this.farestElementDistance) {
				this.farestElement = this.getElements().get(i);
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
		for (int j = 0; j < this.getElements().size(); j++) {
			// for each cluster's element I compute
			// the mean distance to all the others cluster's elements
			double mean_distance_j_sum = 0;

			for (int k = 0; k < this.getElements().size(); k++)
				mean_distance_j_sum += distanceFunction.distance(this
						.getElements().get(j), this.getElements().get(k));

			if (mean_distance_j_sum == smallest_mean_distance_value) {
				if (medoid.hashCode() < this.getElements().get(j).hashCode()) {
					smallest_mean_distance_value = mean_distance_j_sum;
					medoid = this.getElements().get(j);
				}
			} else if (mean_distance_j_sum < smallest_mean_distance_value) {
				smallest_mean_distance_value = mean_distance_j_sum;
				medoid = this.getElements().get(j);
			}
		}

		if (!oldMedoid.equals(this.medoid))
			recomputeFarest();
	}

	public boolean isShadow() {
		return this.getElements().size() == 1;
	}

	private Iterator<I> getElementsIterator() {
		return this.getElements().iterator();

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KMedoidsCluster [medoid=");
		builder.append(medoid);
		builder.append(", farestElement=");
		builder.append(farestElement);
		builder.append(", farestElementDistance=");
		builder.append(farestElementDistance);
		builder.append(", distanceFunction=");
		builder.append(distanceFunction);
		builder.append("]");
		return super.toString().concat(builder.toString());
	}

}
