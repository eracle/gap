package it.clustering;


import it.clustering.kmedoids.KMedoidsCluster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Clusters {

	protected ArrayList<KMedoidsCluster> clusters;

	public Clusters(ArrayList clusters2) {
		this.clusters = clusters2;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("Clusters:\n");
		int i=1;
		for (Cluster cluster : this.clusters) {
			ret.append("Cluster n:"+i+"\n");
			i++;
			ret.append(cluster.toString());
		}
		return ret.toString();
	}
	
	public int countElements(){
		int sum=0;
		for (Cluster it : clusters) {
			ArrayList el = it.getElements();
			sum+=el.size();
		}
		return sum;
	}

}
