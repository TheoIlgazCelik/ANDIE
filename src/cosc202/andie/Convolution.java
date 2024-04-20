package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

/**
 * <p>
 * Convolution class to apply convolution via Kernel to {@link BufferedImage}s,
 * which
 * includes image borders.
 * </p>
 * 
 * <p>
 * In Java's ConvolveOp class, the borders of an image (of width kernel-radius)
 * are either ignored during the convolution or filled in black. This class
 * handles
 * the image edges by using the "nearest valid pixel" in the convolution when
 * the kernel
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
  private final int ALPHA_MAX_VALUE = 255;

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

    // iterate each pixel
    for (int y = 0; y < input.getHeight(); y++) {
      for (int x = 0; x < input.getWidth(); x++) {
        float[] argb = { 0, 0, 0, 0 };

        // KERNEL_DATA index
        int ki = 0;
        // iterate each pixel of kernel
        for (int yOffset = -this.RADIUS; yOffset <= this.RADIUS; yOffset++) {
          for (int xOffset = -this.RADIUS; xOffset <= this.RADIUS; xOffset++) {
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

            // unpack argb from source image
            int ARGB = input.getRGB(dx, dy);
            int a = (ARGB >> 24) & 0xFF;
            int r = (ARGB >> 16) & 0xFF;
            int g = (ARGB >> 8) & 0xFF;
            int b = (ARGB >> 0) & 0xFF;

            argb[0] += a * KERNEL_DATA[ki];
            argb[1] += r * KERNEL_DATA[ki];
            argb[2] += g * KERNEL_DATA[ki];
            argb[3] += b * KERNEL_DATA[ki];

            ki++;
          }
        }

        int a = Math.round(argb[0]);
        int r = Math.round(argb[1]);
        int g = Math.round(argb[2]);
        int b = Math.round(argb[3]);

        if (offset) {
          a += OFFSET_VAL;
          r += OFFSET_VAL;
          g += OFFSET_VAL;
          b += OFFSET_VAL;
        }

        // clamp argb (0 - 255)
        if (a < 0) {
          a = 0;
        } else if (a > 255) {
          a = 255;
        }

        if (r < 0) {
          r = 0;
        } else if (r > 255) {
          r = 255;
        }

        if (g < 0) {
          g = 0;
        } else if (g > 255) {
          g = 255;
        }

        if (b < 0) {
          b = 0;
        } else if (b > 255) {
          b = 255;
        }

        if (!hasAlpha) {
          a = ALPHA_MAX_VALUE;
        }

        // set pixel in output image
        int outputARGB = (a << 24) | (r << 16) | (g << 8) | b;
        output.setRGB(x, y, outputARGB);
      }
    }
  }

}
