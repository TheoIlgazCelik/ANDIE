package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to apply a Vertical flip to a BufferedImage.
 * </p>
 * 
 * <p>
 * A vertical flip transposes the input y co-ordinate to it's corresponding
 * output y co-ordinate while retaining the original x co-ordinate.
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class VerticalFlip implements ImageOperation, java.io.Serializable {

    public BufferedImage apply(BufferedImage input) {
        // iterate through each pixel of top half of image (excluding center pixel if height is odd)
        for (int y = 0; y < input.getHeight() / 2; y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                // ry = corresponding y co-ordinate on bottom half of image
                int ry = (input.getHeight() - 1) - y;

                int argbLeft = input.getRGB(x, y);
                int argbRight = input.getRGB(x, ry);

                input.setRGB(x, y, argbRight);
                input.setRGB(x, ry, argbLeft);
            }

        }

        return input;
    }
    
}
