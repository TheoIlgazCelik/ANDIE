package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions {

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(new MeanFilterAction("Mean filter", null, "Apply a mean filter", Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SharpenFilterAction("Sharpen filter", null, "Apply a sharpen filter", Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new MedianFilterAction("Median Filter", null, "Apply a median filter", Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new GaussianBlurAction("Gaussian Blur", null, "Apply a Gaussian Blur", Integer.valueOf(KeyEvent.VK_G)));
    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Filter");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Create a new median-filter action.
     * </p>
     * 
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    public class MedianFilterAction extends ImageAction {
        private final int MIN_VALUE = 1;
        private final int MAX_VALUE = 10;
        
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel();
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            radiusSpinner.setValue(this.MIN_VALUE);
            addSpinnerChangeListener(radiusSpinner, this.MIN_VALUE, this.MAX_VALUE);

            int option = JOptionPane.showOptionDialog(null, radiusSpinner, "Enter filter radius",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }
            try{
            target.getImage().apply(new MedianFilter(radius));
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to blur an image with a Gaussian Blur
     * </p>
     * 
     * @see MeanFilter
     */
    public class GaussianBlurAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        GaussianBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianBlurAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, "Enter Gaussian Blur radius", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            try{
            target.getImage().apply(new GaussianBlur(radius));
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }


    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {
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
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel();
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            radiusSpinner.setValue(this.MIN_VALUE);
            addSpinnerChangeListener(radiusSpinner, this.MIN_VALUE, this.MAX_VALUE);

            int option = JOptionPane.showOptionDialog(null, radiusSpinner, "Enter filter radius (" + this.MIN_VALUE + "-" + this.MAX_VALUE + ")",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            try{
            target.getImage().apply(new MeanFilter(radius));
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public class SharpenFilterAction extends ImageAction {
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e){
            try{
            target.getImage().apply(new SharpenFilter());
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(target,"No image selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addSpinnerChangeListener(JSpinner radiusSpinner, int MIN, int MAX) {
        JFormattedTextField textField = ((JSpinner.DefaultEditor) radiusSpinner.getEditor()).getTextField();
        DefaultFormatter formatter = (DefaultFormatter) textField.getFormatter();
        formatter.setCommitsOnValidEdit(true);

        textField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                int value = (int) radiusSpinner.getValue();
                System.out.println(value);
                if (value < MIN) {
                    radiusSpinner.setValue(MIN);
                } else if (value > MAX) {
                    radiusSpinner.setValue(MAX);
                }
            }
    
        });
    }
}
