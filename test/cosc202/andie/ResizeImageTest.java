package cosc202.andie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.*;



/**
 * <p>
 * Test class to test the image resize function. To assure that the resized output image 
 * has the correct dimensions
 * </p>
 * 
 * @author Ilgaz Celik
 * @version 1.0
 */
public class ResizeImageTest {
    
    /**
     * <p>
     * Has test percentage value and checks to see if the outputted image has the correct
     * dimensions when resized to the percentage value
     * </p>
     */
    @Test
    void getResizeImageValues(){
        BufferedImage tester = new BufferedImage(100,100,10);
        int testPercentage = 80;
        ResizeImage testResize = new ResizeImage(testPercentage);
        BufferedImage result = testResize.apply(tester);
        Assertions.assertEquals(Math.round(result.getWidth()*result.getHeight()/Math.pow(((double)(testPercentage)/100),2)), 10000);
    }
}
