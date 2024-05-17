package cosc202.andie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.*;

import cosc202.andie.ResizeImage;

public class ResizeImageTest {
    
    @Test
    void getResizeImageValues(){
        BufferedImage tester = new BufferedImage(100,100,10);
        int testPercentage = 80;
        ResizeImage testResize = new ResizeImage(testPercentage);
        BufferedImage result = testResize.apply(tester);
        Assertions.assertEquals(Math.round(result.getWidth()*result.getHeight()/Math.pow(((double)(testPercentage)/100),2)), 10000);
    }
}
