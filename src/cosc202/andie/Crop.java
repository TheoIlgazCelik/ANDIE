package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * <p>
 * ImageOperation to crop a {@link BufferedImage}.
 * </p>
 * 
 * <p>
 * The crop applies to the area of the image given by x and y coordinates,
 * width and height
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class Crop implements ImageOperation. java.io.Serializable {
  /** Top left x coordinate of crop area. */
  private final int x;
  /** Top left y coordinate of crop area. */
  private final int y;
  /** Width of crop area. */
  private final int width;
  /** Height of crop area. */
  private final int height;

  /**
   * <p>
   * Construct a Crop with the given coordinates and size.
   * </p>
   * 
   * @param x Top left x coordinate of crop area
   * @param y Top left y coordinate of crop area
   * @param width Width of crop area
   * @param height Height of crop area
   */
  public Crop(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * <p>
   * Apply a Crop to an image.
   * </p>
   * 
   * @param input The image to apply the Crop to.
   * @return The cropped image.
   */
  @Override
  public BufferedImage apply(BufferedImage input) {
    BufferedImage croppedImage = input.getSubimage(x, y, width, height);
    BufferedImage output = new BufferedImage(width, height, input.getType());
    Graphics2D g = (Graphics2D) output.createGraphics();

    g.drawImage(croppedImage, 0, 0, null);
    return output;
  }

}
