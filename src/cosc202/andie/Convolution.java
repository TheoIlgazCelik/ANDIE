package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

/**
 * <p>
 * Convolution class to apply convolution via Kernel to {@link BufferedImage}s, which
 * includes image borders.
 * </p>
 * 
 * <p>
 * In Java's ConvolveOp class, the borders of an image (of width kernel-radius)
 * are either ignored during the convolution or filled in black. This class handles
 * the image edges by using the "nearest valid pixel" in the convolution when the kernel
 * hangs over the edge.
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class Convolution {
  private final Kernel KERNEL;
  private final float[] KERNEL_DATA;
  private final int RADIUS;
  private final boolean offset;
  private final int OFFSET_VAL = 127;

  /**
   * <p>
   * Create a new Convolution operation.
   * </p>
   * 
   * <p>
   * Default convolution has no offset
   * </p>
   */
  public Convolution(Kernel kernel) {
    this(kernel, false);
  }

  /**
   * <p>
   * Create a new Convolution operation with offset option
   * </p>
   */
  public Convolution(Kernel kernel, boolean offset) {
    this.KERNEL = kernel;
    this.offset = offset;
    this.KERNEL_DATA = kernel.getKernelData(null);
    this.RADIUS = this.KERNEL.getWidth() / 2;
  }

  /**
   * <p>
   * Apply convolution via kernel to a BufferedImage, inclusive of border regions
   * </p>
   * 
   * @param input  Original image to be filtered via convolution kernel
   * @param output Image to write the "filtered" pixels to
   * 
   * @return The filtered image
   */
  public void filter(BufferedImage input, BufferedImage output) {
    if (input == null) {
      throw new NullPointerException("Parameter {input} is null");
    }

    if (output == null) {
      throw new NullPointerException("Parameter {output} is null");
    }

    boolean hasAlpha = input.getColorModel().hasAlpha();

    // for each pixel in image
    for (int y = 0; y < input.getHeight(); y++) {
      for (int x = 0; x < input.getWidth(); x++) {
        float[] argb = { 0, 0, 0, 0 };

        if (!hasAlpha) {
          argb[0] = (input.getRGB(x, y) & 0xFF000000) >> 24;
        }

        // for each pixel in kernel
        for (int yOffset = -this.RADIUS, i = 0; yOffset <= this.RADIUS; yOffset++) {
          for (int xOffset = -this.RADIUS; xOffset <= this.RADIUS; xOffset++, i++) {
            int dy = y + yOffset;
            int dx = x + xOffset;

            // y edge check
            if (dy < 0) {
              dy = 0;
            } else if (dy >= input.getHeight()) {
              dy = input.getHeight() - 1;
            }

            // x edge check
            if (dx < 0) {
              dx = 0;
            } else if (dx >= input.getWidth()) {
              dx = input.getWidth() - 1;
            }

            // argb from source image
            int inputARGB = input.getRGB(dx, dy);
            int inputA = (inputARGB & 0xFF000000) >> 24;
            int inputR = (inputARGB & 0x00FF0000) >> 16;
            int inputG = (inputARGB & 0x0000FF00) >> 8;
            int inputB = (inputARGB & 0x000000FF);

            if (hasAlpha) {
              argb[0] += inputA * KERNEL_DATA[i];
            }

            argb[1] += inputR * KERNEL_DATA[i];
            argb[2] += inputG * KERNEL_DATA[i];
            argb[3] += inputB * KERNEL_DATA[i];
          }
        }

        if (offset) {
          argb[1] += OFFSET_VAL;
          argb[2] += OFFSET_VAL;
          argb[3] += OFFSET_VAL;
        }

        // clamp argb values between 0 - 255
        for (int j = 0; j < argb.length; j++) {
          if (argb[j] < 0) {
            argb[j] = 0;
          } else if (argb[j] > 255) {
            argb[j] = 255;
          }
        }

        // set pixel in output image
        int outputRGB = ((int) argb[0] << 24) | ((int) argb[1] << 16) | ((int) argb[2] << 8) | (int) argb[3];
        output.setRGB(x, y, outputRGB);
      }
    }
  }

}
