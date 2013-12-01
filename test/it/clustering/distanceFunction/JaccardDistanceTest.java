package it.clustering.distanceFunction;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Set;
import java.util.TreeSet;

import it.SparseInstance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JaccardDistanceTest{

	JaccardDistance<String, SparseInstance<String>>  sut;
	
	@Mock
	SparseInstance<String> sparse1;
	
	@Mock
	SparseInstance<String> sparse2;
	
	//first test set
	Set<String> first_set;
	
	//second test set
	Set<String> second_set;
	
	@Before
	public void setUp() throws Exception {
		//build phase- setUp part 
		this.sut= new JaccardDistance<>();
		
		
		first_set = new TreeSet<String>();
		first_set.add("eggs");
		first_set.add("meat");
		
		//common strings
		first_set.add("pasta");
		first_set.add("italy");
		
		first_set.add("sweden");
		first_set.add("spain");

		
		
		second_set = new TreeSet<String>();
		second_set.add("england");
		
		//common strings
		second_set.add("italy");
		second_set.add("pasta");
		
		second_set.add("pizza");
		second_set.add("mandolino");
		
	}

	@Test
	public final void testDistanceNullInstances(){
		//build phase- pre-execution part
		when(sparse1.getElements()).thenReturn(null);
		when(sparse2.getElements()).thenReturn(null);
		
		//TODO: writing the distance method contract (through javadoc)
		
		//operate - phase
		//double d = this.sut.distance(this.sparse1,this.sparse2);
		
		//check - phase
		//assertTrue(true);
	}
	@Test
	public final void testDistanceDefinitionDomain() {
		//build phase- pre-execution part
		when(sparse1.getElements()).thenReturn(this.first_set);
		when(sparse2.getElements()).thenReturn(this.second_set);
		
		//operate - phase
		double res = this.sut.distance(this.sparse1,this.sparse2);
		
		//check - phase
		//TODO:behavioural validation
		
		
		//state validation
		assertTrue("Result less than zero", res < 0);
		assertTrue("Result more than one", res > 1.0);
		assertTrue("Result is not 0.222...",res==(2.0/9.0));
		
	}
	
	@Test
	public final void testJaccardSimilaritySetSet() {
		//build phase
		
		//execution phase
		double res = JaccardDistance.jaccardSimilarity(this.first_set,this.second_set);
		
		//check phase
		assertTrue("Result less than zero", res < 0);
		assertTrue("Result more than one", res > 1.0);
		assertTrue("Result is not 0.222...",res == (2.0/9.0));
	}
	
	
}
