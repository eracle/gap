package it.clustering;


import java.util.ArrayList;


import it.clustering.distanceFunction.DistanceFunction;

public abstract class ClusteringAlgorithm {

	public abstract Clusters cluster();



	public ClusteringAlgorithm(ArrayList<Instance> elements, DistanceFunction distanceFunction) {
		this.elements=elements;
		this.distanceFunction = distanceFunction;
	}


	
	

	protected DistanceFunction distanceFunction;
	
	protected ArrayList<Instance> elements;
}
