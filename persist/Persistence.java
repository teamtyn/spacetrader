package spacetrader.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.stage.FileChooser;
import spacetrader.GameModel;

public class Persistence {

    private static final FileChooser.ExtensionFilter EXTENSION_FILTER
            = new FileChooser.ExtensionFilter("Space Trader Save", "*.sts");
    private static final FileChooser LOAD_CHOOSER = createChooser("Load Game");
    private static final FileChooser SAVE_CHOOSER = createChooser("Save Game");
    private static final File HOME_DIRECTORY = new File(System.getProperty("user.home"));

    public static boolean loadGame() {
        File loadFile = LOAD_CHOOSER.showOpenDialog(GameModel.getStage());
        if (loadFile != null) {
            try (FileInputStream loadStream = new FileInputStream(loadFile)) {
                GameModel.load(loadStream);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean saveGame() {
        File saveFile = SAVE_CHOOSER.showSaveDialog(GameModel.getStage());
        if (saveFile != null) {
            if (!saveFile.getName().toLowerCase().endsWith(".sts")) {
                saveFile = new File(saveFile.getAbsolutePath() + ".sts");
            }
            try (FileOutputStream saveStream = new FileOutputStream(saveFile)) {
                GameModel.save(saveStream);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static FileChooser createChooser(String title) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(EXTENSION_FILTER);
        chooser.setSelectedExtensionFilter(EXTENSION_FILTER);
        chooser.setInitialDirectory(HOME_DIRECTORY);
        return chooser;
    }
}
