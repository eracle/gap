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

##Why This?
There are some data domains where new kind of issues are arising, for example:
- High dimensionality of data;
- Heterogeneity and Complex Data Types.

I started this project which abstracts the idea that the instances to be clustered do not have necessary to be vector instances or database records.

### What about others libraries?
In others Clustering libraries (such as Weka) the distance functions used are all based on vector like instances, for example Euclidean Distance or Manhattan Distance. 

#### High dimensionality problem
In some implementations of clustering libraries and k-means algorithm, the distance computation phase can spend a lot of time scanning the entire vector of attributes that belongs to an instance; for instance if we have a standard Tf-Idf representation of a document, during the computation of the cosine similarity, the distance function scans all the possible words that appears in the whole collection of documents, that in many cases can be made by millions of entries.
This is why, in this domain, some authors [1] suggests to restrict the words considered to a subset of N most frequent word of that language. 

#### Heterogeneity and Complex Data Types.
There are many domains where is considerably better to abstract the notion of distance:
- Clustering of node of a graph;
- Car driving behaviour, represented as GPS sequences;

## Modules:
#### Instance interface
Interface of the object which have to be clustered.

#### Jaccard distance function
Is defined over pairs of instances, both have to extend the Set interface and the Instance interface.

#### K Medoids clustering algorithm
Main clustering algorithm developed.
To be executed needs:
- a List of Instances; 
- a distance Function;
- the number of clusters needed (the k value).
Every cluster obtained at least an element which is the representative object of the cluster (from now on "Medoid").
This algorithm tries to minimize the sum of the distances between every instance and the Medoid of its cluster.

#### Apache Commons Math3 DBSCAN algorithm  
This module is a slightly modified version of the Apache Commons Math3 library DBSCAN algorithm.
The only modifies done were the ones needed to the class to be compliant with the generic interfaces of this library. 

## History
The initial code of the K-Medoids algorithm was developed for solving a news clustering task assigned in the course of Information Retrieval, taught by Paolo Ferragina, Università di Pisa.

Author: Antonio Ercole De Luca 
http://www.linkedin.com/pub/antonio-ercole-de-luca/1b/340/197.



## Todo:
Tested and well implemented:
- Distances caching classes;
- Distances Matrix classes (as a distance function);
- Distances Matrix graphical plotting, either with the instances already clustered; [2]
- Unit tests;
- Modify the Apache DBSCAN source code in order to automatically return an additional cluster (the one with the NOISE labelled instances);


##Bibliography:
[1] Christopher D. Manning, Prabhakar Raghavan, and Hinrich Schütze. 2008. Introduction to Information Retrieval. Cambridge University Press, New York, NY, USA.

[2] http://en.wikipedia.org/wiki/Distance_matrix
