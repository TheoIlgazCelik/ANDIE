package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MacroActions {

    protected ArrayList<Action> actions;

    public MacroActions(){
        actions = new ArrayList<Action>();
        actions.add(new StartRecordingAction("Start Recording", null, "Start recording your macro", null));
        actions.add(new StopRecordingAction("Stop Recording", null, "Stop recording your macro", null));
        actions.add(new OpenMacroAction("Open Macro", null, "Open your macro", null));
    }
    
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Macro");
        for(Action action : actions){
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    public class StartRecordingAction extends ImageAction {
        StartRecordingAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
        }

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

    public class StopRecordingAction extends ImageAction {
        StopRecordingAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        }


        public void actionPerformed(ActionEvent e){
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

    public class OpenMacroAction extends ImageAction {
        OpenMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        }

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
