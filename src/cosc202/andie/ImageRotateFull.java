package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to apply a 180 degree rotation.
 * </p>
 * 
 * <p>
 * A 180 degree Image rotation keeps the width and height of the original 
 * image and transposes the pixels accordingly.
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class ImageRotateFull implements ImageOperation, java.io.Serializable {

    public BufferedImage apply(BufferedImage input) {
        // set output image dimensions to same as input image
        int outputWidth = input.getWidth();
        int outputHeight = input.getHeight();
        
        int outputType = input.getType();
        
        BufferedImage output = new BufferedImage(outputWidth, outputHeight, outputType);
        
        // iterate through each pixel of input image
        for (int iy = 0; iy < input.getHeight(); iy++) {
            for (int ix = 0; ix < input.getWidth(); ix++) {
                int argb = input.getRGB(ix, iy);
            
                // transpose (x, y) of the input pixel to (x, y) in the output
                int ox = (output.getWidth() - 1) - ix;
                int oy = (output.getHeight() - 1) - iy;
            
                output.setRGB(ox, oy, argb);
            }
        }
    
        return output;
    }
    
}
