package it.ci.DistanceMatrix;

import it.ci.MutualInformationMain;
import it.ci.SimilarityMeasures.MI.MutualInformationSimilarity;
import org.junit.Assert;
import org.junit.Test;
import weka.core.Instances;

import java.io.File;

/**
 * Created by eracle on 21/09/16.
 */
public class DistanceMatrixTest {




    @Test
    public void Constructor_Test_ionosphere_dataset() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ionosphere.arff").getFile());
        Instances data = MutualInformationMain.readArff(file);

        DistanceMatrix matrix_obj = new DistanceMatrix(data,new MutualInformationSimilarity());
        matrix_base_verifications(matrix_obj.distanceMatrix);
        System.out.println(matrix_obj.toString());
    }

    @Test
    public void Constructor_Test_simple_dataset() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("null_pointer_bug.arff").getFile());
        Instances data = MutualInformationMain.readArff(file);

        DistanceMatrix matrix_obj = new DistanceMatrix(data,new MutualInformationSimilarity());
        double[][] matrix = matrix_obj.distanceMatrix;

        matrix_base_verifications(matrix);

        System.out.println(matrix_obj.toString());
    }

    private void matrix_base_verifications(double[][] matrix){
        Assert.assertTrue(matrix!=null);

        for (int i = 0; i < matrix.length;i++ ){
            Assert.assertTrue("verifing diagonal is zero", matrix[i][i] == 0 );
        }


        for (int i = 0; i < matrix.length;i++ ){
            for (int j = 0; j < matrix.length;j++ ){
                Assert.assertTrue("verifing simmetry", matrix[i][j] == matrix[j][i]);
            }
        }
    }

}