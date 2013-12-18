package it.clustering.kmedoids.instanceStub;

import it.Instance;

public class InstanceStub implements Instance {

	public static int MIN_VALUE=0;
	
	public static int MAX_VALUE=0;
	
	private int value;
	
	public InstanceStub(){
		this.value = InstanceStub.MAX_VALUE;
		InstanceStub.MAX_VALUE++;
	}

	@Override
	public String toString() {
		return "InstanceStub [value=" + value + "]";
	}

	public int getValue() {
		return value;
	}
	
}
