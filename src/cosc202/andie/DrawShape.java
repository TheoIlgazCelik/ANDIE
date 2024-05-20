package cosc202.andie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to draw a Shape
 * </p>
 * 
 * @author Ilgaz Celik
 * @version 1.0
 */
public class DrawShape implements ImageOperation, java.io.Serializable{
    /** Fill color of shape.*/
    private Color fillCol;
    /** Outline color of shape. */
    private Color outlineCol;
    /** Flag for which shape is to draw. */
    private int selectedShape;
    /** Flag if the shape includes an outline. */
    private boolean outline;
    /** Flag if the shape is filled inside the outline. */
    private boolean fill;
    /** Top-left x co-ordinate of shape. */
    private int x;
    /** Top-left y co-ordinate of shape. */
    private int y;
    /** Height of shape to draw. */
    private int height;
    /** Width of shape to draw. */
    private int width;
    /** Outline width of shape. */
    private float outlineBs;

    /**
   * <p>
   * Construct a DrawShape with multiple custom options.
   * </p>
   * 
   * @param fillCol Fill color of shape.
   * @param outlineCol Outline color of shape.
   * @param selectedShape Flag for which shape is to draw.
   * @param outline Flag if the shape includes an outline.
   * @param fill Flag if the shape is filled inside the outline.
   * @param width Width of shape to draw.
   * @param height Height of shape to draw.
   * @param x Top-left x co-ordinate of shape.
   * @param y Top-left y co-ordinate of shape.
   * @param outlineBs Outline width of shape.
   */
    public DrawShape(Color fillCol, Color outlineCol, int selectedShape, boolean outline, boolean fill, int width, int height, int x, int y, float outlineBs){
        this.fillCol = fillCol;
        this.outlineCol = outlineCol;
        this.selectedShape = selectedShape;
        this.outline = outline;
        this.fill = fill;
        this.width = width;
        this.height = height;
        this.x=x;
        this.y=y;
        this.outlineBs=outlineBs;
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
    g.setStroke(new BasicStroke(outlineBs));
    switch (selectedShape){
        case 0:
          if (fill) {
            g.setColor(fillCol);
            g.drawRect(x,y,width,height);
            g.fillRect(x,y,width,height);
          }
          if (outline){
            g.setColor(outlineCol);
            g.drawRect(x,y,width,height);
          }
          break;

        case 1:
        if (fill) {
          g.setColor(fillCol);
          g.drawOval(x,y,width,height);
          g.fillOval(x,y,width,height);
        }
        if (outline){
          g.setColor(outlineCol);
          g.drawOval(x,y,width,height);
        }
      }
    return output;
  }
}
