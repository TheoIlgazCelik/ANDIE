package cosc202.andie;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import cosc202.andie.EditActions.RedoAction;
import cosc202.andie.EditActions.UndoAction;
import cosc202.andie.FileActions.FileExportAction;
import cosc202.andie.FileActions.FileOpenAction;
import cosc202.andie.FileActions.FileSaveAction;

import javax.imageio.*;
import java.util.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program. h
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class Andie {
    /** File path for main ANDIE icon. */
    private static final String iconFilePath = "icon.png";

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggered via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * 
     * 
     */
    public static JMenuBar menuBar;
    public static JToolBar toolBar;

    private static void createAndShowGUI() {
        
        
        
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");
        try {
            Image image = ImageIO.read(Andie.class.getClassLoader().getResource(iconFilePath));
            frame.setIconImage(image);
        } catch (Exception e) {
            System.out.println("Failed to load: " + iconFilePath);
            System.out.println(e);
        }

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // prompt user to save any changes before exiting application
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ResourceBundle b = ResourceBundle.getBundle("cosc202.andie.LanguageBundle", Andie.locale);
                Object[] options2 = { b.getString("Ok"), b.getString("Cancel") };

                int option = JOptionPane.showOptionDialog(null, b.getString("close_dialog"),
                        null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, null);

                // 0 = OK
                if (option == 0) {
                    System.exit(0);
                }
            }
        });

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Add in menus for various types of action the user may perform.
        menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local window
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        // Actions that affect the macro recording
        MacroActions macroActions = new MacroActions();
        menuBar.add(macroActions.createMenu());

        LangActions langActions = new LangActions();
        menuBar.add(langActions.createMenu());

        DrawingActions drawActions = new DrawingActions();
        menuBar.add(drawActions.createMenu());

        // Adding a save icon and button
        ImageIcon saveIcon = new ImageIcon("src/SaveIcon.png");

        JButton saveButton = new JButton();
        FileSaveAction saveAction = fileActions.new FileSaveAction(null, new ImageIcon(saveIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)), "Save | Ctrl + S", Integer.valueOf(KeyEvent.VK_S));
        saveButton.setAction(saveAction);
        
        toolBar = new JToolBar();
        saveButton.setPreferredSize(new Dimension(40,40));
        toolBar.add(saveButton);

        // Adding a export icon and button
        ImageIcon exportIcon = new ImageIcon("src/ExportIcon.png");

        JButton exportButton = new JButton();
        FileExportAction exportAction = fileActions.new FileExportAction(null, new ImageIcon(exportIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)), "Export | Ctrl + E", Integer.valueOf(0));
        exportButton.setAction(exportAction);

        exportButton.setPreferredSize(new Dimension(40,40));
        toolBar.add(exportButton);

        // Adding a open icon and button
        ImageIcon openIcon = new ImageIcon("src/OpenIcon.png");

        JButton openButton = new JButton();
        FileOpenAction openAction = fileActions.new FileOpenAction(null, new ImageIcon(openIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)), "Open File | Ctrl + O", Integer.valueOf(KeyEvent.VK_O));
        openButton.setAction(openAction);

        openButton.setPreferredSize(new Dimension(40,40));
        toolBar.add(openButton);

        // Adding a undo icon and button
        ImageIcon undoIcon = new ImageIcon("src/UndoIcon.png");

        JButton undoButton = new JButton();
        UndoAction undoAction = editActions.new UndoAction(null, new ImageIcon(undoIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)), "Undo Action | Ctrl + Z", Integer.valueOf(KeyEvent.VK_Z));
        undoButton.setAction(undoAction);

        undoButton.setPreferredSize(new Dimension(40,40));
        toolBar.add(undoButton);

        // Adding a redo icon and button
        ImageIcon redoIcon = new ImageIcon("src/redoIcon.png");

        JButton redoButton = new JButton(new ImageIcon(redoIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        RedoAction redoAction = editActions.new RedoAction(null, new ImageIcon(redoIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)), "Redo Action | Ctrl + Y", Integer.valueOf(KeyEvent.VK_Y));
        redoButton.setAction(redoAction);

        redoButton.setPreferredSize(new Dimension(40,40));
        toolBar.add(redoButton);




        frame.add(toolBar, BorderLayout.NORTH);

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     *
     * 
     * @see #createAndShowGUI()
     */
    // public static Preferences prefs =
    // Preferences.userNodeForPackage(Andie.class);
    public static Locale locale = new Locale("en");

    public static void main(String[] args) throws Exception {
        // Preferences prefs = Preferences.userNodeForPackage(Andie.class);
        // Locale.setDefault(new
        // Locale(prefs.get("language","en"),prefs.get("country","NZ")));
        // Locale.setDefault(new Locale("en","NZ"));
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }

}
