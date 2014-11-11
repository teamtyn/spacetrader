package spacetrader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import spacetrader.persist.Persistence;

/**
 * 
 * @author Team TYN
 */
public class MenuController implements Initializable, ControlledScreen {
    /**
     * 
     */
    private ScreensController parentController;

    @FXML
    private void startButtonAction(final ActionEvent event) {
        parentController.setScreen("SkillSetup");
    }

    @FXML
    private void loadButtonAction(final ActionEvent event) {
        if (Persistence.loadGame()) {
            parentController.setScreen("UniverseMap");
        }
    }

    @FXML
    private void exitButtonAction(final ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(final URL url, final ResourceBundle rBundle) {
        // TODO
    }

    @Override
    public void lazyInitialize() {
        // TODO
    }

    @Override
    public void setScreenParent(final ScreensController aParentController) {
        parentController = aParentController;
    }
}
