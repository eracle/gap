package it.clustering;


import java.util.List;

import it.clustering.distanceFunction.DistanceFunction;

public abstract class ClusteringAlgorithm<I extends Instance> {

	public abstract Clusters<I,? extends Cluster<I>> cluster();

	public ClusteringAlgorithm(List<I> elements,DistanceFunction<I> distanceFunction) {
		this.elements=elements;
		this.distanceFunction = distanceFunction;
	}


	
	

	protected DistanceFunction<I> distanceFunction;
	
	protected List<I> elements;
}
