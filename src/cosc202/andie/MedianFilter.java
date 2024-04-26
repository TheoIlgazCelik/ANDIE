package cosc202.andie;

import java.awt.image.*;
import java.util.Arrays;

/**
 * <p>
 * ImageOperation to apply a Median filter.
 * </p>
 * 
 * <p>
 * A Median filter blurs an image by replacing each pixel by the median argb values of the
 * pixels in a surrounding neighbourhood.
 * </p>
 * 
 * @author Josh Saunders
 * @author Matthew Rae
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Median filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter
     */
    MedianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Median filter with the default size.
     * </p>
     * 
     * <p>
     * By default, a Median filter has radius 1.
     * </p>
     * 
     * @see MedianFilter
     */
    MedianFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     * 
     * <p>
     * Unlike many other filters, the Median filter isn't implemented via convolution.
     * The neighbourhood values of each pixel are programmatically retrieved, before the median 
     * is calculated and used to replace the original pixel.
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage output = new BufferedImage(width, height, input.getType());
        int size = (2 * radius + 1) * (2 * radius + 1);

        // iterate through each pixel of original image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int[] neighbourhoodValuesA = new int[size];
                int[] neighbourhoodValuesR = new int[size];
                int[] neighbourhoodValuesG = new int[size];
                int[] neighbourhoodValuesB = new int[size];

                // iterate through neighbourhood
                for (int yOffset = -radius, currNeighbour = 0; yOffset <= radius; yOffset++) {
                    for (int xOffset = -radius; xOffset <= radius; xOffset++, currNeighbour++) {
                        // x, y for neighbour
                        int nx = x + xOffset;
                        int ny = y + yOffset;

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

                        // unpack and store argb
                        int argb = input.getRGB(nx, ny);
                        int a = (argb >> 24) & 0xFF;
                        int r = (argb & 0x00FF0000) >> 16;
                        int g = (argb & 0x0000FF00) >> 8;
                        int b = (argb & 0x000000FF);

                        neighbourhoodValuesA[currNeighbour] = a;
                        neighbourhoodValuesR[currNeighbour] = r;
                        neighbourhoodValuesG[currNeighbour] = g;
                        neighbourhoodValuesB[currNeighbour] = b;
                    }
                }

                // get medians
                Arrays.sort(neighbourhoodValuesA);
                Arrays.sort(neighbourhoodValuesR);
                Arrays.sort(neighbourhoodValuesG);
                Arrays.sort(neighbourhoodValuesB);

                int medianA = neighbourhoodValuesA[size / 2];
                int medianR = neighbourhoodValuesR[size / 2];
                int medianG = neighbourhoodValuesG[size / 2];
                int medianB = neighbourhoodValuesB[size / 2];

                // pack argb and set in output
                int argb = (medianA << 24) | (medianR << 16) | (medianG << 8) | medianB;

                output.setRGB(x, y, argb);
            }

        }
        
        return output;
    }

}