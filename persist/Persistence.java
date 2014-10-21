package spacetrader.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.stage.FileChooser;
import spacetrader.GameModel;

public class Persistence {
    private static final FileChooser loadChooser = createChooser("Load Game");
    private static final FileChooser saveChooser = createChooser("Save Game");
    private static final FileChooser.ExtensionFilter extensionFilter =
            new FileChooser.ExtensionFilter("Space Trader Save", "sts");
    private static final File homeDirectory = new File(System.getProperty("user.home"));

    public static void loadGame() {
        File loadFile = loadChooser.showOpenDialog(null);
        if (loadFile != null) {
            try (FileInputStream loadStream = new FileInputStream(loadFile)) {
                GameModel.load(loadStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveGame() {
        File saveFile = saveChooser.showSaveDialog(null);
        if (saveFile != null) {
            try (FileOutputStream saveStream = new FileOutputStream(saveFile)) {
                GameModel.save(saveStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static FileChooser createChooser(String title) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(extensionFilter);
        chooser.setSelectedExtensionFilter(extensionFilter);
        chooser.setInitialDirectory(homeDirectory);
        return chooser;
    }
}
