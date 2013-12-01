package it.clustering;

import it.Instance;

public interface ClusteringAlgorithm<I extends Instance> {

	public Clusters<I> doClustering();
                                

}
