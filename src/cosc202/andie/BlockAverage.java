package cosc202.andie;

import java.awt.image.*;
/**
 * <p>
 * ImageOperation to apply a block averaging filter.
 * </p>
 * 
 * <p>
 * A block averaging filter works to provide a pixelated view of an image by taking the average pixel
 * values of pixels in a chosen radius and sets each pixels value to this average.
 * </p>
 * 
 * @author Aiden O'Brien
 * @version 1.0
 */

public class BlockAverage implements ImageOperation, java.io.Serializable{
    private int x;
    private int y;
     /**
     * <p>
     * Construct a block averaging filter.
     * </p>
     * */
    BlockAverage(){this.x = 1; this.y = 1;};
    BlockAverage(int x, int y) {this.x = x; this.y = y;}

    /**
     * <p>
     * Apply a block averaging filter to an image.
     * </p>
     * 
     * <p>
     * The block averaging filter works by finding the mean of a specified block size and setting each pixel
     * within that to that average value.
     * </p>
     * 
     * @param input The image to apply the block averaging filter to.
     * @return The resulting image.
     */ 

    public BufferedImage apply(BufferedImage input){
        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage output = new BufferedImage(width, height, input.getType());
        int size = (2 * x + 1) * (2 * y + 1);

        // iterate through each pixel of original image
        for (int ty = 0; ty < height; ty+=this.y) {
            for (int tx = 0; tx < width; tx+=this.x) {

                int[] neighbourhoodValuesA = new int[size];
                int[] neighbourhoodValuesR = new int[size];
                int[] neighbourhoodValuesG = new int[size];
                int[] neighbourhoodValuesB = new int[size];

                // iterate through neighbourhood
                for (int yOffset = -this.y, currNeighbour = 0; yOffset <= this.y; yOffset++) {
                    for (int xOffset = -this.x; xOffset <= this.x; xOffset++, currNeighbour++) {
                        // x, y for neighbour
                        int nx = tx + xOffset;
                        int ny = ty + yOffset;

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
                        int a = (argb & 0xFF000000) >> 24;
                        int r = (argb & 0x00FF0000) >> 16;
                        int g = (argb & 0x0000FF00) >> 8;
                        int b = (argb & 0x000000FF);

                        neighbourhoodValuesA[currNeighbour] = a;
                        neighbourhoodValuesR[currNeighbour] = r;
                        neighbourhoodValuesG[currNeighbour] = g;
                        neighbourhoodValuesB[currNeighbour] = b;
                    }
                }

                //Find the average values

                int meanA = 0;
                int meanR = 0;
                int meanG = 0;
                int meanB = 0;
                for (int i=0; i<size; i++) {
                    meanA += neighbourhoodValuesA[i];
                    meanR += neighbourhoodValuesR[i];
                    meanG += neighbourhoodValuesG[i];
                    meanB += neighbourhoodValuesB[i];
                }
                meanA = meanA/size;
                meanR = meanR/size;
                meanG = meanG/size;
                meanB = meanB/size;

                // iterate through neighbourhood once more to set values
                for (int yOffset = -this.y; yOffset <= this.y; yOffset++) {
                    for (int xOffset = -this.x; xOffset <= this.x; xOffset++) {
                        // x, y for neighbour
                        int nx = tx + xOffset;
                        int ny = ty + yOffset;

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

                        int argb = (meanA << 24) | (meanR << 16) | (meanG << 8) | meanB;
                        output.setRGB(nx, ny, argb);
                    }
                }

            }

        }
        
        return output;
    }
    

}
