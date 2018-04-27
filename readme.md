# Gap
Very old, Data Mining library with special focus on Clustering of non-vector instances. 
Developed for didactical reasons, and never taken too much seriuosly :-) 

## Motivation
There are some clustering domains where use vector based representation of instances is not useful.
By using alternatives instances' representations is also possible to use custom metrics (even not metrics) distance functions.
All algorithms implemented do not rely on any vector values.
For instance, domains where there is:
- High dimensionality of data;
- Heterogeneity or Complex Data Types.

Where the vector representations lie on high dimensional domains, compute distances between objects (or similarity) is inefficent, since it takes too time much time for scan the entire vector of attributes on each instance. 

There are some clustering domains where can be interesting to abstract the classical notion of distance, since the objects are not vectors at all:
- Nodes of a Graph:
    Where the distances between nodes is represented as their shortest path or the number of shared neighbors.
- Car driving behaviours, represented as time-labelled GPS sequences:
    Where their distance measure can be implemented as a customized Euclidean Distance (but not necessary a metric).
- Documents, and their bag-of-words representation:
    Some [authors][1] suggests to restrict the words, used to compute BOW representation, to a subset of the N most frequent words of the issued language.


### Alternatives:
- [Weka][1]:
    In [Weka][1] the distance functions used are all based on vector alike instances, for example Euclidean Distance or Manhattan Distance. 
- [Elki][7]:
    Very interesting library, maybe too much complicated for the purposes of this project.


## History:
This project was started as assignment a University's class, its overall objective was to perform news clustering.
The course's name was Information Retrieval, taught by Prof. [Paolo Ferragina][6] from University of Pisa.
After graduation, I refined it during some freelance jobs obtained on [UpWork][5]. 


## Details:
- Jaccard Similarity;
- Mutual Information Similarity;
- K-Medoids algorithm.

    
#### Classes:
In order to extend the [JavaML](http://java-ml.sourceforge.net/) library, I implemented some customized classes:

- MiDistanceMatrix:
    Main class where are implemented the functionality described in this readme file;
    
- it.ci.JavaMlAdaptors.DMDistance:
    Customized distance function, it basically wraps a matrix that contains the distances between couples of Instance objects.

- it.ci.JavaMlAdaptors.DMInstance:
    Customized Instance object, it simply contains an integer which is the index used for retrieving all the distances between the Instance and the rest of the dataset into the distance matrix.    

- it.ci.JavaMlAdaptors.DMDataset:
    Customized Dataset class, it simply allows to create a dataset of it.ci.JavaMlAdaptors.DMInstance objects; 

- it.ci.JavaMlAdaptors.DMKMedoids:
    Extends the net.sf.javaml.clustering.KMedoids class. I simply overridden a method: recalculateMedoids, which is the phase where, for each cluster, the medoid is recomputed. 
    Since its superclass computed a mean value between all the instances of each cluster and then the closest instance of the cluster to the mean value is chosen, and I was adapting the algorithm to non-vector based instances and the only allowed operation was to compute distances between them, so I solved it by overriding the method, by implementing an algorithm that finds for each cluster the Instance which minimizes the sum of the distance toward all the other cluster's Instances.
	By doing so, like the KMedoids classical algorithm, every cluster ends up with a representative element, from now on the "Medoid", and chooses it by minimizing the sum of the distances between every cluster's instance and the candidate Medoid.

- it.ci.SimilarityMeasures.JaccardSimilarity
	Is defined over pairs of instances, both have to extend both the Set interface and the Instance interface.


# Usage: 
## Dimentionality Reduction via Mutual Information
Simple script which reads from an arff file with N attributes:
1. Computes a distance matrix, composed by N x N entries, each entry is composed by the Mutual Information Distance between a pair of attributes of the datase, obtained by their Mutual Information Similarity;
2. Clusters the attributes in k groups using k-medoids algorithm;
3. For each attributes cluster discovers the medoid attribute and its name is printed.

In order to compute the mutual information values and execute the clustering of the attributes, I used some open source libraries:

- [Weka][4]:
    Used for opening the arff files;
    
- [JavaMI](https://github.com/Craigacp/JavaMI):
    Used for computing the Mutual Information between every pair of attributes in the dataset;
    
- [JavaML](http://java-ml.sourceforge.net/):
    Used for implement a clustering algorithm based on a distance matrix, I imported the source code and I slightly changed it in order make it easily extendable with my custom implementations of the clustering classes.
   
# Install

## Ubuntu:
I used 14.04 and Java 7 (version "1.7.0_111" - OpenJDK Runtime Environment (IcedTea 2.6.7)).
It works for me.

##### Building
```
./gradlew build
```

##### Usage
Type whose command from the project directory:
```
./gradlew clean
./gradlew fatJar


java -jar ./build/libs/gap-all*.jar -f /path/to/file.arff -i 10 -k 5
```
Where -i is the option for the maximum number of iterations of the k-medoids algorithm and k is the number of cluster searched.

Or:
```
bash start.sh
```

## Windows:

##### Usage:
Open the command [prompt](http://www.digitalcitizen.life/7-ways-launch-command-prompt-windows-7-windows-8) and type the following commands:

```
# Change directory to the project's one
cd \directory\where\is\located\the\project\gap

# compile the code and build the jar (executable) file
.\gradlew fatJar

# execute che the program contained in the jar file, change the arff file location and the numbler of desired clusters (the number after the -k )
java -jar .\build\libs\gap-all-1.0-SNAPSHOT.jar -f .\path\to\file.arff -i 10 -k 5


```

## Todo:
- Distances Matrix [graphical plotting][2], either with the instances already clustered;
- Interfacing with knn-libraries (cite please) in order to use it as an efficient way to retrieve close instances.



[1]: http://nlp.stanford.edu/IR-book/pdf/irbookonlinereading.pdf
[2]: http://en.wikipedia.org/wiki/Distance_matrix
[3]: http://www.linkedin.com/pub/antonio-ercole-de-luca/1b/340/197
[4]: http://www.cs.waikato.ac.nz/ml/weka/
[5]: https://www.upwork.com/
[6]: http://pages.di.unipi.it/ferragina/
[7]: http://elki.dbs.ifi.lmu.de/
