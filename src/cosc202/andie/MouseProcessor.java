package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
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
 * @author Ilgaz Celik
 * @version 1.0
 */
public class MouseProcessor extends MouseAdapter {
  /** Top-left x coordinate for shape operations. */
  private int x1;
  /** Top-left y coordinate for shape operations. */
  private int y1;

  /** Bottom-right x coordinate for shape operations. */
  private int x2;
  /** Bottom-right y coordinate for shape operations. */
  private int y2;

  /** Final x coordinate used for shape operations. */
  private int x3;
  /** Final y coordinate used for shape operations. */
  private int y3;

  /** Width for shape operations. */
  private int width;
  /** Height for shape operations. */
  private int height;

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
  private int op;

  /** Crop operation. See {@link Crop}. */
  public static final int CROP_OP = 1;
  /** Draw operation. See {@link DrawShape} and {@link DrawLine}. */
  public static final int DRAWING_OP = 2;

  /** Outline color of {@link Crop} operation */
  private static final Color CROP_OP_COLOR = new Color(255, 0, 0);

  // Drawing Variables
  Color fillCol;
  Color outlineCol;
  int selectedShape;
  boolean outline;
  boolean fill;
  float lineBs;
  float outlineBs;

  /** Mouse button flag */
  private boolean leftMouseButtonActive;

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
    this.op = op;
    this.MIN_X = 0;
    this.MIN_Y = 0;
    this.MAX_X = panel.getImage().getCurrentImage().getWidth() - 1;
    this.MAX_Y = panel.getImage().getCurrentImage().getHeight() - 1;
    this.leftMouseButtonActive = false;
  }

  /**
   * <p>
   *  Set line width of draw operation.
   * </p>
   * @param lineBs
   */
  public void setLineBs(float lineBs) {
      this.lineBs = lineBs;
  }

  /**
   * <p>
   *  Set line outline width of draw operation.
   * </p>
   * @param outlineBs
   */
  public void setOutlineBs(float outlineBs) {
      this.outlineBs = outlineBs;
  }

  /**
   * <p>
   *  Set fill color of draw operation.
   * </p>
   * @param fillCol
   */
  public void setFillCol(Color fillCol){
    this.fillCol = fillCol;
  }

  /**
   * <p>
   *  Set outline color of draw operation.
   * </p>
   * @param outlineCol
   */
  public void setOutlineCol(Color outlineCol){
    this.outlineCol = outlineCol;
  }

  /**
   * <p>
   *  Set shape of draw operation.
   * </p>
   * @param shape
   */
  public void setSelectedShape(int shape) {
    this.selectedShape = shape;
  }

  /**
   * <p>
   *  Outline flag if shape is have an outline.
   * </p>
   * @param outline
   */
  public void setOutline(boolean outline) {
    this.outline = outline;
  }

  /**
   * <p>
   *  Fill flag if shape is have a filled area.
   * </p>
   * @param fill
   */
  public void setFill(boolean fill) {
    this.fill = fill;
  }

  /**
   * <p>
   * Callback for a mouse click
   * </p>
   * 
   * @param e the triggered MouseEvent
   */
  public void mousePressed(MouseEvent e) {
    if (e.getButton() != MouseEvent.BUTTON1) {
      leftMouseButtonActive = false;
      return;
    }

    x1 = e.getX();
    y1 = e.getY();
    x2 = x1;
    y2 = y1;
    leftMouseButtonActive = true;
    validateCoordinates();
  }

  /**
   * <p>
   * Callback for a mouse drag event.
   * </p>
   * 
   * @param e the triggered MouseEvent
   */
  public void mouseDragged(MouseEvent e) {
    if (leftMouseButtonActive == false) {
      return;
    }

    x2 = e.getX();
    y2 = e.getY();
    validateCoordinates();
    panel.repaint();
  }

  /**
   * <p>
   * Callback for a mouse release event.
   * </p>
   * 
   * @param e the triggered MouseEvent
   */
  public void mouseReleased(MouseEvent e) {
    // right click cancels draw mode
    if (e.getButton() == MouseEvent.BUTTON3) {
      panel.repaint();
      panel.clearDrawingMode();
      return;
    }

    if (leftMouseButtonActive == false) {
      return;
    }

    validateCoordinates();
    panel.repaint();
    applyToBufferedImage();

    panel.clearDrawingMode();

    leftMouseButtonActive = false;
  }

  /**
   * <p>
   * Support method to keep x and y in bounds and calculate width and height data
   * fields.
   * </p>
   */
  private void validateCoordinates() {
    x1 = Math.max(MIN_X, Math.min(MAX_X, x1));
    x2 = Math.max(MIN_X, Math.min(MAX_X, x2));
    y1 = Math.max(MIN_Y, Math.min(MAX_Y, y1));
    y2 = Math.max(MIN_Y, Math.min(MAX_Y, y2));

    // drawing line doesn't use x3/y3 and width/height as below
    if (selectedShape == 2 && op == DRAWING_OP) {
      return;
    }

    // ensure x3/y3 represent lesser coordinate and width/height are calculate from
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
   * changes to the images.
   * </p>
   * 
   * @param g2d Graphics object supplied by the {@link ImagePanel}
   */
  public void applyOnImagePanel(Graphics2D g2d) {
    switch (op) {
      case CROP_OP:
        g2d.setColor(CROP_OP_COLOR);
        g2d.drawRect(x3, y3, width, height);
        break;
      case DRAWING_OP:
        switch (selectedShape) {
          case 0:
          g2d.setStroke(new BasicStroke(outlineBs));
          if (fill) {
            g2d.setColor(fillCol);
            g2d.drawRect(x3,y3,width,height);
            g2d.fillRect(x3,y3,width,height);
          }
          if (outline){
            g2d.setColor(outlineCol);
            g2d.drawRect(x3,y3,width,height);
          }
          break;
          case 1:
          g2d.setStroke(new BasicStroke(outlineBs));
          if (fill) {
            g2d.setColor(fillCol);
            g2d.drawOval(x3,y3,width,height);
            g2d.fillOval(x3,y3,width,height);
          }
          if (outline){
            g2d.setColor(outlineCol);
            g2d.drawOval(x3,y3,width,height);
          }
          break;
          case 2:
            g2d.setStroke(new BasicStroke(lineBs));
            g2d.setColor(fillCol);
            g2d.drawLine(x1, y1, x2, y2);
            break;
        }
    }

  }

  /**
   * <p>
   * Support method to apply {@link ImageOperation} on {@link BufferedImage}.
   * </p>
   * 
   */
  private void applyToBufferedImage() {
    switch (this.op) {
      case CROP_OP:
        panel.getImage().apply(new Crop(x3, y3, width, height));
        break;
      case DRAWING_OP:
        if (selectedShape != 2) {
          panel.getImage().apply(new DrawShape(fillCol, outlineCol, selectedShape, outline, fill, width, height, x3, y3, outlineBs));
        } else {
          panel.getImage().apply(new DrawLine(fillCol, x1, y1, x2, y2, lineBs));
        }
        break;
    }
  }
  
}
