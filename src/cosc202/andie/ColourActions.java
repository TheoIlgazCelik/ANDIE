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
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
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
        actions.add(new ColourCycleAction("Colour Channel Cycle", null, "Change Colour Cycle", null));
        actions.add(new BrightnessAndContrast("Brightness and Contrast", null, "Adjust Brightness and Contrast", null));
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

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    private int getPercentageFromUser(int MIN_VALUE, int MAX_VALUE) {
        int percentage = 100;

        // JSpinner with range (MIN_VALUE - MAX_VALUE) inclusive
        SpinnerNumberModel percentageModel = new SpinnerNumberModel(1, MIN_VALUE, MAX_VALUE, 1);
        JSpinner percentageSpinner = new JSpinner(percentageModel);
        // disable keyboard input
        ((JSpinner.DefaultEditor) percentageSpinner.getEditor()).getTextField().setEditable(false);

        ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle", Andie.locale);
        Object[] options2 = { b.getString("Ok"), b.getString("Cancel") };
        String optionMessage = b.getString("Percentage") + " (" + MIN_VALUE + " - " + MAX_VALUE + ")";

        // Pop-up dialog box to ask for the radius value.
        int option = JOptionPane.showOptionDialog(null, percentageSpinner, optionMessage,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, null);

        // Check the return value from the dialog box.
        // Ok = 0, Cancel = 1, Exit = -1
        if (option == 0) {
            percentage = percentageModel.getNumber().intValue();
        }

        return percentage;
    }

    /**
     * <p>
     * Action to blur an image with a Gaussian Blur
     * </p>
     * 
     */
    public class BrightnessAndContrast extends ImageAction {
        private final int MIN_VALUE = 1;
        private final int MAX_VALUE = 10;

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        BrightnessAndContrast(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianBlurAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            int radius = getPercentageFromUser(this.MIN_VALUE, this.MAX_VALUE);

            if (radius <= 0) {
                return;
            }

            // Create and apply the filter
            try {
                target.getImage().apply(new GaussianBlur(radius));
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception ex) {
                ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle", Andie.locale);
                Object[] options = { b.getString("Ok") };
                JOptionPane.showOptionDialog(target, b.getString("No_image"), "Error", JOptionPane.CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, options, null);
            }
        }

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
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
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

    /**
     * <p>
     * Action to cycle through an image's colour channels 
     * </p>
     * 
     * @see ColourCycle
     */
    public class ColourCycleAction extends ImageAction {
        
        private ColourCycle colourCycle;

        /**
         * <p>
         * Create a new colour-cycle action
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        public ColourCycleAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            colourCycle = new ColourCycle();
        }

        /**
         * <p>
         * Callback for when the colour-cycle action is triggered.
         * <p>
         * 
         * <p>
         * This method is called whenever the ColourCycleAction is triggered.
         * It changes the image's colour channels.
         * 
         * @param e The event triggering this callback
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(colourCycle);
            target.repaint();
            target.getParent().revalidate();
        }
    }
}
