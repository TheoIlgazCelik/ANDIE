package cosc202.andie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.*;

/**<p>
 * Test class for Unit test of the block average filter. Ensures it works correctly
 * </p>
 * @author Aiden O'Brien
 * @version 1.0
 */
public class BlockAverageTest {
    
    /**
     * <p>
     * Creates a buffered image and applies the block average filter to it
     * Checks whether each pixel in the specified radius has the same pixel argb value
     * </p>
     */
    @Test
    public void getBlockAveraged() {
        int radius = 5;
        BufferedImage tester = new BufferedImage(10,10,10);
        BlockAverage testAvg = new BlockAverage(radius,radius);
        testAvg.apply(tester);
        
        int argb = tester.getRGB(0, 0);
        for (int i=1;i<radius;i++){
            for (int j=1; j<radius; j++) {
                int argb2 = tester.getRGB(i,j);
                Assertions.assertEquals(argb,argb2);
            }
        }
        
    }
}
