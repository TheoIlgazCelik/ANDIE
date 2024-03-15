package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to apply a horizontal flip to a BufferedImage.
 * </p>
 * 
 * <p>
 * A horizontal flip transposes the input x co-ordinate to it's corresponding
 * output x co-ordinate while retaining the original y co-ordinate.
 * 
 * outputX = (width-1) - inputX
 * outputY = inputY;
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class HorizontalFlip implements ImageOperation, java.io.Serializable {

    public BufferedImage apply(BufferedImage input) {
        // set output image dimensions to opposite of input image
        int outputWidth = input.getWidth();
        int outputHeight = input.getHeight();
        
        int outputType = input.getType();
        
        BufferedImage output = new BufferedImage(outputWidth, outputHeight, outputType);
        
        // iterate through each pixel of input image
        for (int iy = 0; iy < input.getHeight(); iy++) {
            for (int ix = 0; ix < input.getWidth(); ix++) {
                int argb = input.getRGB(ix, iy);
            
                // transpose (x, y) of the input pixel to (x, y) in the output
                int ox = (input.getWidth() - 1) - ix;
                int oy = iy;
            
                output.setRGB(ox, oy, argb);
            }
        }
    
        return output;
    }
    
}
