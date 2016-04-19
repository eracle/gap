package it.clustering.distanceFunction;


import it.clustering.Instance;

public interface DistanceFunction {

	public abstract double distance(Instance obj1, Instance obj2);

}