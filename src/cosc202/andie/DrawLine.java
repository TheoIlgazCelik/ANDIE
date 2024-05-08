package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to draw a {@link BufferedImage}.
 * </p>
 * 
 * @author Ilgaz Celik
 * @version 1.0
 */
public class DrawLine implements ImageOperation{
    private Color col;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    public DrawLine(Color col, int x1, int y1, int x2, int y2){
        this.col = col;
        this.x1 = x1;
        this.y1 = y1;
        this.y2 = y2;
        this.x2 = x2;
    }
    /**
   * <p>
   * Apply a drawing to an image.
   * </p>
   * 
   * @param input The image to apply the drawing to.
   * @return The image with the drawing.
   */
  @Override
  public BufferedImage apply(BufferedImage input) {
    
    BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
    output.setData(input.getData());
    Graphics2D g = (Graphics2D) output.createGraphics();
    g.setColor(col);
    g.drawLine(x1,y1,x2,y2);
    return output;
  }
}
