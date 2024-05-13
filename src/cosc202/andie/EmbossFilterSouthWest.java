package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a south west emboss filter.
 * </p>
 * 
 * 
 * @see java.awt.image.ConvolveOp
 * @author 
 * @version 1.0
 */
public class EmbossFilterSouthWest implements ImageOperation, java.io.Serializable{

    /**
     * <p>
     * Apply an emboss filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the emboss filter is implemented via convolution.
     * </p>
     * 
     * @param input The image to apply the emboss filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input){
        float[] array = {-1,0,0,0,0,0,0,0,1};
        Kernel kernel = new Kernel(3,3,array);
        Convolution convolution = new Convolution(kernel, true);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convolution.filter(input,output);
        
        return output;
 
    }


}