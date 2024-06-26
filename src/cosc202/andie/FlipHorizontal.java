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
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class FlipHorizontal implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Apply a horizontal flip to an image.
     * </p>
     * 
     * @param input The image to apply the rotation to.
     * @return The resulting (horizontally flipped) image.
     */
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
