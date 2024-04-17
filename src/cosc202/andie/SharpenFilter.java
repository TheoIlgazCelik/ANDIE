package cosc202.andie;

import java.awt.image.*;
/**
 * <p>
 * ImageOperation to apply a Sharpen filter.
 * </p>
 * 
 * <p>
 * A Sharpen filter is the reverse of a blur, instead of making each pixel more like its
 * neighbours it enhances the differences between neighbouring values.
 * </p>
 * 
 * @author Finn Rimmer
 * @version 1.0
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable{
     /**
     * <p>
     * Construct a Sharpen filter.
     * </p>
     * */
    SharpenFilter(){};

/**
     * <p>
     * Apply a Sharpen filter to an image.
     * </p>
     * 
     * <p>
     * The Sharpen filter is implemented via convolution with the Kernal.
     * Making each pixel less like its neighbours by enhancing the differences.
     * </p>
     * 
     * @param input The image to apply the Sharpen filter to.
     * @return The resulting (sharpened) image.
     */    
    public BufferedImage apply(BufferedImage input){
        float[] array = {0,-0.5f,0,-0.5f,3,-0.5f,0,-0.5f,0};
        Kernel kernel = new Kernel(3,3,array);
        Convolution convolution = new Convolution(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convolution.filter(input, output);
        return output;
    }
    

}
