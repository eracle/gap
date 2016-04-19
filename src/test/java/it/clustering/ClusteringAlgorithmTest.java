package it.clustering;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import it.clustering.distanceFunction.DistanceFunction;
import it.clustering.kmedoids.instanceStub.InstanceStub;
import it.clustering.kmedoids.instanceStub.InstanceStubDistanceFunction;

import org.junit.Before;
import org.junit.Test;

public abstract class ClusteringAlgorithmTest {

	protected ClusteringAlgorithm sut;
	
	protected ArrayList<InstanceStub> list;
	
	protected DistanceFunction df;
	
	@Before
	public void setUp() throws Exception {
		df = new InstanceStubDistanceFunction();
		list = new ArrayList();
		
		//first cluster
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		
		//second cluster
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		list.add(new InstanceStub());
		
		//noise
		list.add(new InstanceStub());
	}


	protected void clusterTest_ThreeClusters_NotNull() {
		Clusters res = this.sut.cluster();
		System.out.println(res);
		assertNotNull("clusters obj is null!", res);
		assertFalse("the number of clusters is zero!",res.clusters.size()==0);
		
		int num_ele = res.countElements();
		assertTrue("Not all elements were clustered",num_ele==list.size());
		assertTrue("Not 3 clusters returned!",res.clusters.size()==3);
	}

}
