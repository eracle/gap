# Gap
## Distance Based clustering library

## Introduction
This is an embryonal clustering project, composed by:
- Generic Distance Function class;
- Jaccard distance function class;
- K-Medoids algorithm;
- Modified version of the Apache Commons Math3 DBSCAN algorithm
- Some test classes.
- Old stuff

## Why This?
There are some data domains where new kind of issues are arising, for example:
- High dimensionality of data;
- Heterogeneity and Complex Data Types.

The idea beyond this project was that the instances to be clustered don't have to be vectors or database records.

What about others libraries?
In others Clustering libraries (such as Weka) the distance functions used are all based on vectors like instances, for example Euclidean Distance or Manhattan Distance. There the distance computation phase can spend a lot of time scanning the entire vector of attributes of an instance; for example if we have a standard tf-idf representation of a document, during the computation of the cosine similarity, the distance function scans all the possible words that appears in the whole collection of documents, that in many cases can be made by millions of entries.
That's why, in this domain, some [authors][1] suggests to restrict the words to a subset of the N most frequent words of the language.

There are many domains where is considerably better to abstract the notion of distance:
- Clustering of the nodes of a graph;
- Car driving behaviour, represented as GPS sequences;

## Classes:
- Instance interface:

	Interface of the object which have to be clustered.

- Jaccard distance function:

	Is defined over pairs of instances, both have to extend the Set interface and the Instance interface.

- K Medoids clustering algorithm:

	Main clustering algorithm, it needs:
	- List of Instances;
	- Distance Function;
	- Number of clusters needed (the k value).

	Every cluster obtained have at least an element which is the representative object of the cluster (from now on the "Medoid"). The algorithm tries to minimize the sum of the distances between every instance and the Medoid of its cluster.

- Apache Commons Math3 DBSCAN algorithm:

	This module is a slightly modified version of the Apache Commons Math3 library DBSCAN algorithm, it was modified in such a way that is compliant with the generic interfaces of this library.

## History
The initial code of the K-Medoids algorithm was developed for solving a news clustering task assigned in the class of Information Retrieval, taught by Prof. Paolo Ferragina from University of Pisa.

Author: [Antonio Ercole De Luca][3]




## Todo:
- Distances caching classes;
- Distances Matrix classes (as a distance function);
- Distances Matrix [graphical plotting][2], either with the instances already clustered;
- Unit tests;
- Modify the Apache DBSCAN source code in order to return an additional cluster (the one with the NOISE labelled instances);


## Bibliography:

[1]: http://nlp.stanford.edu/IR-book/pdf/irbookonlinereading.pdf
[2]: http://en.wikipedia.org/wiki/Distance_matrix
[3]: http://www.linkedin.com/pub/antonio-ercole-de-luca/1b/340/197
