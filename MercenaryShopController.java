package spacetrader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import spacetrader.player.Mercenary;
import spacetrader.player.Player;

public class MercenaryShopController implements Initializable, ControlledScreen {

    @FXML
    private Label dialogueField;

    @FXML
    private VBox mercList;

    @FXML
    private Button viewPlayerCardButton;

    @FXML
    private Button backButton;

    @FXML
    private Label moneyLabel;

    @FXML
    private Pane mercDetailsPane;

    @FXML
    private VBox currentCrewList;

    @FXML
    private Label shipDialogueField;
    
    private FadeTransition ft;
    private Player player;
    private ScreensController parentController;
    private List<Mercenary> mercs;
    
    @Override
    public void setScreenParent(ScreensController aParentController) {
        parentController = aParentController;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ft = new FadeTransition(Duration.millis(1000), dialogueField);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setAutoReverse(true);
        ft.setCycleCount(2);
    }
    
    @Override
    public void lazyInitialize() {
        player = GameModel.getPlayer();
        display();
    }
    
    private void display() {
        mercs = generateMercs();
        updateMercList();
        updateCrewList();
    }
    
    private ArrayList<Mercenary> generateMercs() {
        ArrayList<Mercenary> arr = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Mercenary merc = new Mercenary(player.getPlanet().getName());
            arr.add(merc);
        }
        return arr;
    }
    
    private void updateMercList() {
        MercList ml = new MercList(mercs);
        mercList.getChildren().add(ml.vBox);
    }
    
    private void updateCrewList() {
        
    }
    
    @FXML
    void viewPlayerCardButtonAction(ActionEvent event) {

    }

    @FXML
    void backButtonAction(ActionEvent event) {

    }
    
    /**
     * MercsList is a VBox made up of HBox MercsRow's.
     */
    private class MercList {

        private final VBox vBox;
        private final List<Mercenary> array;
        public MercList(List<Mercenary> mercs) {
            vBox = new VBox();
            array = mercs;
            listMercs(array);
        }

        
        public final void listMercs(List<Mercenary> mercs) {
            for (Mercenary merc : mercs) {
                MercenaryShopController.MercsRow row = new MercenaryShopController.MercsRow(merc);
                this.addChild(row);
            }
        }

        public void addChild(Node node) {
            vBox.getChildren().add(node);
        }
    }
    
    /**
     * MercsRow is a HBox that contains two labels and a buy/sell button
     */
    private class MercsRow extends HBox {

        public MercsRow(Mercenary merc) {
            this.getChildren().add(new Label(merc.getName()));
            Label specialty;
            Button button;

                specialty = new Label("x" + merc.getSpecialty());
                button = new Button("Hire");
            this.getChildren().add(specialty);
            this.getChildren().add(button);
            this.setOnMouseEntered((MouseEvent event) -> {
                mercDetailsPane.getChildren().add(new Label(merc.getSkills().toString()));
            });
            this.setId("mercs-row");
            this.setAlignment(Pos.CENTER_RIGHT);
            this.setSpacing(30);
        }
    }
    


}