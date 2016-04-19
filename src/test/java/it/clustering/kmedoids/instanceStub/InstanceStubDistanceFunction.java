package it.clustering.kmedoids.instanceStub;

import it.clustering.Instance;
import it.clustering.distanceFunction.DistanceFunction;

public class InstanceStubDistanceFunction implements
		DistanceFunction {

	@Override
	public double distance(Instance obj1, Instance obj2) {
		InstanceStub cast_obj1 = (InstanceStub) obj1;
		InstanceStub cast_obj2 = (InstanceStub) obj2;

		return Math.abs((cast_obj1.getValue() - cast_obj2.getValue())
				/ (InstanceStub.MAX_VALUE - InstanceStub.MIN_VALUE));
	}
}
