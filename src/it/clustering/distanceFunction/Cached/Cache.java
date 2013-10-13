package it.clustering.distanceFunction.Cached;

import it.Instance;

public interface Cache<C extends Instance> {

	public abstract void put(Double value, C obj1, C obj2);

	public abstract Double get(C obj1, C obj2);

}
