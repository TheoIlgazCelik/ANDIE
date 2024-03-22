package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 * 
 * <p>
 * The View menu contains actions that affect how the image is displayed in the application.
 * These actions do not affect the contents of the image itself, just the way it is displayed.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 * 
 * MATT
 */
public class ViewActions {
    
    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        actions = new ArrayList<Action>();
        actions.add(new ZoomInAction("Zoom In", null, "Zoom In", Integer.valueOf(KeyEvent.VK_PLUS)));
        actions.add(new ZoomOutAction("Zoom Out", null, "Zoom Out", Integer.valueOf(KeyEvent.VK_MINUS)));
        actions.add(new ZoomFullAction("Zoom Full", null, "Zoom Full", Integer.valueOf(KeyEvent.VK_1)));
        actions.add(new RotateRightAction("Rotate 90\u00b0 right", null, "Rotate image 90\u00b0 to the right", Integer.valueOf(KeyEvent.VK_2)));
        actions.add(new RotateLeftAction("Rotate 90\u00b0 left", null, "Rotate image 90\u00b0 to the left", Integer.valueOf(KeyEvent.VK_3)));
        actions.add(new RotateFullAction("Rotate 180\u00b0", null, "Rotate image 180\u00b0", Integer.valueOf(KeyEvent.VK_4)));
        actions.add(new FlipHorizontalAction("Flip Horizontally", null, "Flip image horizontally", Integer.valueOf(KeyEvent.VK_5)));
        actions.add(new FlipVerticalAction("Flip Vertically", null, "Flip image vertically", Integer.valueOf(KeyEvent.VK_6)));
        actions.add(new ResizeAction("Resize Image", null, "Resize Image", Integer.valueOf(KeyEvent.VK_R)));
    }

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     * 
     * @return The view menu UI element.
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu("View");

        for (Action action: actions) {
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
    }
    /**
     * <p>
     * Action to Resize an Image
     * </p>
     * 
     */
    public class ResizeAction extends ImageAction {

        ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {

            int sizePercentageIncrease100 = 100;
            ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle",Andie.locale);
            SpinnerNumberModel increaseModel = new SpinnerNumberModel(100, 1, 1000, 1); //new spinner model
            JSpinner increaseSpinner = new JSpinner(increaseModel);
            Object[] options = {b.getString("Ok"),b.getString("Cancel")};
            int option = JOptionPane.showOptionDialog(null, increaseSpinner, b.getString("Enter_size"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                sizePercentageIncrease100 = increaseModel.getNumber().intValue();
            }
            try{
            target.getImage().apply(new ResizeImage(sizePercentageIncrease100));
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomInAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomInAction is triggered.
         * It increases the zoom level by 10%, to a maximum of 200%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
            target.setZoom(target.getZoom()+10);
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }


    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomOutAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-out action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-out action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomOutAction is triggered.
         * It decreases the zoom level by 10%, to a minimum of 50%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
            target.setZoom(target.getZoom()-10);
            target.repaint();
            target.getParent().revalidate();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomFullAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomFullAction is triggered.
         * It resets the Zoom level to 100%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
            target.setZoom(100);
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to rotate the image 90º to the right
     * </p>
     * 
     * @author Matthew Rae
     */
    public class RotateRightAction extends ImageAction {
        /**
         * <p>
         * Create a RotateRight action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RotateRightAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the RotateRight action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateRight is triggered.
         * It applies an {@link RotateRight} action, which rotates the image 90 degrees
         * to the right.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Create and apply the rotation
            try{
            target.getImage().apply(new RotateRight());
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to rotate the image 90º to the left
     * </p>
     * 
     * @author Matthew Rae
     */
    public class RotateLeftAction extends ImageAction {
        /**
         * <p>
         * Create a RotateLeft action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RotateLeftAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the RotateLeft action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateLeft is triggered.
         * It applies an {@link RotateLeft} action, which rotates the image 90 degrees
         * to the left.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Create and apply the rotation
            try{
            target.getImage().apply(new RotateLeft());
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to rotate the image 180º
     * </p>
     * 
     * @author Matthew Rae
     */
    public class RotateFullAction extends ImageAction {
        /**
         * <p>
         * Create a RotateFull action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RotateFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the RotateFull action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateFull is triggered.
         * It applies an {@link RotateFull} action, which rotates the image 180 degrees.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Create and apply the rotation
            try{
            target.getImage().apply(new RotateFull());
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to flip the image horizontally
     * </p>
     * 
     * @author Matthew Rae
     */
    public class FlipHorizontalAction extends ImageAction {
        /**
         * <p>
         * Create a FlipHorizontal action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FlipHorizontalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the FlipHorizontal action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the HorizontalFlip is triggered.
         * It applies an {@link FlipHorizontal} action, which flips the image across
         * the x-axis.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Create and apply the rotation
            try{
            target.getImage().apply(new FlipHorizontal());
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to flip the image vertically
     * </p>
     * 
     * @author Matthew Rae
     */
    public class FlipVerticalAction extends ImageAction {
        /**
         * <p>
         * Create a FlipVertical action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FlipVerticalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the FlipVertical action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FlipVertical is triggered.
         * It applies an {@link FlipVertical} action, which flips the image across
         * the y-axis.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Create and apply the rotation
            try{
            target.getImage().apply(new FlipVertical());
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }


}
