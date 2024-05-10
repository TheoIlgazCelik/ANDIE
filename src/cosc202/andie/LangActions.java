package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Language menu.
 * </p>
 * 
 * <p>
 * The Language menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * These include the languages that the application supports.
 * </p>
 * 
 *
 * 
 * @author Aiden O'Brien
 * @version 1.0
 */
public class LangActions {
    /** A list of actions for the language menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Lang menu actions.
     * </p>
     */
    public LangActions() {
        actions = new ArrayList<Action>();
        // Include languages here with action.add(. . .) //see fileActions for format
        actions.add(new LangEnglishAction("English", null, "Change language to english", null));
        actions.add(new LangSpanishAction("Spanish", null, "Change language to spanish", null));
        actions.add(new LangMaoriAction("Maori", null, "Change language to maori", null));
    }

    /**
     * <p>
     * Create a menu containing the list of language actions.
     * </p>
     * 
     * @return The Language menu UI element.
     */
    public JMenu createMenu() {
        ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle", Andie.locale);
        JMenu langMenu = new JMenu(b.getString("Language"));
        for (Action action : actions) {
            langMenu.add(new JMenuItem(action));
        }
        return langMenu;
    }

    /**
     * <p>
     * Action to change the language to spanish
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class LangSpanishAction extends ImageAction {
        /**
         * <p>
         * Change the language to Spanish.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        public LangSpanishAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the language change to Spanish action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the LangSpanishAction is triggered.
         * It changes the language of the UI.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Andie.locale = new Locale("es");
            langChange();
        }

    }

    /**
     * <p>
     * Action to change the language to English
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class LangEnglishAction extends ImageAction {
        /**
         * <p>
         * Change the language to English.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        public LangEnglishAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the language change to English action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the LangEnglishAction is triggered.
         * It changes the language of the UI.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Andie.locale = new Locale("en");
            langChange();
        }

    }

    public class LangMaoriAction extends ImageAction {
        /**
         * <p>
         * Change the language to Maori.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        public LangMaoriAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the language change to Maori action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the LangMaoriAction is triggered.
         * It changes the language of the UI.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Andie.locale = new Locale("mi");
            langChange();
        }

    }

    /**
     * Code sampled from
     * https://stackoverflow.com/questions/24850424/get-jmenuitems-from-jmenubar
     * <p>
     * Method that iterates through the UI and changes all text to the language
     * chosen.
     * </p>
     */
    public static void langChange() {
        ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle", Andie.locale);
        String[][] m = { { "File", "Open", "Save", "Save_as", "Exit", "Export" },
                { "Edit", "Undo", "Redo" },
                { "View", "Zoom_in", "Zoom_out", "Zoom_full", "Rotate_right", "Rotate_left", "Rotate_180","Flip_horizontal", "Flip_vertical", "Resize", "Crop_image" },
                { "Filter", "Mean_filter", "Sharpen", "Median", "Gaussian_blur","Block_average","Random_scattering","Vertical_sobel","Horizontal_sobel","Emboss_filter"},
                { "Colour", "Greyscale", "Invert_color","Colour_cycle", "Brightness_contrast"},
                {"Macro","Macro_start","Macro_stop","Macro_open"},
                { "Language", "English", "Spanish", "Maori" },
                {"Drawing", "Draw_option","Draw"}
            };
        String[][] descs = { { "Open_desc", "Save_desc", "Save_as_desc", "Exit_desc", "Export_desc" },
                { "Undo_desc", "Redo_desc" },
                { "Zoom_in_desc", "Zoom_out_desc", "Zoom_full_desc", "Rotate_right_desc", "Rotate_left_desc","Rotate_180_desc", "Flip_horizontal_desc", "Flip_vertical_desc", "Resize_desc", "Crop_image" },
                { "Mean_filter_desc", "Sharpen_desc", "Median_desc", "Gaussian_blur_desc","Block_average_desc","Random_scattering_desc","Vertical_sobel_desc","Horizontal_sobel_desc", "Emboss_apply_desc" },
                { "Greyscale_desc", "Invert_color_desc","Colour_cycle_desc","Brightness_contrast_desc" },
                {"Macro_start_desc","Macro_stop_desc","Macro_open_desc"},
                { "English_desc", "Spanish_desc", "Maori_desc" },
                {"Draw_option_desc","Draw_desc"}
            }; 
        String[] embossTime = {"East","North_east","North","North_west","West","South_west","South","South_east"};

        for (int i = 0; i < m.length/* Andie.menuBar.getMenuCount() */; i++) {
            JMenu menu1 = Andie.menuBar.getMenu(i);
            // System.out.println("Menu:" + menu1.getText());
            menu1.setText(b.getString(m[i][0]));
            for (int j = 0; j < m[i].length - 1/* menu1.getMenuComponentCount() */; j++) {
                java.awt.Component comp = menu1.getMenuComponent(j);
                if (comp instanceof JMenuItem) {
                    JMenuItem menuItem1 = (JMenuItem) comp;
                    // System.out.println("MenuItem:" + menuItem1.getText());
                    if (j + 1 < m[i].length) {
                        menuItem1.setText(b.getString(m[i][j + 1]));
                        menuItem1.setToolTipText(b.getString(descs[i][j]));
                        if (m[i][j+1].equals("Emboss_filter")) {
                            MenuElement[] sub = menuItem1.getSubElements();
                            JPopupMenu f = (JPopupMenu)sub[0];
                            for (int k=0; k<embossTime.length; k++) {
                               JMenuItem g = (JMenuItem)f.getComponent(k);
                               g.setText(b.getString(embossTime[k]));
                               g.setToolTipText(b.getString("Emboss_apply_desc") + " " + b.getString(embossTime[k]));
                            }
                        }
                    }

                }
            }
        }
        String[] tools = {"Save","Export","Open_desc","Undo","Redo"};
        
        for (int i=0; i<tools.length; i++) {
            JComponent j = (JComponent)Andie.toolBar.getComponentAtIndex(i);
            if (tools[i] == "Undo") {
                j.setToolTipText(b.getString(tools[i]) + " | Ctrl + U");
            } else if (tools[i] == "Redo") {
                j.setToolTipText(b.getString(tools[i]) + " | Ctrl + R");
            } else if (i < 3) {
                j.setToolTipText(b.getString(tools[i]) + " | Ctrl + " + tools[i].charAt(0));
            }
        }
 
        UIManager.put("FileChooser.openButtonText", b.getString("Open"));
        UIManager.put("FileChooser.cancelButtonText", b.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", b.getString("Abort_file"));
        UIManager.put("FileChooser.openButtonToolTipText", b.getString("Open_desc"));
        UIManager.put("FileChooser.fileNameLabelText", b.getString("File_name"));
        UIManager.put("FileChooser.filesOfTypeLabelText", b.getString("Type_file"));
        UIManager.put("FileChooser.setDialogTitle", b.getString("Open"));
        UIManager.put("FileChooser.lookInLabelText", b.getString("Look"));
        UIManager.put("FileChooser.saveInLabelText", b.getString("Save_in"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", b.getString("Details_desc"));
        UIManager.put("FileChooser.listViewButtonToolTipText", b.getString("List_desc"));
        UIManager.put("FileChooser.upFolderToolTipText", b.getString("Up_level"));
        UIManager.put("FileChooser.homeFolderToolTipText", b.getString("Home"));
        UIManager.put("FileChooser.newFolderToolTipText", b.getString("Create_folder"));

    }

}
