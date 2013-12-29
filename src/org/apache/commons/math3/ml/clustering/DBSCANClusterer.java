/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math3.ml.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.clustering.Cluster;
import it.clustering.ClusteringAlgorithm;
import it.clustering.Clusters;
import it.clustering.Instance;
import it.clustering.distanceFunction.DistanceFunction;

/**
 * DBSCAN (density-based spatial clustering of applications with noise)
 * algorithm.
 * <p>
 * The DBSCAN algorithm forms clusters based on the idea of density
 * connectivity, i.e. a point p is density connected to another point q, if
 * there exists a chain of points p<sub>i</sub>, with i = 1 .. n and
 * p<sub>1</sub> = p and p<sub>n</sub> = q, such that each pair
 * &lt;p<sub>i</sub>, p<sub>i+1</sub>&gt; is directly density-reachable. A point
 * q is directly density-reachable from point p if it is in the
 * &epsilon;-neighborhood of this point.
 * <p>
 * Any point that is not density-reachable from a formed cluster is treated as
 * noise, and will thus not be present in the result.
 * <p>
 * The algorithm requires two parameters:
 * <ul>
 * <li>eps: the distance that defines the &epsilon;-neighborhood of a point
 * <li>minPoints: the minimum number of density-connected points required to
 * form a cluster
 * </ul>
 * 
 * @param <I>
 *            type of the points to cluster
 * @see <a href="http://en.wikipedia.org/wiki/DBSCAN">DBSCAN (wikipedia)</a>
 * @see <a
 *      href="http://www.dbs.ifi.lmu.de/Publikationen/Papers/KDD-96.final.frame.pdf">
 *      A Density-Based Algorithm for Discovering Clusters in Large Spatial
 *      Databases with Noise</a>
 * @version $Id: DBSCANClusterer.java 1461866 2013-03-27 21:54:36Z tn $
 * @since 3.2
 */
public class DBSCANClusterer<I extends Instance> extends ClusteringAlgorithm<I> {

	/** Maximum radius of the neighborhood to be considered. */
	private final double eps;

	/** Minimum number of points needed for a cluster. */
	private final int minPts;

	/** Status of a point during the clustering process. */
	private enum PointStatus {
		/** The point has is considered to be noise. */
		NOISE,
		/** The point is already part of a cluster. */
		PART_OF_CLUSTER
	}

	/**
	 * Creates a new instance of a DBSCANClusterer.
	 * 
	 * @param eps
	 *            maximum radius of the neighborhood to be considered
	 * @param minPts
	 *            minimum number of points needed for a cluster
	 * @param measure
	 *            the distance measure to use
	 * @throws IllegalArgumentException
	 *             if {@code eps < 0.0} or {@code minPts < 0}
	 */
	public DBSCANClusterer(final List<I> points,final DistanceFunction<I> measure,final double eps, final int minPts)
			 {
		super(points,measure);

		if (eps < 0.0d) {
			throw new IllegalArgumentException("" + eps);
		}
		if (minPts < 0) {
			throw new IllegalArgumentException("" + minPts);
		}
		this.eps = eps;
		this.minPts = minPts;
	}

	/**
	 * Returns the maximum radius of the neighborhood to be considered.
	 * 
	 * @return maximum radius of the neighborhood
	 */
	public double getEps() {
		return eps;
	}

	/**
	 * Returns the minimum number of points needed for a cluster.
	 * 
	 * @return minimum number of points needed for a cluster
	 */
	public int getMinPts() {
		return minPts;
	}

	/**
	 * Performs DBSCAN cluster analysis.
	 * 
	 * @param points
	 *            the points to cluster
	 * @return the list of clusters
	 * @throws IllegalArgumentException
	 *             if the data points are null
	 */
	public Clusters<I,Cluster<I>> cluster(){

		final List<Cluster<I>> clusters = new ArrayList<Cluster<I>>();
		final Map<Instance, PointStatus> visited = new HashMap<Instance, PointStatus>();

		for (final I point : this.elements) {
			if (visited.get(point) != null) {
				continue;
			}
			final List<I> neighbors = getNeighbors(point, this.elements);
			if (neighbors.size() >= minPts) {
				// DBSCAN does not care about center points
				final Cluster<I> cluster = new Cluster<I>();
				clusters.add(expandCluster(cluster, point, neighbors, this.elements,
						visited));
			} else {
				visited.put(point, PointStatus.NOISE);
			}
		}

		return new Clusters<I,Cluster<I>>(clusters);
	}

	/**
	 * Expands the cluster to include density-reachable items.
	 * 
	 * @param cluster
	 *            Cluster to expand
	 * @param point
	 *            Point to add to cluster
	 * @param neighbors
	 *            List of neighbors
	 * @param points
	 *            the data set
	 * @param visited
	 *            the set of already visited points
	 * @return the expanded cluster
	 */
	private Cluster<I> expandCluster(final Cluster<I> cluster, final I point,
			final List<I> neighbors, final Collection<I> points,
			final Map<Instance, PointStatus> visited) {
		cluster.getElements().add(point);
		visited.put(point, PointStatus.PART_OF_CLUSTER);

		List<I> seeds = new ArrayList<I>(neighbors);
		int index = 0;
		while (index < seeds.size()) {
			final I current = seeds.get(index);
			PointStatus pStatus = visited.get(current);
			// only check non-visited points
			if (pStatus == null) {
				final List<I> currentNeighbors = getNeighbors(current, points);
				if (currentNeighbors.size() >= minPts) {
					seeds = merge(seeds, currentNeighbors);
				}
			}

			if (pStatus != PointStatus.PART_OF_CLUSTER) {
				visited.put(current, PointStatus.PART_OF_CLUSTER);
				cluster.getElements().add(current);
			}

			index++;
		}
		return cluster;
	}

	/**
	 * Returns a list of density-reachable neighbors of a {@code point}.
	 * 
	 * @param point
	 *            the point to look for
	 * @param points
	 *            possible neighbors
	 * @return the List of neighbors
	 */
	private List<I> getNeighbors(final I point, final Collection<I> points) {
		final List<I> neighbors = new ArrayList<I>();
		for (final I neighbor : points) {
			if (point != neighbor
					&& this.distanceFunction.distance(neighbor, point) <= eps) {
				neighbors.add(neighbor);
			}
		}
		return neighbors;
	}

	/**
	 * Merges two lists together.
	 * 
	 * @param one
	 *            first list
	 * @param two
	 *            second list
	 * @return merged lists
	 */
	private List<I> merge(final List<I> one, final List<I> two) {
		final Set<I> oneSet = new HashSet<I>(one);
		for (I item : two) {
			if (!oneSet.contains(item)) {
				one.add(item);
			}
		}
		return one;
	}


}
