package cosc202.andie;

import java.awt.BasicStroke;
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
public class DrawLine implements ImageOperation, java.io.Serializable {
  /** Color of line. */
  private Color col;
  /** x coordinate of first point. */
  private int x1;
  /** y coordinate of first point. */
  private int y1;
  /** x coordinate of second point. */
  private int x2;
  /** y coordinate of second point */
  private int y2;
  /** Line width. */
  private float lineBs;

  /**
   * <p>
   * Construct a DrawLine with the given coordinates and line width.
   * </p>
   * 
   * @param col Color of drawing operation.
   * @param x1 First x coordinate of draw area.
   * @param y1 First y coordinate of draw area.
   * @param x2 Second x coordinate of draw area.
   * @param y2 Second y coordinate of draw area.
   * @param lineBs Width of outline for draw operation.
   */
  public DrawLine(Color col, int x1, int y1, int x2, int y2, float lineBs) {
    this.col = col;
    this.x1 = x1;
    this.y1 = y1;
    this.y2 = y2;
    this.x2 = x2;
    this.lineBs = lineBs;
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
    g.setStroke(new BasicStroke(lineBs));
    g.setColor(col);
    g.drawLine(x1, y1, x2, y2);
    return output;
  }
}
