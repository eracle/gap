package it.clustering.distanceMatrix;

import it.Instance;

import java.util.HashMap;
import java.util.List;


public class DistanceMatrixImpl<C extends Instance> implements DistanceMatrix<C>{

	
	private double matrix[][];
	//private List<C> elements;
	
	private HashMap<C,Integer> indexer;
	
	public DistanceMatrixImpl(List<C> elements){
		//this.elements=elements;
		indexer = new HashMap<>(elements.size());
		int i=0;
		for (C c : elements) {
			indexer.put(c,new Integer(i));
			i++;
		}
	}
	
	public DistanceMatrixImpl(List<C> elements,double[][] matrix2) {
		this(elements);
		this.matrix=matrix2;
	}

	@Override
	public Double get(C obj1, C obj2) {
		int index1= indexer.get(obj1);
		int index2 = indexer.get(obj2);
		return new Double(matrix[index1][index2]);
	}

	@Override
	public void put(Double value, C obj1, C obj2) {
		int index1= indexer.get(obj1);
		int index2 = indexer.get(obj2);
		matrix[index1][index2] = value.doubleValue();
	}

	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				r.append(matrix[i][j]+"\t");
			}
			r.append("\n");
		}
		return r.toString();
	}

}
