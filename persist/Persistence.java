package spacetrader.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.stage.FileChooser;
import spacetrader.GameModel;

/**
 * Handles saving and loading of the game.
 *
 * @author Team TYN
 */
public final class Persistence {

    /**
     * The file extension to use for all saved games.
     */
    private static final String EXTENSION = ".sts";

    /**
     * An extension filter that only shows our saved game files.
     */
    private static final FileChooser.ExtensionFilter EXTENSION_FILTER
            = new FileChooser.ExtensionFilter("Space Trader Save", EXTENSION);
    /**
     * The file chooser used when loading a save game.
     */
    private static final FileChooser LOAD_CHOOSER = createChooser("Load Game");
    /**
     * The file chooser used when saving a game.
     */
    private static final FileChooser SAVE_CHOOSER = createChooser("Save Game");
    /**
     * When possible, use the user's home directory as a starting point.
     */
    private static final File HOME_DIRECTORY
            = new File(System.getProperty("user.home"));

    /**
     * Prevents instantiation.
     */
    private Persistence() {
    }

    /**
     * Shows a dialog which gives the user the option of loading a saved game.
     *
     * @return true if a saved game was loaded successfully
     */
    public static boolean loadGame() {
        File loadFile = LOAD_CHOOSER.showOpenDialog(GameModel.getStage());
        boolean success = false;
        if (loadFile != null) {
            try (FileInputStream loadStream = new FileInputStream(loadFile)) {
                GameModel.load(loadStream);
                success = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * Shows a dialog which gives the user the option of saving a game.
     *
     * @return true if the game was saved successfully
     */
    public static boolean saveGame() {
        File saveFile = SAVE_CHOOSER.showSaveDialog(GameModel.getStage());
        boolean success = false;
        if (saveFile != null) {
            if (!saveFile.getName().toLowerCase().endsWith(EXTENSION)) {
                saveFile = new File(saveFile.getAbsolutePath() + EXTENSION);
            }
            try (FileOutputStream saveStream = new FileOutputStream(saveFile)) {
                GameModel.save(saveStream);
                success = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * Initializes a file chooser for saving or loading.
     *
     * @param title the text shown in the title bar
     * @return a file chooser for opening or saving Space Trader save files
     */
    private static FileChooser createChooser(final String title) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(EXTENSION_FILTER);
        chooser.setSelectedExtensionFilter(EXTENSION_FILTER);
        chooser.setInitialDirectory(HOME_DIRECTORY);
        return chooser;
    }
}
