package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class MacroActions {

    protected ArrayList<Action> actions;

    public MacroActions(){
        actions = new ArrayList<Action>();
        actions.add(new StartRecordingAction("Start Recording", null, "Start recording your macro", null));
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
        }

        public void saveOperations(){
           
        }

        public void actionPerformed(ActionEvent e){
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);
            
            if(result == JFileChooser.APPROVE_OPTION){
            try{
                String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                target.getImage().saveMacro(imageFilepath);
               
            } catch(Exception ex){
                ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle", Andie.locale);
                Object[] options = { b.getString("Ok") };
                JOptionPane.showOptionDialog(target, b.getString("No_image"), "Error", JOptionPane.CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, options, null);
            }
        }
    }

    public class OpenMacroAction extends ImageAction {
        OpenMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e){
            
        }
    }
    }

    
    

}
