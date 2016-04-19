package it.clustering.distanceFunction.jaccard;

import static org.junit.Assert.*;
import it.clustering.distanceFunction.DistanceFunctionTest;
import it.clustering.distanceFunction.jaccard.JaccardDistance;
import it.clustering.distanceFunction.jaccard.TreeSetInstance;

import org.junit.Before;
import org.junit.Test;

public class JaccardDistanceTest extends DistanceFunctionTest{

	JaccardDistance  sut;
	
	TreeSetInstance tree1;
	
	TreeSetInstance tree2;
		
	@Before
	public void setUp() throws Exception {
		//build phase- setUp part 
		this.sut= new JaccardDistance();
		
		
		tree1 = new TreeSetInstance();
		tree1.add("eggs");
		tree1.add("meat");
		
		//common strings
		tree1.add("pasta");
		tree1.add("italy");
		
		tree1.add("sweden");
		tree1.add("spain");

		
		
		tree2 = new TreeSetInstance();
		tree2.add("england");
		
		//common strings
		tree2.add("italy");
		tree2.add("pasta");
		
		tree2.add("pizza");
		tree2.add("mandolino");
		
	}

	@Test
	public final void testDistance_NullInstances(){
		//build phase- pre-execution part
		boolean exception;
		//operate - phase
		try{
			@SuppressWarnings("unused")
			double res = this.sut.distance(null,null);
			exception=false;
		}catch(NullPointerException e){
			exception=true;
		}
		
		//check - phase
		//state validation
		assertTrue("NullPointerException not thrown",exception);
	}
	
	@Test
	public final void testDistance_EmptyInstances(){
		//build phase- pre-execution part
		
		//operate - phase
		double res = this.sut.distance(new TreeSetInstance(),new TreeSetInstance());
		
		//check - phase
		//state validation
		assesDomainDefinition(res);
	}
	
	@Test
	public final void testDistance_equalInstances(){
		//build phase- pre-execution part
		
		//operate - phase
		TreeSetInstance instance = new TreeSetInstance();
		double res = this.sut.distance(instance,instance);
		
		//check - phase
		//state validation
		assesDomainDefinition(res);
		assertTrue("Distance is not 0",res==0.0);
	}
	
	@Test
	public final void testDistance_clonedInstances(){
		//build phase- pre-execution part
		
		//operate - phase
		TreeSetInstance instance = new TreeSetInstance();
		instance.addAll(this.tree1);
		double res = this.sut.distance(instance,this.tree1);
		
		//check - phase
		//state validation
		assesDomainDefinition(res);
		assertTrue("Distance is not 0",res==0);
	}
	
	@Test
	public final void testDistance_workingCase(){
		//build phase- pre-execution part
		
		//operate - phase
		double res = this.sut.distance(this.tree1,this.tree2);
		
		//check - phase
		//state validation
		assesDomainDefinition(res);
		assertTrue("Result is not 0.777...",res==(7.0/9.0));
	}
	
}
