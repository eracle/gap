package it.ci;

import be.abeel.util.Pair;
import it.ci.DistanceMatrix.DistanceMatrix;
import it.ci.JavaMlAdaptors.DMDataset;
import it.ci.JavaMlAdaptors.DMInstance;
import it.ci.JavaMlAdaptors.DMKMedoids;
import it.ci.JavaMlAdaptors.DMDistance;
import it.ci.SimilarityMeasures.MI.MutualInformationSimilarity;
import net.sf.javaml.clustering.KMedoids;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import org.apache.commons.cli.*;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.exit;

/**
 * Created by eracle on 07/09/16.
 */
public class MutualInformationMain {

    private static final Logger LOGGER = Logger.getLogger( MutualInformationMain.class.getName() );
    static{
        LOGGER.setLevel(Level.INFO);
    }

    /**
     * Read the arff file provided as parameter and returns the dataset as a WEKA's Istances object.
     * @param file  file to be opened
     * @return  The dataset as a WEKA's Istances object
     * @throws IOException if something wrong
     */
    public static Instances readArff(File file) throws IOException {


        LOGGER.log( Level.INFO, "Reading the arff file, which path: {0} ", file.getAbsolutePath() );

        BufferedReader reader =
                new BufferedReader(new FileReader(file));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();
        //data.setClassIndex(data.numAttributes() - 1);

        LOGGER.log( Level.FINE, "Read {0} instances", data.numInstances() );

        return data;
    }





    /**
     * Perform the clustering, returns a pair of objects:
     * first is the Dataset[] containing the array of clusters;
     * second is the Instance[] containing the array of medoids.
     * The order matters!
     *
     * @param matrix the distance matrix
     * @param k     the number of cluster needed
     * @param max_iter maximum number of iteration of the algorithm
     * @return
     */
    public static Pair matrixClustering(double[][] matrix, int k, int max_iter ){
        LOGGER.log( Level.FINE, "Starting clustering, k: {0}", k);



        DMDataset custom_dataset = new DMDataset(matrix);

        KMedoids algo = new DMKMedoids(k, max_iter, new DMDistance(matrix));
        Dataset[] clusters = algo.cluster(custom_dataset);

        return new Pair(clusters,custom_dataset.medoids);


    }


    public static void main(String [ ] args) throws ParseException, IOException {



        // create Options object
        Options options = new Options();

        options.addOption("f", true, "arff file location");

        options.addOption("k", true, "number of clusters");

        options.addOption("i", true, "maximum number of iterations");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( options, args);

        if(!cmd.hasOption("f")){
            System.out.println("arff file location not given");
            exit(1);
        }
        if(!cmd.hasOption("k")){
            System.out.println("number of cluster not given");
            exit(1);
        }

        int max_iter;
        if(!cmd.hasOption("i")){
            System.out.println("number of iterations not given, using 10");
            max_iter = 10 ;
        }else{
            max_iter = Integer.parseInt(cmd.getOptionValue("i"));
        }


        String arff_file_dir = cmd.getOptionValue("f");
        File arff_file = new File(arff_file_dir);

        int k = Integer.parseInt(cmd.getOptionValue("k"));



        Instances data = MutualInformationMain.readArff(arff_file);
        MutualInformationSimilarity sim = new MutualInformationSimilarity();
        DistanceMatrix matrix_obj = new DistanceMatrix(data,sim);
        double[][] matrix = matrix_obj.distanceMatrix;

        Pair pair_return = MutualInformationMain.matrixClustering(matrix,k,max_iter);

        Dataset[] clusters = (Dataset[]) pair_return.x();

        Instance[] medoids = (Instance[]) pair_return.y();


        System.out.println("Lets print distance matrix:");
        System.out.println(matrix_obj.toString());


        System.out.println("Lets print the medoids:");

        System.out.println("Cluster_number, mumber_of_instances, medoid_name");
        for(int i = 0; i<k;i++){
            if(clusters[i].size()>0) {
                System.out.print(i + ",");
                System.out.print(clusters[i].size() + ",");
                DMInstance medo = (DMInstance) medoids[i];
                System.out.print(data.attribute(medo.getID()).name());
                System.out.println("");
            }
        }

    }


}
