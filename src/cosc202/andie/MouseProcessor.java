package cosc202.andie;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;

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

  public MouseProcessor(ImagePanel panel, BufferedImage image) {
    this.panel = panel;
    this.image = image;
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
  }

  public void mouseDragged(MouseEvent e) {
    x2 = e.getX();
    y2 = e.getY();
    updateSquare();
    paintShape();
    panel.repaint();
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
  }

  private void paintShape() {
    Graphics2D g = (Graphics2D) panel.getGraphics();
    g.setColor(new Color(150, 150, 150, 127));
    g.fillRect(x3, y3, width, height);
  }
}
