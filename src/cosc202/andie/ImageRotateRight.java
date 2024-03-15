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
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class ImageRotateRight implements ImageOperation, java.io.Serializable {

    public BufferedImage apply(BufferedImage input) {
        return input;
    }
    
}
