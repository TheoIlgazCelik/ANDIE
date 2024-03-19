package cosc202.andie;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;


public class LangActions   {
    protected ArrayList<Action> actions;

    public LangActions() {
        actions = new ArrayList<Action>();
        //Include languages here with action.add(. . .) //see fileActions for format
        actions.add(new LangEnglishAction("English",null,"Change language to english",null));
        actions.add(new LangSpanishAction("Spanish",null,"Change language to spanish",null));
        actions.add(new LangMaoriAction("Maori",null,"Change language to maori",null));
    }

    public JMenu createMenu() {
        ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle",Andie.locale);
        JMenu langMenu = new JMenu(b.getString("Language"));
        for (Action action : actions) {
            langMenu.add(new JMenuItem(action));
        }
        return langMenu;
    }

    public class LangSpanishAction extends ImageAction {

        public LangSpanishAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        
        public void actionPerformed(ActionEvent e) {
            Andie.locale = new Locale("es");
            langChange();
        }
    
    }
    public class LangEnglishAction extends ImageAction {

        public LangEnglishAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            Andie.locale = new Locale("en");
            langChange();
        }
    
    }

    public class LangMaoriAction extends ImageAction {

        public LangMaoriAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            Andie.locale = new Locale("mi");
            langChange();
        }
    
    }
 
    //Code sampled from https://stackoverflow.com/questions/24850424/get-jmenuitems-from-jmenubar
    public static void langChange() {
        ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle",Andie.locale);
        String[][] m = {{"File","Open","Save","Save_as","Exit","Export"},
                        {"Edit","Undo","Redo"},
                        {"View","Zoom_in","Zoom_out","Zoom_full","Rotate_right","Rotate_left","Rotate_180","Flip_horizontal","Flip_vertical","Resize"},
                        {"Filter","Mean_filter","Sharpen","Median"},
                        {"Colour","Greyscale","Invert_color"},
                        {"Language","English","Spanish","Maori"}};
        String[][] descs = {{"Open_desc","Save_desc","Save_as_desc","Exit_desc","Export_desc"},
                          {"Undo_desc","Redo_desc"},
                          {"Zoom_in_desc","Zoom_out_desc","Zoom_full_desc","Rotate_right_desc","Rotate_left_desc","Rotate_180_desc","Flip_horizontal_desc","Flip_vertical_desc","Resize_desc"},
                          {"Mean_filter_desc","Sharpen_desc","Median_desc"},
                          {"Greyscale_desc","Invert_color_desc"},
                          {"English_desc","Spanish_desc","Maori_desc"}};

        for (int i = 0; i < m.length/*Andie.menuBar.getMenuCount()*/; i++) {
            JMenu menu1 = Andie.menuBar.getMenu(i);
            //System.out.println("Menu:" + menu1.getText());
            menu1.setText(b.getString(m[i][0]));
            for (int j = 0; j < m[i].length-1/*menu1.getMenuComponentCount()*/; j++) {
                java.awt.Component comp = menu1.getMenuComponent(j);
                if (comp instanceof JMenuItem) {
                    JMenuItem menuItem1 = (JMenuItem) comp;
                    //System.out.println("MenuItem:" + menuItem1.getText());
                    if (j+1 < m[i].length)  {
                        menuItem1.setText(b.getString(m[i][j+1]));    
                        menuItem1.setToolTipText(b.getString(descs[i][j]));                   
                    }
                    
                    
                }
            }
        }
        
    } 

}
