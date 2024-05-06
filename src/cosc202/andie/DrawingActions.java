package cosc202.andie;

import java.util.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

 /**
 * <p>
 * Actions provided by the Drawing menu.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Ilgaz Celik
 * @version 1.0
 */
public class DrawingActions {
    
    /** A list of actions for the Draw menu. */
    protected ArrayList<Action> actions;
    private Color col = new Color(0,0,0);
    private boolean outline = true;
    private boolean fill = false;
    private int selectedShape = 0; // 0 = rectangle, 1 = circle, 2 = line


    private Color getColor(){
        return col;
    }
    private boolean getOutline(){
        return outline;
    }
    private boolean getFill(){
        return fill;
    }
    private int getSelectedShape(){
        return selectedShape;
    }
    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public DrawingActions() {
        actions = new ArrayList<Action>();
        actions.add(new DrawOptions("Drawing Options", null, "Drawing Options", null));
        actions.add(new Draw("Draw", null, "Draw", Integer.valueOf(KeyEvent.VK_D)));
    }

    /**
     * <p>
     * Create a menu containing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu drawMenu = new JMenu("Drawing");

        for (Action action: actions) {
            drawMenu.add(new JMenuItem(action));
        }

        return drawMenu;
    }
    private void getValuesFromUser() {
        ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle", Andie.locale);
        // JSpinner with range (MIN_VALUE - MAX_VALUE) inclusive
        SpinnerNumberModel redModel = new SpinnerNumberModel(col.getRed(), 0, 255, 1);
        SpinnerNumberModel greenModel = new SpinnerNumberModel(col.getGreen(), 0, 255, 1);
        SpinnerNumberModel blueModel = new SpinnerNumberModel(col.getBlue(), 0, 255, 1);
        JToggleButton fillButton = new JToggleButton(b.getString("Fill"));
        JToggleButton outlineButton = new JToggleButton(b.getString("Outline"));

        JLabel redLabel = new JLabel(b.getString("Red"));
        JLabel greenLabel = new JLabel(b.getString("Green"));
        JLabel blueLabel = new JLabel(b.getString("Blue"));

        JSpinner redSpinner = new JSpinner(redModel);
        JSpinner greenSpinner = new JSpinner(greenModel);
        JSpinner blueSpinner = new JSpinner(blueModel);

        if(fill)fillButton.doClick();
        if(outline)outlineButton.doClick();

        ButtonGroup bg = new ButtonGroup();

        JRadioButton rect = new JRadioButton("Rectangle");
        JRadioButton circle = new JRadioButton("Circle");
        JRadioButton line = new JRadioButton("Line");

        bg.add(rect);
        bg.add(circle);
        bg.add(line);

        switch (selectedShape){
            case 0:
                rect.setSelected(true);
                break;
            case 1:
                circle.setSelected(true);
                break;
            case 2:
                line.setSelected(true);
                break;
        }

        JComponent[] spinners = new JComponent[]{redLabel, redSpinner, greenLabel, greenSpinner, blueLabel, blueSpinner, fillButton, outlineButton, rect, circle, line};

        //(((JSpinner.DefaultEditor)redSpinner.getEditor()).getTextField()).setColumns(35);

        Object[] options2 = { b.getString("Ok"), b.getString("Cancel") };
        String optionMessage = b.getString("Drawing_Option_Pane");
        int option;
        boolean firstTry = true;
        do {
        // Pop-up dialog box to ask for the radius value.
        if (!firstTry){
            if (line.isSelected() && (outlineButton.isSelected() || !fillButton.isSelected())){
                JOptionPane.showOptionDialog(null, b.getString("Line_Outline"), "Error", JOptionPane.CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, new Object[]{b.getString("Ok")}, null);
            }else {
                JOptionPane.showOptionDialog(null, b.getString("Fill_Outline"), "Error", JOptionPane.CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, new Object[]{b.getString("Ok")}, null);
            }
        }
        option = JOptionPane.showOptionDialog(null, spinners, optionMessage,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, null);
        firstTry = false;
        } while ((!fillButton.isSelected() && !outlineButton.isSelected() && option==0)||(line.isSelected() && (outlineButton.isSelected() || !fillButton.isSelected())));

        // Check the return value from the dialog box.
        // Ok = 0, Cancel = 1, Exit = -1
        if (option == 0) {
            col = new Color(redModel.getNumber().intValue(),greenModel.getNumber().intValue(),blueModel.getNumber().intValue());
            fill = fillButton.isSelected();
            outline = outlineButton.isSelected();
            if (rect.isSelected())selectedShape = 0;
            if (circle.isSelected())selectedShape = 1;
            if (line.isSelected())selectedShape = 2;
        }
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class DrawOptions extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        DrawOptions(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        



        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            getValuesFromUser();
        }
    }
    public class Draw extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Draw(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
        }

        



        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            MouseProcessor mp = new MouseProcessor(target, 2);
            mp.setCol(getColor());
            mp.setFill(getFill());
            mp.setOutline(getOutline());
            mp.setSelectedShape(getSelectedShape());
            target.setDrawingMode(mp);
        }
    }
}
