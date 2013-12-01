package it.clustering.distanceFunction;


import it.SparseInstance;

import java.util.HashSet;
import java.util.Set;


/**
 * This class implements as DistanceFunction 
 * the JaccardDistance function defined as follows:
 * JaccardDistance(x,y) = 1 - JaccardSimilarity(x,y)
 * 
 * From Wikipedia:
 * Jaccard similarity coefficient
 * "The Jaccard coefficient measures similarity between sample sets, and is defined as the size of the intersection divided by the size of the union of the sample sets"
 * 
 * Wikipedia:
 * http://en.wikipedia.org/wiki/Jaccard_index
 */
public class JaccardDistance<K,T extends SparseInstance<K>> implements DistanceFunction<T>  {

	//TODO: javadoc of the distance method
	
	//TODO: some explaination about generic types
	
	@Override
	public double distance(T obj1,T obj2){
		return 1-this.jaccardSimilarity(obj1, obj2);
	}
	
	/**
	 * Returns the Jaccard similarity between the sets of the instances passed.
	 * @param obj1 The first instance.
	 * @param obj2 The second instance.
	 * @return A real value between 0 and 1.
	 */
	private double jaccardSimilarity(
			T obj1, T obj2) {
		//if the instances are exactly the same java object
		//the similarity is 1.
		if (obj1.equals(obj2))
			return 1;

		//getting the sets of objects from the instances
		Set<K> set1 = obj1.getElements();
		Set<K> set2 = obj2.getElements();

		return jaccardSimilarity(set1, set2);

	}

	
	/**
	 * Computes the Jaccard similarity between the sets passed by arguments.
	 * The intersection and the union of the sets passed are computed using 
	 * the equals function of the objects.
	 * @param set1 First set of objects.
	 * @param set2 Second set of objects.
	 * @return A real number between 0 and 1.
	 */
	public static <E> double jaccardSimilarity(Set<E> set1, Set<E> set2) {
		// building the intersection set
		Set<E> interception = new HashSet<E>();
		interception.addAll(set1);
		interception.retainAll(set2);

		// computing the size of the union set
		double unionCardinality = set1.size() + set2.size() - interception.size();

		// computing the jaccard value
		double jaccardValue = ((double) interception.size())
				/ ((double) unionCardinality);

		return jaccardValue;
	}

}
