package it.ci.SimilarityMeasures;


import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.AbstractDistance;
import net.sf.javaml.distance.AbstractSimilarity;

import java.util.Set;
import java.util.TreeSet;


/**
 * This class implements as DistanceFunction the JaccardSimilarity function
 * defined as follows: JaccardSimilarity(x,y) = 1 - JaccardSimilarity(x,y)
 * 
 * From Wikipedia: Jaccard similarity coefficient
 * "The Jaccard coefficient measures similarity between sample sets, and is defined as the size of the intersection divided by the size of the union of the sample sets"
 * 
 * Wikipedia: http://en.wikipedia.org/wiki/Jaccard_index
 * 
 * 
 * Abouts generics type:
 * This class declares two types:
 * S:	The type of the objects that composes the sets.
 * 		The method {@code}equals{@code} is used on this objects.
 * C:	The implementation of the Instance interface.
 * 		It must extend Set<S>.
 * 
 * 
 * @author Antonio Ercole De Luca 
 * 
 */
public class JaccardSimilarity extends AbstractSimilarity {




	/**
	 * Computes the Jaccard similarity between the sets passed by arguments. The
	 * intersection and the union of the sets passed are computed using the
	 * equals function of the objects.
	 * 
	 * @param set1
	 *            First set of objects.
	 * @param set2
	 *            Second set of objects.
	 * @return A real number between 0 and 1.
	 */
	public double jaccardSimilarity(Set<Instance> set1, Set<Instance> set2) {
		// if the instances are exactly the same java object
		// the similarity is 1.
		if (set1.equals(set2))
			return 1;

		// building the intersection set
		Set<Instance> interception = new TreeSet<Instance>();
		interception.addAll(set1);
		interception.retainAll(set2);

		// computing the size of the union set
		double unionCardinality = set1.size() + set2.size()
				- interception.size();

		// computing the jaccard value
		double jaccardValue = ((double) interception.size())
				/ ((double) unionCardinality);

		return jaccardValue;
	}


	@Override
	public double measure(Instance x, Instance y) {
		Set f = (Set) x;
		Set s = (Set) y;

		return this.jaccardSimilarity(f, s);
	}
}
