package it.clustering.distanceFunction;

import static org.junit.Assert.*;


import it.clustering.distanceFunction.DistanceMatrix;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DistanceMatrixTest {

	private DistanceMatrix<InstanceStub> dm;
	
	private InstanceStub[] instances;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		this.instances = new InstanceStub[4];
		
		instances[0] = new InstanceStub("A");
		instances[1] = new InstanceStub("B");
		instances[2] = new InstanceStub("C");
		instances[3] = new InstanceStub("D");
			
							//A B C D
		double[][] arr = 	{{0,0,1,1}, //A
							 {0,0,1,1},	//B
							 {1,1,0,0},	//C	
							 {1,1,0,0}};//D
		
		this.dm = new DistanceMatrix<InstanceStub>(instances,arr);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDistanceMatrix() {
		assert(this.dm!=null);
		System.out.println(this.dm.toString());
		
	}

	@Test
	public void testDistance() {
		double[] exp = new double[1];
		exp[0]=0;
		double[] real = new double[1];
		real[0]=this.dm.distance(this.instances[0],this.instances[1]);
		System.out.println(real[0]);
		assertEquals("Distance between first and second is not expected",exp,real);
	}

}
