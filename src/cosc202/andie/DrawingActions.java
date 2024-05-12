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
    private Color fillCol = new Color(0,0,0);
    private Color outlineCol = new Color(0,0,0);
    private boolean outline = true;
    private boolean fill = false;
    private int selectedShape = 0; // 0 = rectangle, 1 = oval, 2 = line


    private Color getFillColor(){
        return fillCol;
    }
    private Color getOutlineColor(){
        return outlineCol;
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

        JToggleButton fillButton = new JToggleButton(b.getString("Fill"));
        JToggleButton outlineButton = new JToggleButton(b.getString("Outline"));

        if(fill)fillButton.doClick();
        if(outline)outlineButton.doClick();

        ButtonGroup bg = new ButtonGroup();

        JRadioButton rect = new JRadioButton(b.getString("Rectangle"));
        JRadioButton oval = new JRadioButton(b.getString("Oval"));
        JRadioButton line = new JRadioButton(b.getString("Line")); 

        bg.add(rect);
        bg.add(oval);
        bg.add(line);

        switch (selectedShape){
            case 0:
                rect.setSelected(true);
                break;
            case 1:
                oval.setSelected(true);
                break;
            case 2:
                line.setSelected(true);
                break;
        }

        JComponent[] spinners = new JComponent[]{fillButton, outlineButton, rect, oval, line};

        Object[] options2 = { b.getString("Ok"), b.getString("Cancel") };
        String optionMessage = b.getString("Drawing_Option_Pane");
        int option;
        boolean firstTry = true;
        do {
        if (!firstTry){
            if (line.isSelected() && (outlineButton.isSelected() || !fillButton.isSelected())){
                JOptionPane.showOptionDialog(null, b.getString("Line_Outline"), "Error", JOptionPane.CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, new Object[]{b.getString("Ok")}, null);
            }else {
                JOptionPane.showOptionDialog(null, b.getString("Fill_Outline"), b.getString("Error"), JOptionPane.CANCEL_OPTION,
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
            fill = fillButton.isSelected();
            outline = outlineButton.isSelected();
            if (rect.isSelected())selectedShape = 0;
            if (oval.isSelected())selectedShape = 1;
            if (line.isSelected())selectedShape = 2;
        }
        if (fill){
            JColorChooser jc = new JColorChooser(this.fillCol);
            fillCol=JColorChooser.showDialog(jc, b.getString("Color_Of")+" "+b.getString("Fill"), fillCol);
        }
        if (outline){
            JColorChooser jc = new JColorChooser(this.outlineCol);
            outlineCol=JColorChooser.showDialog(jc, b.getString("Color_Of")+" "+b.getString("Outline"), outlineCol);
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
            mp.setFillCol(getFillColor());
            mp.setOutlineCol(getOutlineColor());
            mp.setFill(getFill());
            mp.setOutline(getOutline());
            mp.setSelectedShape(getSelectedShape());
            target.setDrawingMode(mp);
        }
    }
}
