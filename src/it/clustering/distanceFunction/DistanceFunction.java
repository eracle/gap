package it.clustering.distanceFunction;

import it.Instance;

public interface DistanceFunction<I extends Instance> {

	public abstract double distance(I obj1, I obj2);

}