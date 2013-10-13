package it.clustering.distanceFunction.Cached;

import it.Instance;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class HashTreeCache<C extends Instance & Comparable<C>> implements Cache<C> {

	private HashMap<C, TreeMap<C, Double>> data;

	public HashTreeCache(List<C> elements) {
		data = new HashMap<C, TreeMap<C, Double>>(elements.size());
		for (int i = 0; i < elements.size(); i++) {
			data.put(elements.get(i), new TreeMap<C, Double>());
		}
	}

	@Override
	public Double get(C obj1, C obj2) {
		MinMax<C> m = new MinMax<C>(obj1, obj2);
		return data.get(m.getMin()).get(m.getMax());
	}


	@Override
	public void put(Double value, C obj1, C obj2) {
		MinMax<C> m = new MinMax<C>(obj1, obj2);
		data.get(m.getMin()).put(m.getMax(), value);
	}

	private class MinMax<V extends Comparable<V>> {
		private V max;
		private V min;

		public MinMax(V obj1, V obj2) {
			super();
			if (obj1.compareTo(obj2) > 0) {
				max = obj1;
				min = obj2;
			} else {
				max = obj2;
				min = obj1;
			}
		}

		protected V getMax() {
			return max;
		}

		protected V getMin() {
			return min;
		}
	}

}
