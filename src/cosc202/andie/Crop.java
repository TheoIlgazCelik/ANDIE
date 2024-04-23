package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Crop implements ImageOperation {
  private final int x;
  private final int y;
  private final int width;
  private final int height;

  public Crop(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  @Override
  public BufferedImage apply(BufferedImage input) {
    if (width <= 0 || height <= 0) {
      return input;
    }
    
    BufferedImage croppedImage = input.getSubimage(x, y, width, height);
    BufferedImage output = new BufferedImage(width, height, input.getType());
    Graphics2D g = (Graphics2D) output.createGraphics();

    g.drawImage(croppedImage, 0, 0, null);
    return output;
  }
  
}
