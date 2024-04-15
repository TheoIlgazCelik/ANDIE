package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Random scattering filter.
 * </p>
 * 
 * <p>
 * A Random Scattering filter takes each pixel and replaces them with a random pixel within
 * the radius specified
 * </p>
 * 
 * @author Aiden O'Brien
 * @version 1.0
 */
public class RandomScattering implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply
    */
    private int radius;

    /**
     * <p>
     * Construct a Random Scattering filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * </p>
     * 
     * @param radius The radius of the newly constructed RandomScattering
     */
    RandomScattering(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Random Scattering filter with the default size.
     * </p>
     * 
     * <p>
     * By default, a Random Scattering filter has radius 1.
     * </p>
     * 
     * @see RandomScattering
     */
    RandomScattering() {
        this(1);
    }

    /**
     * <p>
     * Apply a Random Scattering filter to an image.
     * </p>
     * 
     * <p>
     * Unlike many other filters, the Random Scattering filter isn't implemented via convolution.
     * The pixels are randomly retrieved and then swapped in.
     * </p>
     * 
     * @param input The image to apply the Random Scattering filter to.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        int nx, ny = 0;

        BufferedImage output = new BufferedImage(width, height, input.getType());
        Random rand = new Random();
        // iterate through each pixel of original image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (rand.nextInt(2) == 0) {
                    nx = x + rand.nextInt(radius+1);
                    ny = y + rand.nextInt(radius+1);
                } else {
                    nx = x - rand.nextInt(radius+1);
                    ny = y - rand.nextInt(radius+1);
                }
                //System.out.println(rand.nextInt(2));

                // bounds check
                if (nx < 0) {
                    nx = 0;
                } else if (nx >= width) {
                    nx = width - 1;
                }
                if (ny < 0) {
                    ny = 0;
                } else if (ny >= height) {
                    ny = height - 1;
                }
                int argb = input.getRGB(nx, ny);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                //This code would be used if the value of the random pixel had to take the current pixels value
                /*int argb2 = input.getRGB(x,y);
                int a2 = (argb2 & 0xFF000000) >> 24;
                int r2 = (argb2 & 0x00FF0000) >> 16;
                int g2 = (argb2 & 0x0000FF00) >> 8;
                int b2 = (argb2 & 0x000000FF);*/

                // pack argb and set in output
                int argb3 = (a << 24) | (r << 16) | (g << 8) | b;
                //int argb4 = (a2 << 24) | (r2 << 16) | (g2 << 8) | b2;

                //output.setRGB(nx, ny, argb4);
                output.setRGB(x, y, argb3);
            }

        }
        
        return output;
    }
}