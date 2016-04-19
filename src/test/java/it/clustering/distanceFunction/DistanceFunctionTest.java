package it.clustering.distanceFunction;

import static org.junit.Assert.assertTrue;

public class DistanceFunctionTest {

	public DistanceFunctionTest() {
		super();
	}

	protected void assesDomainDefinition(double res) {
		assertTrue("Result less than zero", res >= 0);
		assertTrue("Result more than one", res <= 1.0);
		
	}

}