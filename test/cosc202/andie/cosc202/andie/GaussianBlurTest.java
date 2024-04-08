package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.*;
import java.util.Arrays;

import cosc202.andie.GaussianBlur;
import cosc202.andie.ImagePanel;

public class GaussianBlurTest {
    
    @Test
    void getGaussianArrayValues(){
        BufferedImage tester = new BufferedImage(10,10,10);
        GaussianBlur testBlur = new GaussianBlur(1);
        testBlur.apply(tester);
        System.out.println(Arrays.toString(testBlur.array));
        float[] compare = {0.0f,0.01f,0.0f,0.01f,0.957f,0.01f,0.0f,0.01f,0.0f};

        Assertions.assertEquals(compare, testBlur.array);

    }
}
