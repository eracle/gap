package it.clustering.distanceFunction.Cached;

import it.Instance;

import java.util.HashMap;
import java.util.List;

public class MatrixCache<I extends Instance> implements
		Cache<I> {

	private HashMap<I, Integer> indexer;

	private double matrix[][];

	// REQUIRES:
	// every entry in the matrix2 matrix must be not null
	// this is guaranteed by the type of the matrix (double, not Double)
	public MatrixCache(List<I> elements, double[][] matrix) {
		this.matrix = matrix;
		indexer = new HashMap<>(elements.size());
		int i = 0;
		for (I c : elements) {
			indexer.put(c, new Integer(i));
			i++;
		}
	}

	@Override
	public Double get(I obj1, I obj2) {
		int index1 = indexer.get(obj1);
		int index2 = indexer.get(obj2);
		return new Double(matrix[index1][index2]);
	}

	@Override
	public void put(Double value, I obj1, I obj2) {
	}

}
