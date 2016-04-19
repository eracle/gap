package it.clustering.kmedoids.instanceStub;

import it.clustering.distanceFunction.DistanceFunction;
import it.clustering.distanceFunction.DistanceFunctionTest;

import org.junit.Before;
import org.junit.Test;

public class InstanceStubDistanceFunctionTest extends DistanceFunctionTest{

	private DistanceFunction sut;
	
	private InstanceStub i1;
	
	private InstanceStub i2; 
	
	@Before
	public void setUp() throws Exception {
		sut =  new InstanceStubDistanceFunction();
		i1 = new InstanceStub();
		i2 = new InstanceStub();
	}

	@Test
	public void testDistance() {
		
		double dist = sut.distance(i1, i2);
		super.assesDomainDefinition(dist);
		
	}

}
