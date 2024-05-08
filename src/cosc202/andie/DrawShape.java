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
public class DrawShape implements ImageOperation{
    private Color col;
    private int selectedShape;
    private boolean outline;
    private boolean fill;
    private int x;
    private int y;
    private int height;
    private int width;
    public DrawShape(Color col, int selectedShape, boolean outline, boolean fill, int width, int height, int x, int y){
        this.col = col;
        this.selectedShape = selectedShape;
        this.outline = outline;
        this.fill = fill;
        this.width = width;
        this.height = height;
        this.x=x;
        this.y=y;
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
    switch (selectedShape){
        case 0:
          if (fill)g.fillRect(x,y,width,height);
          if (outline)g.drawRect(x,y,width,height);
          break;
        case 1:
          if (fill)g.fillOval(x,y,width,height);
          if (outline)g.drawOval(x,y,width,height);
          break;
      }
    return output;
  }
}
