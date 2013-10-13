package it.clustering;

import it.Instance;

public interface ClusteringAlgorithm<I extends Instance> {

	public void doClustering();

	public Clusters<I> getClusters();

}
