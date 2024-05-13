package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

// JUnit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import cosc202.andie.Convolution;

public class ConvolutionTest {

    @Test
    void TestMeanFilter() {
        // create test "image" of 1000x1000 argb pixels
        int width = 1000;
        int height = 1000;
        BufferedImage input = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // generate random rgbs for image
        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                int a = (int) Math.random() * 255;
                int r = (int) Math.random() * 255;
                int g = (int) Math.random() * 255;
                int b = (int) Math.random() * 255;

                int argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }

        // create output images to convolve
        BufferedImage andieOutput = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        BufferedImage javaOutput = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);

        // test kernel data - 3x3 mean filter
        int radius = 1;
        int size = (2 * radius + 1) * (2 * radius + 1);
        float[] array = new float[size];
        Arrays.fill(array, 1.0f / size);

        // andie/java convolution objects
        Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, array);
        Convolution andieC = new Convolution(kernel);
        ConvolveOp javaC = new ConvolveOp(kernel);

        // apply convolution
        andieC.filter(input, andieOutput);
        javaC.filter(input, javaOutput);

        // arrays to store data to compare andie/java results
        int[] andieData = new int[(andieOutput.getWidth() - (2 * radius)) * (andieOutput.getHeight() - (2 * radius))];
        int[] javaData = new int[andieData.length];

        // iterate through image and store data, ignoring borders (which java ignores)
        int i = 0;
        for (int y = radius; y <= andieOutput.getHeight() - (1 + radius); y++) {
            for (int x = radius; x <= andieOutput.getWidth() - (1 + radius); x++) {
                andieData[i] = andieOutput.getRGB(x, y);
                javaData[i] = javaOutput.getRGB(x, y);
                if (andieData[i] != javaData[i]) {
                    System.out.println(andieData[i] + " : " + javaData[i]);
                }
                i++;

            }
        }

        assertArrayEquals(andieData, javaData);

    }

}
