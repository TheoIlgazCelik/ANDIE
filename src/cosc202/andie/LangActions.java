package cosc202.andie;
import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import javax.swing.*;

public class LangActions {
    protected ArrayList<Action> actions;

    public LangActions() {
        actions = new ArrayList<Action>();
        //Include languages here with action.add(. . .) //see fileActions for format
        actions.add(new LangEnglishAction("English",null,"Change language to english",null));
        actions.add(new LangSpanishAction("Spanish",null,"Change language to spanish",null));
    }

    public JMenu createMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");
        JMenu langMenu = new JMenu(bundle.getString("Language"));
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
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            prefs.put("language","es");
            prefs.put("country","ES");
        }
    
    }
    public class LangEnglishAction extends ImageAction {

        public LangEnglishAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            Locale.setDefault(new Locale(prefs.get("language","en")));
        }
    
    }

}
