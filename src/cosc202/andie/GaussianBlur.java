package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Gaussian Blur filter.
 * </p>
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Ilgaz Celik
 * @version 1.0
 */
public class GaussianBlur implements ImageOperation, java.io.Serializable {
    
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;
    public float[] array;

    /**
     * <p>
     * Construct a Gaussian Blur filter with the given size.
     * </p>
     * 
     * 
     * @param radius The radius of the newly constructed Gaussian Blur
     */
    public GaussianBlur(int radius) {
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
     * </p>
     * 
     * @param input The image to apply the Gaussian Blur to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {

        //Initialising and declaring required variables
        int size = (2*radius+1) * (2*radius+1);
        this.array = new float[size];
        double sum = 0;
        double sigma = (double)radius/3;


        //initialising and declaring variables that will be used often
        double val = 1/(2*Math.PI*sigma*sigma);
        double val2 = 2*sigma*sigma;
        int diameter = 2*radius+1;
        
        //going through the array and entering in the gaussian blur values
        for (int i = 0;i<array.length;i++){
            array[i]=(float)(val*Math.exp(-(((radius-i%diameter)*(radius-i%diameter)+(radius-i/diameter)*(radius-i/diameter))/val2)));
            sum+=array[i];
        }

        //going through the array and diving each value by the sum to create a percentage value
        for (int i = 0;i<array.length;i++){
            array[i]=(float)(array[i]/sum);
        }
        



        //using a kernel to do a convolution operation
        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);
        Convolution convolution = new Convolution(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convolution.filter(input, output);

        return output;
    }


}
