package cosc202.andie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.*;
//import java.util.Arrays;
import java.text.DecimalFormat;
import java.text.NumberFormat;


//import cosc202.andie.ImagePanel;



/*
 * <p>
 * Test class to test the Gaussian Blur filter, has expected output and test output.
 * Test is to ensure that the expected output and test output are identical
 * </p>
 * 
 * @author Ilgaz Celik
 * @author Finn Rimmer
 * @version 1.0
 * 
 */
public class GaussianBlurTest {
    

    /**
     *  <p>
     * Test method {@link GaussianBlur}
     * </p>
     */
    @Test
    void getGaussianArrayValues(){
        BufferedImage tester = new BufferedImage(10,10,10);
        GaussianBlur testBlur = new GaussianBlur(1);
        testBlur.apply(tester);
        NumberFormat df = new DecimalFormat("0.000");
        double[] nums = new double[testBlur.array.length];
        double[] compare = {0.000,0.011,0.000,0.011,0.957,0.011,0.000,0.011,0.000};
        for (int i = 0;i<nums.length;i++){
            nums[i]=Double.parseDouble(df.format(testBlur.array[i]));
        }
        Assertions.assertArrayEquals(compare, nums);

    }
}
