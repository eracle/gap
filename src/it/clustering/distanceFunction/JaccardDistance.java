package it.clustering.distanceFunction;


import it.SparseInstance;

import java.util.HashSet;
import java.util.Set;



public class JaccardDistance<K,T extends SparseInstance<K>> implements DistanceFunction<T>  {


	/*
	public JaccardDistance(List<T> elements){
		this.similarityMeasure = new JaccardSimilarity<T>(elements);
		
		
	}
	*/
	
	
	@Override
	public double distance(T obj1,T obj2){
		return 1-this.computeSimilarity(obj1, obj2);
	}
	
	private double computeSimilarity(
			T obj1, T obj2) {
		if (obj1.equals(obj2))
			return 1;

		Set<K> concepts1 = obj1.getElements();
		Set<K> concepts2 = obj2.getElements();

		// creo l'insieme intersezione
		Set<K> interception = new HashSet<K>();
		interception.addAll(concepts1);
		interception.retainAll(concepts2);

		double unionCardinality = concepts1.size() + concepts2.size() - interception.size();

		// calcolo l'indice di jaccard
		double jaccardValue = ((double) interception.size())
				/ ((double) unionCardinality);

		return jaccardValue;

	}

}
