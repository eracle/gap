package it.clustering.distanceFunction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import it.Instance;

public class DistanceMatrix<I extends Instance> implements DistanceFunction<I>{

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("DistanceMatrix:\n");
		Iterator<Entry<I, Integer>> iterator = this.indexer.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<I, Integer> next = iterator.next();
			ret.append("Instance: "+next.getKey().toString()+"\tIndex "+next.getValue().toString()+"\n");
		}
		
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances[0].length; j++) {
				ret.append(this.distances[i][j]+"\t");
			}
			ret.append("\n");
		}
		
		return ret.toString();
	}

	private HashMap<I, Integer> indexer;
	
	private double[][] distances;

	public DistanceMatrix(I[] elements, double[][] distances) {
		super();
		if((elements.length != distances.length))
			throw new IllegalArgumentException("Number of elements differs from the rows of the matrix");
		if(elements.length != distances[0].length)
			throw new IllegalArgumentException("Number of elements differs from the column of the matrix");
		
		indexer = new HashMap<I,Integer>(elements.length);
		int i = 0;
		for (I c : elements) {
			indexer.put(c, new Integer(i));
			i++;
		}
		
		this.distances=distances;
	}

	@Override
	public double distance(I obj1, I obj2) {
		Integer idx1 = indexer.get(obj1);
		Integer idx2 = indexer.get(obj2);
		return this.distances[idx1][idx2];
	}

	
	
}
