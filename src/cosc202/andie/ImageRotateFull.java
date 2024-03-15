package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to apply a 180 degree rotation.
 * </p>
 * 
 * <p>
 * A 180 degree Image rotation swaps the width and height of the original 
 * image and transposes the pixels accordingly.
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class ImageRotateFull implements ImageOperation, java.io.Serializable {

    public BufferedImage apply(BufferedImage input) {
        return input;
    }
    
}
