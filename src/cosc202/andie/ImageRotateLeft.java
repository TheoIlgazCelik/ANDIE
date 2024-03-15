package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to apply a 90 degree rotation to the left.
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
public class ImageRotateLeft implements ImageOperation, java.io.Serializable {

    public BufferedImage apply(BufferedImage input) {
        return input;
    }
    
}
