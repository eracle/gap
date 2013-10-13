package test;

import static org.junit.Assert.*;


import it.clustering.distanceFunction.DistanceMatrix;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DistanceMatrixTest {

	private DistanceMatrix<InstanceStub> dm;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		InstanceStub[] elements = new InstanceStub[4];
		
		elements[0] = new InstanceStub("A");
		elements[1] = new InstanceStub("B");
		elements[2] = new InstanceStub("C");
		elements[3] = new InstanceStub("D");
			
							//A B C D
		double[][] arr = 	{{0,0,1,1}, //A
							 {0,0,1,1},	//B
							 {1,1,0,0},	//C	
							 {1,1,0,0}};//D
		
		this.dm = new DistanceMatrix<InstanceStub>(elements,arr);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDistanceMatrix() {
		System.out.println(this.dm.toString());
		
	}

	@Test
	public void testDistance() {
		fail("Not yet implemented");
	}

}
