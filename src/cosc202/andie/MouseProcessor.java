package cosc202.andie;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

public class MouseProcessor extends MouseAdapter {
  private int x1;
  private int y1;
  private int x2;
  private int y2;
  private int x3;
  private int y3;
  private int width;
  private int height;
  private boolean selectMode = false;
  private ImagePanel panel;
  private BufferedImage image;
  private int op;
  private static final int CROP_OP = 1;
  private int MIN_X = 0;
  private int MIN_Y = 0;
  private int MAX_X = 0;
  private int MAX_Y = 0;

  public MouseProcessor(ImagePanel panel, BufferedImage image) {
    this.panel = panel;
    this.image = image;
    this.op = CROP_OP;
    this.MAX_X = image.getWidth() - 1;
    this.MAX_Y = image.getHeight() - 1;
  }

  public void mousePressed(MouseEvent e) {
    x1 = e.getX();
    y1 = e.getY();
    x2 = x1;
    y2 = y1;
    selectMode = true;
    updateSquare();
  }

  public void mouseReleased(MouseEvent e) {
    selectMode = false;
    updateSquare();

    switch (this.op) {
      case CROP_OP:
        panel.getImage().apply(new Crop(x3, y3, width, height));
        panel.clearDrawingMode();
        break;
    }

    System.out.println("released");
  }

  public void mouseDragged(MouseEvent e) {
    x2 = e.getX();
    y2 = e.getY();
    updateSquare();
  }

  private void updateSquare() {
    if (x1 < x2) {
      x3 = x1;
      width = x2 - x1;
    } else {
      x3 = x2;
      width = x1 - x2;
    }

    if (y1 < y2) {
      y3 = y1;
      height = y2 - y1;
    } else {
      y3 = y2;
      height = y1 - y2;
    }

    if (x3 < MIN_X) x3 = MIN_X;
    if (x3 > MAX_X) x3 = MAX_X;
    if (y3 < MIN_Y) y3 = MIN_Y;
    if (y3 > MAX_Y) y3 = MAX_Y;
    if (x3 + width > MAX_X) {
      width = MAX_X - x3;
    }
    if (y3 + height > MAX_Y) {
      height = MAX_Y - y3;
    }

    panel.repaint();
  }

  public void paint(Graphics2D g2d) {
    if (selectMode) {
      g2d.setColor(new Color(255, 0, 0));
      g2d.drawRect(x3, y3, width, height);
    }
  }
}
