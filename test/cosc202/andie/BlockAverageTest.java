package cosc202.andie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.*;

/**
 * Unit test for the block average filter
 * Author Aiden O'Brien
 */
public class BlockAverageTest {
    
    /**
     * Creates a buffered image and applies the block average filter to it
     * Checks whether each pixel in the specified radius has the same pixel argb value
     */
    @Test
    public void getBlockAveraged() {
        BufferedImage tester = new BufferedImage(5,5,10);
        BlockAverage testAvg = new BlockAverage(5,5);
        testAvg.apply(tester);
        
        int argb = tester.getRGB(0, 0);
        for (int i=1;i<5;i++){
            for (int j=1; j<5; j++) {
                int argb2 = tester.getRGB(i,j);
                Assertions.assertEquals(argb,argb2);
            }
        }
        
    }
}
