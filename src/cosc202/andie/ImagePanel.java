package cosc202.andie;

import java.awt.*;
import javax.swing.*;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well
 * as zooming
 * in and out.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {

    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;
    /**
     * Custom {@link MouseProcessor} to handle mouse input for drawing operations.
     */
    private MouseProcessor processor;

    /** First checkerboard square {@link Color}. */
    private static final Color CHECKERBOARD_COLOR_1 = Color.WHITE;
    /** Second checkerboard square {@link Color}. */
    private static final Color CHECKERBOARD_COLOR_2 = Color.GRAY;
    /** Default checkerboard individual square width/height. */
    private final static int CHECKERBOARD_SQUARE_SIZE = 15;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is
     * zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally
     * as a percentage.
     * </p>
     */
    private double scale;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;
    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * </p>
     * 
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100 * scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * 
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }

    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a
     * default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth() * scale),
                    (int) Math.round(image.getCurrentImage().getHeight() * scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.scale(scale, scale);
            paintCheckerboard(g2);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);

            if (processor != null) {
                processor.applyOnImagePanel(g2);
            }

            g2.dispose();
        }
    }

    private void paintCheckerboard(Graphics2D g2) {
        int endX = (int) (getPreferredSize().width / scale);
        int endY = (int) (getPreferredSize().height / scale);
        Color initialColor = CHECKERBOARD_COLOR_1;

        for (int y = 0; y < endY; y += CHECKERBOARD_SQUARE_SIZE) {
            Color color = initialColor;
            for (int x = 0; x < endX; x += CHECKERBOARD_SQUARE_SIZE) {
                // contain checkerboard within image bounds
                int width = Math.max(0, Math.min(CHECKERBOARD_SQUARE_SIZE, endX - x));
                int height = Math.max(0, Math.min(CHECKERBOARD_SQUARE_SIZE, endY - y));
                g2.setColor(color);
                g2.fillRect(x, y, width, height);

                color = swapCheckerboardColor(color);
            }
            initialColor = swapCheckerboardColor(initialColor);
        }

    }

    /**
     * <p>
     * Helper method for {@link #paintCheckerboard} to swap {@link Color}s.
     * </p>
     * 
     * @param color Current color to be swapped.
     * @return The alternate color than what was given as parameter.
     */
    private Color swapCheckerboardColor(Color color) {
        if (color == CHECKERBOARD_COLOR_1) {
            return CHECKERBOARD_COLOR_2;
        }

        return CHECKERBOARD_COLOR_1;

    }

    /**
     * <p>
     * Clear the current mouse listener and add new mouse listener for
     * MouseEvent triggered actions. Also sets the cursor to "Draw" cursor.
     * </p>
     * 
     * @param processor processor The MouseEvent callback to add to this ImagePanel.
     * @return Flag if drawing mode was sucessfully set on this ImagePanel.
     */
    public boolean setDrawingMode(MouseProcessor processor) {
        clearDrawingMode();
        addMouseListener(processor);
        addMouseMotionListener(processor);
        this.processor = processor;
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        return true;
    }

    /**
     * <p>
     * Clear the current mouse listener for MouseEvent triggered actions.
     * Also resets the cursor to default.
     * </p>
     */
    public void clearDrawingMode() {
        removeMouseListener(processor);
        removeMouseMotionListener(processor);
        this.processor = null;
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
