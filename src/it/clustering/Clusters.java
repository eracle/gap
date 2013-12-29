package it.clustering;


import java.util.Iterator;
import java.util.List;

public class Clusters<I extends Instance,C extends Cluster<I>> {

	protected List<C> clusters;

	public Clusters(List<C> clusters2) {
		this.clusters = clusters2;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("Clusters:\n");
		int i=1;
		for (Cluster<I> cluster : this.clusters) {
			ret.append("Cluster n:"+i+"\n");
			i++;
			ret.append(cluster.toString());
		}
		return ret.toString();
	}
	
	public int countElements(){
		int sum=0;
		for (Cluster<I> it : clusters) {
			List<I> el = it.getElements();
			sum+=el.size();
		}
		return sum;
	}

}
