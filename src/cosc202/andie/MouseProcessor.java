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
 * @author Matthew RaeW
 * @version 1.0
 */
public class MouseProcessor extends MouseAdapter {
  // x - y for top left corner of mouse
  private int x1;
  private int y1;
  // x - y for bottom right corner of mouse
  private int x2;
  private int y2;

  // x - y for final coordinate to operate on
  private int x3;
  private int y3;

  // width and height for operation
  private int width;
  private int height;

  // true if mouse is pressed or dragged
  private boolean selectMode = false;

  // panel to draw "select mode" indicators onto - e.g. red rectangle for crop
  // operation
  private final ImagePanel panel;
  private final int MIN_X;
  private final int MIN_Y;
  private final int MAX_X;
  private final int MAX_Y;

  private final int op;
  // Possible operations
  public static final int CROP_OP = 1;
  public static final int DRAWING_OP = 2;

  // Drawing Variables
  Color fillCol;
  Color outlineCol;
  int selectedShape;
  boolean outline;
  boolean fill;

  public MouseProcessor(ImagePanel panel, int op) {
    this.panel = panel;
    this.op = op;
    this.MIN_X = 0;
    this.MIN_Y = 0;
    this.MAX_X = panel.getImage().getCurrentImage().getWidth() - 1;
    this.MAX_Y = panel.getImage().getCurrentImage().getHeight() - 1;
  }
  public void setFillCol(Color fillCol){
    this.fillCol = fillCol;
  }
  public void setOutlineCol(Color outlineCol){
    this.outlineCol = outlineCol;
  }
  public void setSelectedShape(int shape){
    this.selectedShape = shape;
  }
  public void setOutline(boolean outline){
    this.outline = outline;
  }
  public void setFill(boolean fill){
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
    x1 = e.getX();
    y1 = e.getY();
    x2 = x1;
    y2 = y1;
    selectMode = true;
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
    selectMode = false;
    updateSquare();
    panel.repaint();

    // perform operation on image
    switch (this.op) {
      case CROP_OP:
        panel.getImage().apply(new Crop(x3, y3, width, height));
        break;
      case DRAWING_OP:
          if(selectedShape!=2){
            panel.getImage().apply(new DrawShape(fillCol, selectedShape, outline, fill, width, height, x3, y3));
          }else {
            panel.getImage().apply(new DrawLine(fillCol, x1,y1, x2, y2));
          }
        break;
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
    if (selectedShape == 2 && op == DRAWING_OP) {
      return;
    }
    
    // enforce x1 is the lesser x coordinate
    if (x1 < x2) {
      x3 = x1;
      width = x2 - x1;
    } else {
      x3 = x2;
      width = x1 - x2;
    }

    // enforce y1 is the lesser y coordinate
    if (y1 < y2) {
      y3 = y1;
      height = y2 - y1;
    } else {
      y3 = y2;
      height = y1 - y2;
    }

    // x and y bounds checks
    if (x3 < MIN_X) {
      x3 = MIN_X;
    } else if (x3 > MAX_X) {
      x3 = MAX_X;
    }
    if (y3 < MIN_Y) {
      y3 = MIN_Y;
    } else if (y3 > MAX_Y) {
      y3 = MAX_Y;
    }

    if (x3 + width > MAX_X) {
      width = MAX_X - x3;
    }
    if (y3 + height > MAX_Y) {
      height = MAX_Y - y3;
    }

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
      switch (this.op) {
        case CROP_OP:
          g2d.setColor(new Color(255, 0, 0));
          g2d.drawRect(x3, y3, width, height);
          break;
        case DRAWING_OP:
          g2d.setColor(fillCol);
          switch (selectedShape){
            case 0:
              if (fill)g2d.fillRect(x3,y3,width,height);
              if (outline)g2d.drawRect(x3,y3,width,height);
              break;
            case 1:
              if (fill)g2d.fillOval(x3,y3,width,height);
              if (outline)g2d.drawOval(x3,y3,width,height);
              break;
            case 2:
              g2d.drawLine(x1, y1, x2, y2);
              break;
          }
          break;
      }
    }
  }
}
