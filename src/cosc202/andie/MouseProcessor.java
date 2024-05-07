package cosc202.andie;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * <p>
 * Extended {@link MouseAdapter} to get coordinates via mouse input to
 * apply operations on BufferedImage.
 * </p>
 * 
 * <p>
 * This {@link MouseAdapter} is utilised by many operations that require
 * "drawing" on the {@link ImagePanel} or BufferedImage by tracking
 * mouse coordinates and drawing with it's paint() method. It can call the
 * {@link EditableImage} apply method to apply an {@link ImageOperation} when
 * required.
 * </p>
 * 
 * @author Matthew Rae
 * @version 1.0
 */
public class MouseProcessor extends MouseAdapter {
  /** Top-left x coordinate. */
  private int x1;
  /** Top-left y coordinate. */
  private int y1;

  /** Bottom-right x coordinate. */
  private int x2;
  /** Bottom-right y coordinate. */
  private int y2;

  /** Final x coordinate used for operation. */
  private int x3;
  /** Final y coordinate used for operation. */
  private int y3;

  /** Width for operation. */
  private int width;

  /** Height for operation. */
  private int height;

  /** True if mouse is pressed or dragged. */
  private boolean selectMode;

  /**
   * Panel to draw "select mode" indicators onto - e.g. red rectangle for crop
   * operation.
   */
  private final ImagePanel panel;

  /** Minimum x bound of image. */
  private final int MIN_X;
  /** Minimum y bound of image. */
  private final int MIN_Y;
  /** Maximum x bound of image. */
  private final int MAX_X;
  /** Maximum y bound of image. */
  private final int MAX_Y;

  /** Current draw operation. */
  private final int OP;

  /** Crop operation. See {@link Crop}. */
  public static final int CROP_OP = 1;

  /**
   * <p>
   * Construct a MouseProcessor to act as a {@link MouseAdapter} for drawing
   * actions.
   * </p>
   * 
   * @param panel The ImagePanel to apply this MouseProcessor to
   * @param op    The current drawing operation
   */
  public MouseProcessor(ImagePanel panel, int op) {
    this.panel = panel;
    this.OP = op;
    this.MIN_X = 0;
    this.MIN_Y = 0;
    this.MAX_X = panel.getImage().getCurrentImage().getWidth();
    this.MAX_Y = panel.getImage().getCurrentImage().getHeight();
    this.selectMode = false;
  }

  /**
   * <p>
   * Callback for a mouse click
   * </p>
   * 
   * @param e the triggered MouseEvent
   */
  public void mousePressed(MouseEvent e) {
    x1 = e.getX();
    y1 = e.getY();
    x2 = x1;
    y2 = y1;
    updateSquare();
    panel.repaint();
  }

  /**
   * <p>
   * Callback for a mouse release
   * </p>
   * 
   * @param e the triggered MouseEvent
   */
  public void mouseReleased(MouseEvent e) {
    updateSquare();
    panel.repaint();

    if (selectMode) {
      // perform operation on image
      switch (this.OP) {
        case CROP_OP:
          panel.getImage().apply(new Crop(x3, y3, width, height));
          break;
      }
    }

    // exit drawing mode
    panel.clearDrawingMode();
  }

  /**
   * <p>
   * Callback for a mouse Drag
   * </p>
   * 
   * @param e the triggered MouseEvent
   */
  public void mouseDragged(MouseEvent e) {
    selectMode = true;
    x2 = e.getX();
    y2 = e.getY();
    updateSquare();
    panel.repaint();
  }

  /**
   * <p>
   * Support method to keep x and y in bounds and calculate width and height data
   * fields.
   * </p>
   */
  private void updateSquare() {
    // x y bounds check (between MIN and MAX)
    x1 = Math.max(MIN_X, Math.min(MAX_X, x1));
    x2 = Math.max(MIN_X, Math.min(MAX_X, x2));
    y1 = Math.max(MIN_Y, Math.min(MAX_Y, y1));
    y2 = Math.max(MIN_Y, Math.min(MAX_Y, y2));

    // ensure x3/y3 represent lesser coordinate and width/height are calculated from
    // greater coordinate
    x3 = Math.min(x1, x2);
    y3 = Math.min(y1, y2);

    width = Math.max(x1, x2) - x3;
    height = Math.max(y1, y2) - y3;

  }

  /**
   * <p>
   * Support method to paint shapes over image panel (for user feedback purposes,
   * such as a red rectangle for user to see crop area). These aren't permanent
   * changes to the images/
   * </p>
   * 
   * @param g2d Graphics object supplied by the {@link ImagePanel}
   */
  public void paint(Graphics2D g2d) {
    if (selectMode) {
      switch (OP) {
        case CROP_OP:
          g2d.setColor(new Color(255, 0, 0));
          g2d.drawRect(x3, y3, width, height);
          break;
      }
    }
  }
}
