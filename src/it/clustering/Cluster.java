package it.clustering;


import java.util.ArrayList;
import java.util.List;

public class Cluster<I extends Instance> {

	/**
	 * The elements that belongs to the cluster.
	 */
	private List<I> elements;

	public Cluster(){
		this.elements = new ArrayList<I>();
	}
	public List<I> getElements() {
		return elements;
	}

	public void setElements(List<I> elements) {
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
