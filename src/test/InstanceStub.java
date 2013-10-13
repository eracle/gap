package test;

import it.Instance;

public class InstanceStub implements Instance {

	private String name;

	public InstanceStub(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "InstanceStub [name=" + name + "]";
	}

}
