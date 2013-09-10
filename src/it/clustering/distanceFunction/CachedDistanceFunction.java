package it.clustering.distanceFunction;


import it.Instance;
import it.clustering.distanceMatrix.DistanceMatrix;

public class CachedDistanceFunction<I extends Instance> implements DistanceFunction<I>{

	private DistanceMatrix<I> matrix;

	private DistanceFunction<I> distanceFunction;
	

	
	public CachedDistanceFunction(DistanceMatrix<I> matrix,
			DistanceFunction<I> distanceFunction) {
		super();
		this.matrix = matrix;
		this.distanceFunction = distanceFunction;
	}



	@Override
	public double distance(I obj1, I obj2){
		Double res = matrix.get(obj1, obj2);
		if (res == null) {
			res = new Double(this.distanceFunction.distance(obj1,obj2));
			matrix.put(res, obj1, obj2);
		}
		return res.doubleValue();
	}


}
