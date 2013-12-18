package it.clustering.kmedoids.instanceStub;

import it.clustering.distanceFunction.DistanceFunction;

public class InstanceStubDistanceFunction implements
		DistanceFunction<InstanceStub> {
	
	@Override
	public double distance(InstanceStub obj1, InstanceStub obj2) {
		return Math.abs((obj1.getValue() - obj2.getValue())
				/ (InstanceStub.MAX_VALUE - InstanceStub.MIN_VALUE));
	}

}
