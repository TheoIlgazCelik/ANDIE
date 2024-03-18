package cosc202.andie;
import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;


public class LangActions {
    protected ArrayList<Action> actions;

    public LangActions() {
        actions = new ArrayList<Action>();
        //Include languages here with action.add(. . .) //see fileActions for format
        actions.add(new LangEnglishAction("English",null,"Change language to english",null));
        actions.add(new LangSpanishAction("Spanish",null,"Change language to spanish",null));
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
            //Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            //prefs.put("language","es");
            //prefs.put("country","ES");
            Andie.locale = new Locale("es");
            ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle",Andie.locale);
            System.out.println(b.getString("Language"));
            //Andie.menuBar.getAccessibleContext().setAccessibleName("k");
            
            langChange();
        }
    
    }
    public class LangEnglishAction extends ImageAction {

        public LangEnglishAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            //Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            //Andie.prefs.put("language","en");
            //Andie.prefs.put("country","NZ");
            Andie.locale = new Locale("en");
            ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle",Andie.locale);
            System.out.println(b.getString("Language"));
            //for (Component c : Andie.menuBar.getComponents()) {
            //    setText("k");
            //}
            langChange();
        }
    
    }

    //Code sampled from https://stackoverflow.com/questions/24850424/get-jmenuitems-from-jmenubar
    public static void langChange() {
        ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle",Andie.locale);
        for (int i = 0; i < Andie.menuBar.getMenuCount(); i++) {
            JMenu menu1 = Andie.menuBar.getMenu(i);
            //System.out.println("Menu:" + menu1.getText());
            menu1.setText(b.getString(/**/));
            for (int j = 0; j < menu1.getMenuComponentCount(); j++) {
                java.awt.Component comp = menu1.getMenuComponent(j);
                if (comp instanceof JMenuItem) {
                    JMenuItem menuItem1 = (JMenuItem) comp;
                    //System.out.println("MenuItem:" + menuItem1.getText());
                    menuItem1.setText(b.getString(/* */));
                }
            }
        }
        //need to create way of getting string names for all items regardless of language
    }

}
