Gap - DistanceMatrix Based Data Mining Library
===

Newborn Data Mining repository.


Initialy composed by the K-Medoids cluster algorithm developed for the course Information Retrieval @ Università di Pisa by Antonio Ercole De Luca http://www.linkedin.com/pub/antonio-ercole-de-luca/1b/340/197.

Introduction
===
In standard clustering algorithms, for instance Weka's k-means, to deal with clustering object task they uses Distances Function such as Euclidean Distance or Manhattan Distance.
With that kind of assumption we constrict our self to using databases instances.
This Repository tries to abstract the concept of object to be clustered which is not a database record.

During the distance computation of a clustering algorithm, standard clustering libraries can spend a lot of time scanning the entire list of attributes that belongs to an instance, for example if we have a standard tf-idf rappresentation of a document, during the computation of the cosine similarity, it scans all the possibile word of the dictionary, that in many cases can be made by millions of entries.
There are many objects domains where is considerably better to use a different implementation of an object.
Another intresting domain of clustering, which the objects are not naturally suitable for an array of attributes implementation is graph's nodes clustering, which the objects to be clustered are the nodes of a graph.


K-Medoids
===
It needs only a Distance Function between two objects to perform the clustering task.
In other words it tries to minimize the global distance between every object and the object choosen to be the rappresentative object of the cluster (aka Medoid).
