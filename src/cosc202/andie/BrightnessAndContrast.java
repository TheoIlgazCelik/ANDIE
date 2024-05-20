package cosc202.andie;

import java.awt.image.*;


/**
 * <p>
 * ImageOperation to change the brightness and contrast
 * </p>
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Ilgaz Celik
 * @version 1.0
 */
public class BrightnessAndContrast implements ImageOperation, java.io.Serializable {
    
    /** Percentage change of brightness */
    private int brightness;
    /** Percentage change of contrast. */
    private int contrast;

    /**
     * <p>
     * Changing the Brightness and Contrast for the given input
     * </p>
     * 
     * 
     * @param brightness The percentage amount of change to brightness
     * @param contrast The percentage amount of change to contrast
     */
    public BrightnessAndContrast(int brightness, int contrast) {
        this.brightness = brightness;
        this.contrast = contrast;
    }

    /**
     * <p>
     * Construct a BrightnessAndContrast with the default change.
     * </p
     * >
     * <p>
     * By default, a BrightnessAndContrast does not change (meaning 100%)
     * </p>
     */
    BrightnessAndContrast() {
        this.brightness = 100;
        this.contrast = 100;
    }

    /**
     * <p>
     * Applying a bright and contrast adjustment to an image
     * </p>
     * 
     * <p>
     * This method changes the colour and brightness according to the input given for each individual pixel
     * 
     * @param input The image to apply the brightness and contrast adjustment to.
     * @return The resulting adjusted image.
     */
    public BufferedImage apply(BufferedImage input) {
    // set output image dimensions to opposite of input image
    int outputWidth = input.getWidth();
    int outputHeight = input.getHeight();

    int outputType = input.getType();

    BufferedImage output = new BufferedImage(outputWidth, outputHeight, outputType);

    // iterate through each pixel of input image
    for (int iy = 0; iy < input.getHeight(); iy++) {
        for (int ix = 0; ix < input.getWidth(); ix++) {
            // retrieving values from current image for each pixel
            int argb = input.getRGB(ix, iy);
            int a = (argb & 0xFF000000) >> 24;
            int r = (argb & 0x00FF0000) >> 16;
            int g = (argb & 0x0000FF00) >> 8;
            int b = (argb & 0x000000FF);
            
            //changing values of each pixel
            int outputR = (int)((1+(double)contrast/100)*((double)r-127.5)+127.5*(1+(double)brightness/100));
            int outputG = (int)((1+(double)contrast/100)*((double)g-127.5)+127.5*(1+(double)brightness/100));
            int outputB = (int)((1+(double)contrast/100)*((double)b-127.5)+127.5*(1+(double)brightness/100));

            //adjusting boundaries
            if (outputR<0){
                outputR = 0;
            } else if (outputR>255){
                outputR = 255;
            }
            if (outputG<0){
                outputG = 0;
            } else if (outputG>255){
                outputG = 255;
            }if (outputB<0){
                outputB = 0;
            } else if (outputB>255){
                outputB = 255;
            }

            //outputting values into an int
            int outputRGB = (a << 24) | (outputR << 16) | (outputG << 8) | outputB;

            //setting rgb of image
            output.setRGB(ix, iy, outputRGB);
        }
    }
    return output;
    }


}
