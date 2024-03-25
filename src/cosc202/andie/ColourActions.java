package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel directly 
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {
    
    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction("Greyscale", null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new ColorInversionAction("Invert Color", null, "Invert image color", null));
    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Colour");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                //JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
                ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle",Andie.locale);
                Object[] options = {b.getString("Ok")};
                JOptionPane.showOptionDialog(target, b.getString("No_image"), "Error", JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,null,options,null);
            }
        }

    }

    /**
     * <p>
     * Action to invert an image.
     * </p>
     * 
     * @see InvertColor
     */
    public class ColorInversionAction extends ImageAction {
        /**
         * <p>
         * Create a new invert color action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        public ColorInversionAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the invert color action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ColorInversionAction is triggered.
         * It inverts the colors of the images.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
            target.getImage().apply(new InvertColor());
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                //JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
                ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle",Andie.locale);
                Object[] options = {b.getString("Ok")};
                JOptionPane.showOptionDialog(target, b.getString("No_image"), "Error", JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,null,options,null);
            }
        }
    }

}
