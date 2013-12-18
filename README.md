# Gap
## Distance Based clustering library

## Introduction
This is a newborn clustering repository, actually in an enbrional phase, composed by:
- Generic Distance Function class;
- Jaccard distance function class;
- K-Medoids algorithm;
- Some test classes.

##Why This?
In many domanins there are rising some new issues to deal with, for example:
- High dimentionality of data;
- Heterogeneousity and Complex Data Types.

Thats why I started this project which abstract the idea that the instances to be clustered maybe are not database records.
Another reason why is I consider this project an hobby, which development can enrich myself on this beloved field: Data Mining.

### What about others libraries?
In standard clustering algorithms, for instance Weka's k-means, to deal with clustering object task they uses Distances Function such as Euclidean Distance or Manhattan Distance. This assumption doesn't fits well with Complex Data Type and High dimentionality and constrict us to consider instances as vectors.

#### High dimentionality problem
Using standard clustering libraries and k-means algorithm, the distance computation phase can spend a lot of time scanning the entire vector of attributes that belongs to an instance; for instance if we have a standard tf-idf rappresentation of a document, during the computation of the cosine similarity, the distance function scans all the possibile words that happears in the documents, that in many cases can be made by millions of entries.
This is why, in this domain, somes [1] suggests to restrict the words considered to a subset of N most frequent word of that language. 

#### Heterogeneousity and Complex Data Types.
There are many objects domains where is considerably better to use a different implementation of an object.
Another intresting domain of clustering, which the objects are not naturally suitable for an array of attributes implementation is graph's nodes clustering, which the objects to be clustered are the nodes of a graph.


## Modules:
Follows some shorts introductions over the modules developed:

### Instance interface
Every custom class, which must be clustered, must extends this interface.

### Jaccard distance function
Is defined over pairs of instances, each one must extends the Set interface and the Instance interface.

### KMedoids clustering algorithm
Is actually the main clustering algorithm developed.
To be executed needs:
- a List of Instances; 
- a distanceFunction;
- the number of clusters needed (the k value).

Every cluster obtained has an element which is the rappresentative object of the cluster (from now "Medoid").
This algorithm tries to minimize the sum of the distances between every instance and the Medoid of his cluster.

## History
The initial code of the K-Medoids algorithm was developed for solving a news clustering task assigned in the course of Information Retrieval, by Paolo Ferragina @ Università di Pisa.

Author: Antonio Ercole De Luca 
http://www.linkedin.com/pub/antonio-ercole-de-luca/1b/340/197.


## Todo:
Tested and well implemented:
- Caching system;
- Distance Matrix classes;
- All the modules's test classes.

##Bibliography:
[1] Christopher D. Manning, Prabhakar Raghavan, and Hinrich Schütze. 2008. Introduction to Information Retrieval. Cambridge University Press, New York, NY, USA.
