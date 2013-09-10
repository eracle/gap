package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.Instance;
import it.clustering.ClusteringAlgorithm;
import it.clustering.distanceFunction.CachedDistanceFunction;
import it.clustering.distanceFunction.DistanceFunction;
import it.clustering.distanceFunction.JaccardDistance;
import it.clustering.distanceMatrix.DistanceMatrix;
import it.clustering.distanceMatrix.DistanceMatrixImpl;
import it.clustering.distanceMatrix.HashTreeMatrix;
import it.clustering.kmedoids.Cluster;
import it.clustering.kmedoids.KMedoids;


public class test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//double[][] arr = {{0d,1d,1d},{1d,0d,1d},{1d,1d,0d}};
		
		
		List<SparseInstanceStub> list = new ArrayList<SparseInstanceStub>();
		
		Set<String> first = new TreeSet<String>();
		first.add("Hello");
		first.add("World");
		list.add(new SparseInstanceStub(first));
		
		Set<String> second = new TreeSet<String>();
		second.add("Hello");
		second.add("Mama!");
		list.add(new SparseInstanceStub(second));
		
		Set<String> third = new TreeSet<String>();
		third.add("Second");
		third.add("Cluster");
		list.add(new SparseInstanceStub(third));
		
		DistanceMatrix<SparseInstanceStub> m = new HashTreeMatrix<SparseInstanceStub>(list);
		//System.out.println(m);
		
		DistanceFunction<SparseInstanceStub> jaccard = new JaccardDistance<String,SparseInstanceStub>();
		DistanceFunction<SparseInstanceStub> df = new CachedDistanceFunction<SparseInstanceStub>(m, jaccard);
		
		for (SparseInstanceStub inst1 : list) {
			for (SparseInstanceStub inst2 : list) {
				System.out.println(""+inst1+"\t"+inst2+"\t"+df.distance(inst1, inst2));
			}
		}
		
		
		
		
		
		
		KMedoids<SparseInstanceStub> alg = new KMedoids<SparseInstanceStub>(2,list,df);
		alg.doClustering();
		List<Cluster<SparseInstanceStub>> clusters = alg.getClusters();
		int c = 0;
		for (Cluster<SparseInstanceStub> cluster : clusters) {
			System.out.println("Cluster number:"+c);
			c++;
			System.out.println(cluster);
			System.out.println();
		}
	}

}
