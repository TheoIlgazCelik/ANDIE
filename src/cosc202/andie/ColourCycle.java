package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to cycle an image's colour channels 
 * </p>
 * 
 * <p>
 * The images produced by this operation are still colour images,
 * just that their red, green, and blue values rotate changing the colour 
 * channels each cycle. RGB, GBR, BRG.
 * </p>
 * 
 * 
 * @author Joshua Saunders
 * @version 1.0
 */


public class ColourCycle implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Apply a colour channel cycle to an image
     * </p>
     * 
     * <p>
     * Extracts the current values of red, green, and blue
     * then sets their values of another
     * </p>
     * 
     * @param input The image to be converted to greyscale
     * @return The resulting colour cycle image.
     */
    public BufferedImage apply(BufferedImage input) {

        //iterating through each pixel of input image 
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb >> 24) & 0xFF;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                //rearrange the colour values each time the method is called
                argb = (a << 24) | (b << 16) | (r << 8) | g;
                input.setRGB(x, y, argb);
            }
        }
        //System.out.println(argb);
        return input;
    }
}
 