package spacetrader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import spacetrader.persist.Persistence;

/**
 * @author TYN
 * @version 1.0
 */
public class MenuController implements Initializable, ControlledScreen {

    private ScreensController parentController;

    @FXML
    private void startButtonAction(ActionEvent event) {
        parentController.setScreen("SkillSetup");
    }

    @FXML
    private void loadButtonAction(ActionEvent event) {
        if (Persistence.loadGame()) {
            parentController.setScreen("UniverseMap");
        }
    }

    @FXML
    private void exitButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void lazyInitialize() {
    }

    @Override
    public void setScreenParent(ScreensController parentController) {
        this.parentController = parentController;
    }
}
