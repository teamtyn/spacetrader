package spacetrader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import spacetrader.persist.Persistence;

/**
 * The MenuController class that controls our menu view.
 * @author Team TYN
 */
public class MenuController implements Initializable, ControlledScreen {
    /**
     * The parent controller needed by the ControlledScreen.
     */
    private ScreensController parentController;

    /**
     * The event used by the start button.
     * @param event the button ActionEvent
     */
    @FXML
    private void startButtonAction(final ActionEvent event) {
        parentController.setScreen("SkillSetup");
    }

    /**
     * The event used by the load button.
     * @param event the button ActionEvent
     */
    @FXML
    private void loadButtonAction(final ActionEvent event) {
        if (Persistence.loadGame()) {
            parentController.setScreen("UniverseMap");
        }
    }

    /**
     * The event used by the exit button.
     * @param event the button ActionEvent
     */
    @FXML
    private void exitButtonAction(final ActionEvent event) {
        System.exit(0);
    }

    /**
     * The overridden method needed by controllers.
     * @param url the url to the view
     * @param rBundle the resource bundle
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rBundle) {
        // TODO
    }

    /**
     * The overridden method to initialize after the first initialize.
     */
    @Override
    public void lazyInitialize() {

    }

    /**
     * The overridden method to set the parent for the controller.
     * @param aParentController the parent controller
     */
    @Override
    public final void setScreenParent(
            final ScreensController aParentController) {
        parentController = aParentController;
    }
}
