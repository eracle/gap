package it.ci.DistanceMatrix;

import be.abeel.util.Pair;
import it.ci.MutualInformationMain;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.Test;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;

/**
 * Created by eracle on 07/09/16.
 */
public class MutualInformationMainTest {
    @Test
    public void readArff() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ionosphere.arff").getFile());
        Instances data = MutualInformationMain.readArff(file);
        Assert.assertTrue(data!=null);
    }




    @Test
    public void main() throws IOException, ParseException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ionosphere.arff").getFile());


        String args[] = new String[6];
        args[0] = "-k";
        args[1] = "5";
        args[2] = "-i";
        args[3] = "10";
        args[4] = "-f";
        args[5] = file.getAbsolutePath();

        MutualInformationMain.main(args);
    }

    @Test
    public void main_nullpointer() throws IOException, ParseException {
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource("null_pointer_bug.arff").getFile());


        String args[] = new String[6];
        args[0] = "-k";
        args[1] = "5";
        args[2] = "-i";
        args[3] = "10";
        args[4] = "-f";
        args[5] = file.getAbsolutePath();

        MutualInformationMain.main(args);
    }



    @Test
    public void matrixClustering(){
        /*
        Test matrix:
        A and B close, C and D close.

        A       B       C       D
     _______________________________
	|                               |
A	|   0       0.1     10      10	|
    |   							|
B	|   0.1     0       10      10	|
    |   							|
C	|   10      10      0       0.1	|
    |   							|
D	|   10             0.1     0	|
    |_______________________________|

         */


        double[][] m = new double[4][4];
        m[0][0] = 0;
        m[0][1] = 0.1;
        m[0][2] = 10;
        m[0][3] = 10;

        m[1][0] = 0.1;
        m[1][1] = 0;
        m[1][2] = 10;
        m[1][3] = 10;

        m[2][0] = 10;
        m[2][1] = 10;
        m[2][2] = 0;
        m[2][3] = 0.1;


        m[3][0] = 1;
        m[3][1] = 1;
        m[3][2] = 0.1;
        m[3][3] = 0;

        Pair p = MutualInformationMain.matrixClustering(m,2,10);
        Dataset[] clusters = (Dataset[]) p.x();
        Instance[] medoids = (Instance[]) p.y();

        Assert.assertTrue(medoids.length==2);
        Assert.assertTrue(clusters.length==2);
        for(Dataset c:clusters){
            System.out.println(c.toString());
            Assert.assertTrue(c.size()==2);
        }
    }


}