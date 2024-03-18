package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Gaussian Blur filter.
 * </p>
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Steven Mills
 * @version 1.0
 */
public class GaussianBlur implements ImageOperation, java.io.Serializable {
    
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Mean filter with the given size.
     * </p>
     * 
     * 
     * @param radius The radius of the newly constructed Gaussian Blur
     */
    GaussianBlur(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a Gaussian Blur with the default size.
     * </p
     * >
     * <p>
     * By default, a Gaussian Blur has radius 1.
     * </p>
     */
    GaussianBlur() {
        this(1);
    }

    /**
     * <p>
     * Apply a Gaussian Blur to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Gaussian Blur filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.  
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Mean filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        /*int size = (2*radius+1) * (2*radius+1);
        float [] array = new float[size];
        Arrays.fill(array, 1.0f/size);

        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;*/
        return (new BufferedImage(1, 1, 0));
    }


}
