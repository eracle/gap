package it.clustering.kmedoids;

import static org.junit.Assert.*;
import it.clustering.kmedoids.instanceStub.InstanceStub;
import it.clustering.kmedoids.instanceStub.InstanceStubDistanceFunction;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class KMedoidsClustersTest {

	private KMedoidsClusters sut;

	@Before
	public void setUp() throws Exception {
		ArrayList medoids = new ArrayList();
		medoids.add(new InstanceStub(1));
		medoids.add(new InstanceStub(2));
		medoids.add(new InstanceStub(3));
		medoids.add(new InstanceStub(4));
		
		InstanceStubDistanceFunction df = new InstanceStubDistanceFunction();
		
		this.sut = new KMedoidsClusters(medoids, df);
	}

	//TODO: finish the testing methods
	@Test
	public void testToString_notNull_notEmpty() {
		String str = this.sut.toString();
		assertNotNull(str);
		assertTrue("ToString lenght is 0", str.length() != 0);
		System.out.println(str);
	}

	

	@Test
	public void testStripClusters() {
		return;
	}

	@Test
	public void testAssignToTheNearestCluster() {
		return;
	}

	@Test
	public void testRemoveShadowClusters() {
		return;
	}

	@Test
	public void testGetMedoids() {
		return;
	}

}