package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;
import java.awt.Graphics2D;
import java.awt.image.ConvolveOp;

/**
 * <p>
 * Convolution class to apply convolution via Kernel to BufferedImages, which includes
 * image borders.
 * </p>
 * 
 * <p>
 * In Java's ConvolveOp class, the borders of an image (of width kernel-radius) are either ignored
 * during the convolution or filled in black. This class solves that issue by "preparing" the image
 * before convolution with Java's implementation.
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Matthew Rae
 * @version 1.0
 */
public class Convolution {
  private final Kernel KERNEL;
  private final int RADIUS;

  public Convolution(Kernel kernel) {
    this.KERNEL = kernel;
    this.RADIUS = this.KERNEL.getWidth() / 2;
  }

  /**
     * <p>
     * Apply convolution via kernel to a BufferedImage, inclusive of border regions
     * </p>
     * 
     * @param input Image to be filtered via convolution kernel
     * 
     * @return The filtered image
     */
  public BufferedImage filter(BufferedImage input) {
    BufferedImage expandedImage = expandImageBorders(input);

    // apply java's convolution to expanded image
    ConvolveOp convOp = new ConvolveOp(this.KERNEL);
    BufferedImage convolvedImage = new BufferedImage(expandedImage.getColorModel(), expandedImage.copyData(null), expandedImage.isAlphaPremultiplied(), null);
    convOp.filter(expandedImage, convolvedImage);

    // crop black borders off convolved image
    BufferedImage subimage = convolvedImage.getSubimage(this.RADIUS, this.RADIUS, input.getWidth(), input.getHeight());
    
    return subimage;
  }

  /**
     * <p>
     * Preparation function for image convolution.Takes an input BufferedImage, adds a border 
     * of width KERNEL.radius to each side and fills border pixels in with nearest "valid" pixel.
     * </p>
     * 
     * @param input The image to be "expanded"
     * 
     * @return The expanded image
     */
  private BufferedImage expandImageBorders(BufferedImage input) {
    // expand image by 2 * kernel-radius
    int width = input.getWidth() + (2 * this.RADIUS);
    int height = input.getHeight() + (2 * this.RADIUS);
    BufferedImage output = new BufferedImage(width, height, input.getType());

    // copy orignal image into expanded image with x, y offsets of kernel-radius
    Graphics2D graphics = output.createGraphics();
    graphics.drawImage(input, this.RADIUS, this.RADIUS, null);
    graphics.dispose();

    // for each pixel in border
    int y = 0;
    while (y < output.getHeight()) {
      int x = 0;
      while (x < output.getWidth()) {
        // skip over "valid" co-ordinates
        if ((y >= this.RADIUS && y < output.getHeight() - RADIUS) && (x >= this.RADIUS && x < output.getWidth() - RADIUS)) {
          x = output.getWidth() - RADIUS;
          continue;
        }

        int dy = y;
        int dx = x;

        // set y to nearest valid pixel
        if (y < this.RADIUS) {
          dy = this.RADIUS;
        } else if (y >= output.getHeight() - RADIUS) {
          dy = output.getHeight() - RADIUS - 1;
        }

        // set x to nearest valid pixel
        if (x < this.RADIUS) {
          dx = this.RADIUS;
        } else if (x >= output.getWidth() - RADIUS) {
          dx = output.getWidth() - RADIUS - 1;
        }

        // set pixel
        int rgb = output.getRGB(dx, dy);
        output.setRGB(x, y, rgb);

        x++;
      }
      y++;
    }

    return output;
  }

}
