package it.clustering.dbscan;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class EpsilonDiscoveringTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void discoverEpsTest() {
		ArrayList<Double> uf = new ArrayList<Double>();
		for (int i = 0; i < 10; i++) {
			uf.add(Math.random());
		}
		uf.add(0.9);
		uf.add(0.9);
		uf.add(0.9);
		uf.add(0.9);
		uf.add(0.9);
		uf.add(0.9);
		double[] array = new double[uf.size()];
		for (int i = 0; i < uf.size(); i++) {
			array[i]=uf.get(i);
		}
		EpsilonDiscovering.discoverEps(array);
	}

}
