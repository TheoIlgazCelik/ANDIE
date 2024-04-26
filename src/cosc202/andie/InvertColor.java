package cosc202.andie;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to invert an images colors.
 * </p>
 * 
 * <p>
 * The images produced by this operation are images,
 * that have red, green, and blue values that have been inverted.
 * </p>
 * 
 * 
 * @author Aiden O'Brien
 * @version 1.0
 */
public class InvertColor implements ImageOperation, java.io.Serializable {
     /**
     * <p>
     * Create a new InvertColor operation.
     * </p>
     */
    public InvertColor() {}

     /**
     * <p>
     * Apply image color conversion to an image.
     * </p>
     * 
     * <p>
     * The conversion from red, green, and blue values to an inverted state  
     * involves subracting each of the rgb values from 255.
     * This results in an inverted color.
     * </p>
     * 
     * @param input The image to have its colors inverted
     * @return The resulting inverted image.
     */
    public BufferedImage apply(BufferedImage input) {
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb >> 24) & 0xFF;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                //int grey = (int) Math.round(0.3*r + 0.6*g + 0.1*b);

                argb = (a << 24) | (255-r << 16) | (255-g << 8) | 255-b;
                
                input.setRGB(x, y, argb);
            }
        }
        return input;
    }
}
 