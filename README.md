# Gap
## Distance Based clustering library

## Introduction
This is a newborn clustering repository, actually is in an enbrional phase, composed by:
- Generic Distance Function class;
- Jaccard distance function class;
- K-Medoids algorithm;
- Some test classes.

##Why This?
In many domains there are rising some new issues to deal with, for example:
- High dimentionality of data;
- Heterogeneousity and Complex Data Types.

To deal with those issues I started this project which abstracts the idea that the instances to be clustered could not be database records.
Another reason why I started this project is considering this project as an hobby, which development can enrich myself on the field of Data Mining.

### What about others libraries?
In Weka's K-Means and in other clustering algorithms, in order to perform the task, is used Distance Functions such as Euclidean Distance or Manhattan Distance. This assumption doesn't fits well with Complex Data Type and High dimentionality and constrict the user to consider instances as vectors.

#### High dimentionality problem
In standard clustering libraries and k-means algorithm, the distance computation phase can spend a lot of time scanning the entire vector of attributes that belongs to an instance; for instance if we have a standard Tf-Idf rappresentation of a document, during the computation of the cosine similarity, the distance function scans all the possibile words that appears in the whole collection of documents, that in many cases can be made by millions of entries.
This is why, in this domain, some authors [1] suggests to restrict the words considered to a subset of N most frequent word of that language. 

#### Heterogeneousity and Complex Data Types.
There are many domains where is considerably better to abstract the implementation of an instance:
- Graph's nodes clustering;
- Car driving beahaviour, represented as GPS routes;

## Modules:
Follows some shorts introductions over the modules developed:

#### Instance interface
Every custom class, which must be clustered, must extends this interface.

#### Jaccard distance function
Is defined over pairs of instances, each one must extends the Set interface and the Instance interface.

#### KMedoids clustering algorithm
Is actually the main clustering algorithm developed.
To be executed needs:
- a List of Instances; 
- a distanceFunction;
- the number of clusters needed (the k value).
Every cluster obtained has an element which is the rappresentative object of the cluster (from now "Medoid").
This algorithm tries to minimize the sum of the distances between every instance and the Medoid of his cluster.

#### Apache Commons Math3 DBSCAN algorithm  
This module is a slightly modified version of the Apache Commons Math 3 library DBSCAN class algorithm.
The only modifies done are the ones needed to the class to be compliant with the generic interfaces of this library. 

## History
The initial code of the K-Medoids algorithm was developed for solving a news clustering task assigned in the course of Information Retrieval, tought by Paolo Ferragina, Università di Pisa.

Author: Antonio Ercole De Luca 
http://www.linkedin.com/pub/antonio-ercole-de-luca/1b/340/197.



## Todo:
Tested and well implemented:
- Caching system;
- Distance Matrix classes (as a distance function);
- Distance Matrix graphical plotting, either with the instances already clustered; [2]
- All the modules's test classes;
- Modify the Apache DBSCAN source code in order to automatically return an additional cluster (the one with the NOISE labelled instances).



##Bibliography:
[1] Christopher D. Manning, Prabhakar Raghavan, and Hinrich Schütze. 2008. Introduction to Information Retrieval. Cambridge University Press, New York, NY, USA.

[2] http://en.wikipedia.org/wiki/Distance_matrix
