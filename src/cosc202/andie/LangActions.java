package cosc202.andie;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class LangActions {
    protected ArrayList<Action> actions;

    public LangActions() {
        actions = new ArrayList<Action>();
        //Include languages here with action.add(. . .) //see fileActions for format
    }

    public JMenu createMenu() {
        JMenu langMenu = new JMenu("Lang");
        for (Action action : actions) {
            langMenu.add(new JMenuItem(action));
        }
        return langMenu;
    }


}

public class LangChange {

    public LangChange(String lang, String country) {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        prefs.put("language",lang);
        prefs.put("country",country);
    }

}
