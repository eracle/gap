package it.clustering;


import java.util.ArrayList;
import java.util.List;

public class Cluster {

	/**
	 * The elements that belongs to the cluster.
	 */
	private ArrayList elements;

	public Cluster(){
		this.elements = new ArrayList();
	}
	public ArrayList<Instance> getElements() {
		return elements;
	}

	public void setElements(ArrayList elements) {
		this.elements = elements;
	}
	
	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("Size:" + this.getElements().size() + "\n");
	
		ret.append("Elements:\n");
		for (int i = 0; i < getElements().size(); i++) {
			ret.append("\tElement:\t" + (i + 1) + "\t"
					+ getElements().get(i).toString() + "\n");
		}
	
		ret.append("\n");
		return ret.toString();
	}



}
