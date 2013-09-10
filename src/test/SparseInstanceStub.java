package test;

import java.util.Set;

import it.SparseInstance;

public class SparseInstanceStub implements SparseInstance<String>,Comparable<SparseInstanceStub>{


	
	@Override
	public String toString() {
		return "SparseInstanceStub [elements=" + elements + "]";
	}

	private Set<String> elements;
	
	public SparseInstanceStub(Set<String> elements) {
		super();
		this.elements = elements;
	}

	@Override
	public Set<String> getElements() {
		return elements;
	}

	@Override
	public int compareTo(SparseInstanceStub arg0) {
		return this.hashCode()-arg0.hashCode();
	}

	

	

	

}
