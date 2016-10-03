package it.clustering.distanceFunction.cached;

import it.Instance;
import it.clustering.distanceFunction.DistanceFunction;


/**
 * Decorator Pattern
 **/
public abstract class CachedDistanceFunction<C extends Instance>
		implements DistanceFunction<C> {

	protected DistanceFunction<C> distanceFunction;

	protected Cache<C> cache;

	@Override
	public double distance(C obj1, C obj2) {
		Double res = cache.get(obj1, obj2);
		if (res == null) {
			res = this.distanceFunction.distance(obj1, obj2);
			cache.put(res, obj1, obj2);
		}
		return res.doubleValue();
	}

}
