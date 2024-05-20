package cosc202.andie;

import java.util.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p>
 * Actions provided by the Macro menu item
 * </p>
 * 
 * Allows the starting and stopping of recordings of macros and the opening of macro files.
 * 
 *
 * 
 * @author Finn Rimmer
 * @version 1.0
 */
public class MacroActions {
    /** A list of macro actions. */
    protected ArrayList<Action> actions;

     /**
     * <p>
     * Create a set of macro actions.
     * </p>
     */
    public MacroActions(){
        actions = new ArrayList<Action>();
        actions.add(new StartRecordingAction("Start Recording", null, "Start recording your macro", null));
        actions.add(new StopRecordingAction("Stop Recording", null, "Stop recording your macro", null));
        actions.add(new OpenMacroAction("Open Macro", null, "Open your macro", null));
    }

    /**
     * <p>
     * Create a menu containing the list of macro actions.
     * </p>
     * 
     * @return The macro menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Macro");
        for(Action action : actions){
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * The action to start recording a macro
     * </p>
     */
    public class StartRecordingAction extends ImageAction {
        /**
         * <p>
         * Start a macro recording.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        StartRecordingAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the start macro recording action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the macro start is triggered.
         * It starts recording operations
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            try{
                target.getImage().setRecordingTrue();
                
            } catch(Exception ex){
                ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle", Andie.locale);
                Object[] options = { b.getString("Ok") };
                JOptionPane.showOptionDialog(target, b.getString("No_image"), "Error", JOptionPane.CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, options, null);
            }
        }
    }
    /**
     * <p>
     * The action to stop a recording of a macro
     * </p>
     */
    public class StopRecordingAction extends ImageAction {
        /**
         * <p>
         * Stop a macro recording.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        StopRecordingAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the stop macro recording action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever a macro stop is triggered.
         * It stops recording operations and prompts the user to save the resulting operations file
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            if (target.getImage().isRecordingMacros() == false) {
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);
            
            if(result == JFileChooser.APPROVE_OPTION){
                
             try{ 
                String filePath = fileChooser.getSelectedFile().getCanonicalPath();
               target.getImage().saveMacro(filePath);
            } catch(Exception ex){
                ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle", Andie.locale);
                Object[] options = { b.getString("Ok") };
                JOptionPane.showOptionDialog(target, b.getString("No_image"), "Error", JOptionPane.CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, options, null);
            }
        }
    }
}
    /**
     * <p>
     * The action to open a macro recording and apply it to an image
     * </p>
     */
    public class OpenMacroAction extends ImageAction {
         /**
         * <p>
         * Open a macro recording
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        OpenMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the open macro recording action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever a macro open is triggered.
         * It prompts the user to open an operation file and then applies the specified file to the current image
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle",Andie.locale);
            
            JFileChooser fileChooser = new JFileChooser();

            FileFilter filter = new FileNameExtensionFilter("Operation File", "ops");
            fileChooser.setFileFilter(filter);

            fileChooser.setDialogTitle(b.getString("Open"));
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String opsFilePath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().openMacro(opsFilePath);
                } catch (Exception ex) {
                    Object[] options = {b.getString("Ok")};
                    JOptionPane.showOptionDialog(fileChooser, b.getString("Choose_image"), "Error", JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,null,options,null);
                }
            } 

            target.repaint();
            target.getParent().revalidate();
        }
    }
    

    
    

}
