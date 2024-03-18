package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to apply a 90 degree rotation to the right.
 * </p>
 * 
 * <p>
 * An Image rotation swaps the width and height of the original image, and transposes the
 * pixels accordingly.
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class RotateRight implements ImageOperation, java.io.Serializable {

    public BufferedImage apply(BufferedImage input) {
        // set output image dimensions to opposite of input image
        int outputWidth = input.getHeight();
        int outputHeight = input.getWidth();
        
        int outputType = input.getType();
        
        BufferedImage output = new BufferedImage(outputWidth, outputHeight, outputType);
        
        // iterate through each pixel of input image
        for (int iy = 0; iy < input.getHeight(); iy++) {
            for (int ix = 0; ix < input.getWidth(); ix++) {
                int argb = input.getRGB(ix, iy);
            
                // transpose (x, y) of the input pixel to (x, y) in the output
                int ox = (output.getWidth()-1) - iy;
                int oy = ix;
            
                output.setRGB(ox, oy, argb);
            }
        }
    
        return output;
    }
    
}
