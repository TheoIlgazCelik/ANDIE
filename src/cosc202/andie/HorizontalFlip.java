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
        // iterate through each pixel of left half of image (excluding center pixel if width is odd)
        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth() / 2; x++) {
                // rx = corresponding x co-ordinate on right half of image
                int rx = (input.getWidth() - 1) - x;

                int argbLeft = input.getRGB(x, y);
                int argbRight = input.getRGB(rx, y);

                input.setRGB(x, y, argbRight);
                input.setRGB(rx, y, argbLeft);
            }

        }

        return input;
    }

}
