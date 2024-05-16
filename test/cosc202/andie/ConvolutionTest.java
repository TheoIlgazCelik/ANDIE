package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * <p>
 * Test class to test all {@link ImageOperation}s that use convolution via
 * kernel, to assure output between javas {@link ConvolveOp} and ANDIEs
 * {@link Convolution} is identical.
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class ConvolutionTest {
    /**
     * These three images are "set up" before each individual test in the
     * {@link #setUpInputAndOutputImages} method.
     */
    private BufferedImage input;
    private BufferedImage andieOutputImage;
    private BufferedImage javaOutputImage;

    /**
     * <p>
     * This is run before each indivdual test, to set up the three
     * {@link BufferedImage} data fields. The input data field is set up as an image
     * of randomly generate argb pixels, then the andieOutput and javaOutput are
     * copied from the input.
     * </p>
     */
    @BeforeEach
    void setUpInputAndOutputImages() {
        // create test "image" of 1000x1000 argb pixels
        int width = 1000;
        int height = 1000;
        input = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // generate random rgbs for image
        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                int a = (int) Math.random() * 256;
                int r = (int) Math.random() * 256;
                int g = (int) Math.random() * 256;
                int b = (int) Math.random() * 256;

                int argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }

        // create output images to convolve
        andieOutputImage = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);

        javaOutputImage = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);

    }

    /**
     * <p>
     * This is run before each indivdual test, to set up the three
     * {@link BufferedImage} data fields. The input data field is set up as an image
     * of randomly generate argb pixels, then the andieOutput and javaOutput are
     * copied from the input.
     * </p>
     * 
     * @param kernelData Convolution kernel data.
     * @param radius Radius of convolution kernel. 
     */
    void convolveAndTestData(float[] kernelData, int radius) {
        Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, kernelData);

        Convolution andieConvolveOp = new Convolution(kernel);
        ConvolveOp javaConvolveOp = new ConvolveOp(kernel);

        // apply convolution
        andieConvolveOp.filter(input, andieOutputImage);
        javaConvolveOp.filter(input, javaOutputImage);

        // arrays to store data to compare andie/java results
        int numPixelsExcludingBorders = (andieOutputImage.getWidth() - (2 * radius)) * (andieOutputImage.getHeight() - (2 * radius));

        int[] andieOutputImageData = new int[numPixelsExcludingBorders];
        int[] javaOutputImageData = new int[numPixelsExcludingBorders];

        // iterate through image and store data, ignoring borders (which java ignores)
        int i = 0;
        for (int y = radius; y <= andieOutputImage.getHeight() - (1 + radius); y++) {
            for (int x = radius; x <= andieOutputImage.getWidth() - (1 + radius); x++) {
                andieOutputImageData[i] = andieOutputImage.getRGB(x, y);
                javaOutputImageData[i] = javaOutputImage.getRGB(x, y);
            }
        }

        assertArrayEquals(andieOutputImageData, javaOutputImageData);
    }

    /**
     * <p>
     * Test method for {@link MeanFilter}.
     * </p>
     */
    @Test
    void TestMeanFilter() {
        int radius = 1;
        int size = (2 * radius + 1) * (2 * radius + 1);
        float[] array = new float[size];
        Arrays.fill(array, 1.0f / size);
        convolveAndTestData(array, radius);
    }

    /**
     * <p>
     * Test method for {@link EmbossFilterEast}.
     * </p>
     */
    @Test
    void TestEmbossFilterEast() {
        int radius = 1;
        float[] array = { 0, 0, 0, 1, 0, -1, 0, 0, 0 };
        convolveAndTestData(array, radius);
    }

    /**
     * <p>
     * Test method for {@link EmbossFilterNorth}.
     * </p>
     */
    @Test
    void TestEmbossFilterNorth() {
        int radius = 1;
        float[] array = { 0, 1, 0, 0, 0, 0, 0, -1, 0 };
        convolveAndTestData(array, radius);
    }

    /**
     * <p>
     * Test method for {@link EmbossFilterNorthEast}.
     * </p>
     */
    @Test
    void TestEmbossFilterNorthEast() {
        int radius = 1;
        float[] array = { 1, 0, 0, 0, 0, 0, 0, 0, -1 };
        convolveAndTestData(array, radius);
    }

    /**
     * <p>
     * Test method for {@link EmbossFilterNorthWest}.
     * </p>
     */
    @Test
    void TestEmbossFilterNorthWest() {
        int radius = 1;
        float[] array = { 0, 0, 1, 0, 0, 0, -1, 0, 0 };
        convolveAndTestData(array, radius);
    }

    /**
     * <p>
     * Test method for {@link EmbossFilterSouth}.
     * </p>
     */
    @Test
    void TestEmbossFilterSouth() {
        int radius = 1;
        float[] array = { 0, -1, 0, 0, 0, 0, 0, 1, 0 };
        convolveAndTestData(array, radius);
    }

    /**
     * <p>
     * Test method for {@link EmbossFilterSouthEast}.
     * </p>
     */
    @Test
    void TestEmbossFilterSouthEast() {
        int radius = 1;
        float[] array = { 0, 0, -1, 0, 0, 0, 1, 0, 0 };
        convolveAndTestData(array, radius);
    }

    /**
     * <p>
     * Test method for {@link EmbossFilterSouthWest}.
     * </p>
     */
    @Test
    void TestEmbossFilterSouthWest() {
        int radius = 1;
        float[] array = { -1, 0, 0, 0, 0, 0, 0, 0, 1 };
        convolveAndTestData(array, radius);
    }

    /**
     * <p>
     * Test method for {@link EmbossFilterWest}.
     * </p>
     */
    @Test
    void TestEmbossFilterWest() {
        int radius = 1;
        float[] array = { 0, 0, 0, -1, 0, 1, 0, 0, 0 };
        convolveAndTestData(array, radius);
    }

}
