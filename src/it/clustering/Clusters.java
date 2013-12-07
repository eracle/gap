package it.clustering;

import it.Instance;
import it.clustering.kmedoids.Cluster;

import java.util.ArrayList;

public class Clusters<I extends Instance> extends ArrayList<Cluster<I>>{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2301796384317941708L;

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("Clusters:\n ");
		for(Cluster<I> elem : this){
			ret.append(elem.toString());
		}
		return ret.toString();
	}

	
}
