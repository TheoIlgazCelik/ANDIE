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
 * handles the image edges by using the "nearest valid pixel" in the convolution
 * when
 * the kernel hangs over the edge.
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class Convolution {
  /** Kernel matrix for convolution operation. */
  private final Kernel KERNEL;
  /** Data from kernel. */
  private final float[] KERNEL_DATA;
  /** Radius of kernel. */
  private final int RADIUS;
  /** Flag to apply an offset to handle negative ARGB values. */
  private final boolean ARGB_OFFSET;
  /** Offset to handle filters with negative values. */
  private final int OFFSET_VAL = 127;
  /** Minimum ARGB values. */
  private final int ARGB_MIN_VALUE = 0;
  /** Maximum ARGB values. */
  private final int ARGB_MAX_VALUE = 255;

  /**
   * <p>
   * Create a new Convolution operation.
   * </p>
   * 
   * <p>
   * Default convolution has no offset
   * </p>
   * 
   * @param kernel Kernel matrix for convolution operation
   */
  public Convolution(Kernel kernel) {
    this(kernel, false);
  }

  /**
   * <p>
   * Create a new Convolution operation with offset option
   * </p>
   * 
   * @param kernel Kernel matrix for convolution operation
   * @param offset Flag to handle filters with negative values
   */
  public Convolution(Kernel kernel, boolean offset) {
    this.KERNEL = kernel;
    this.ARGB_OFFSET = offset;
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
   */
  public void filter(BufferedImage input, BufferedImage output) {
    if (input == null) {
      throw new NullPointerException("Parameter {input} is null");
    }

    if (output == null) {
      throw new NullPointerException("Parameter {output} is null");
    }

    boolean hasAlpha = input.getColorModel().hasAlpha();
    int width = input.getWidth();
    int height = input.getHeight();

    int[] inputPixels = new int[width * height];
    int[] outputPixels = new int[width * height];
    input.getRGB(0, 0, width, height, inputPixels, 0, width);

    // pixel index
    int pi = 0;
    // iterate each pixel
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        float[] argb = { 0, 0, 0, 0 };

        // KERNEL_DATA index
        int ki = 0;
        // iterate each pixel of kernel
        for (int yOffset = -this.RADIUS; yOffset <= this.RADIUS; yOffset++) {
          for (int xOffset = -this.RADIUS; xOffset <= this.RADIUS; xOffset++) {
            int dy = y + yOffset;
            int dx = x + xOffset;

            // edge check
            dx = Math.max(0, Math.min(dx, width - 1));
            dy = Math.max(0, Math.min(dy, height - 1));

            // kernel-pixel index
            int kpi = (dy * width) + dx;

            // unpack argb from source image
            int ARGB = inputPixels[kpi];
            int ka = (ARGB >> 24) & 0xFF;
            int kr = (ARGB >> 16) & 0xFF;
            int kg = (ARGB >> 8) & 0xFF;
            int kb = (ARGB >> 0) & 0xFF;

            argb[0] += ka * KERNEL_DATA[ki];
            argb[1] += kr * KERNEL_DATA[ki];
            argb[2] += kg * KERNEL_DATA[ki];
            argb[3] += kb * KERNEL_DATA[ki];

            ki++;
          }
        }

        int[] intARGB = {
            Math.round(argb[0]),
            Math.round(argb[1]),
            Math.round(argb[2]),
            Math.round(argb[3])
        };

        for (int i = 0; i < intARGB.length; i++) {
          if (ARGB_OFFSET) {
            intARGB[i] += OFFSET_VAL;
          }

          // clamp values (0 - 255)
          intARGB[i] = Math.max(ARGB_MIN_VALUE, Math.min(intARGB[i], ARGB_MAX_VALUE));
        }

        if (!hasAlpha) {
          intARGB[0] = ARGB_MAX_VALUE;
        }

        outputPixels[pi] = (intARGB[0] << 24) | (intARGB[1] << 16) | (intARGB[2] << 8) | intARGB[3];
        pi++;
      }
    }

    output.setRGB(0, 0, width, height, outputPixels, 0, width);
  }

}
