package it.clustering.distanceMatrix;


public interface DistanceMatrix<C> {

	public abstract Double get(C obj1, C obj2);
	
	public void put(Double value, C obj1, C obj2);

}
