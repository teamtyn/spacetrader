package spacetrader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import spacetrader.player.AbstractCrewMember;
import spacetrader.player.Mercenary;
import spacetrader.player.Player;
import spacetrader.player.Skill;

public class MercenaryShopController implements Initializable, ControlledScreen {

    @FXML
    private Label crewSkillLabel;
    
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
    private VBox detailList;
    
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
        setup();
        updateMercList();
        updateCrewList();
    }
    
    private void setup() {
        detailList = new VBox();
        mercDetailsPane.getChildren().add(detailList);
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
        mercList.getChildren().clear();
        MercList ml = new MercList(mercs);
        ml.addHireButtons();
        mercList.getChildren().add(ml.vBox);
    }
    
    private void updateCrewList() {
        System.out.println("updating crew list!");
        currentCrewList.getChildren().clear();
        AbstractCrewMember[] acme = player.getShip().getCrew();
        ArrayList<Mercenary> crewMercs = new ArrayList<>();
        for(int i = 0; i < acme.length; i++) {
            if(acme[i] instanceof Mercenary)
                crewMercs.add((Mercenary) acme[i]);
        }
        MercList cl = new MercList(crewMercs);
        currentCrewList.getChildren().add(cl.vBox);
        player.getShip().generateSkills();
        crewSkillLabel.setText(player.getShip().listShipSkillValues());
    }
    
    private void hireMercenary(Mercenary merc) {
        if(player.getSkill("charming").getValue() > 9) {

            if(!player.getShip().addCrewMember(merc)) {
                shipDialogueField.setText("You can't jam any more crew members in there!");
            } else {
                mercs.remove(merc);
                shipDialogueField.setText("Welcome to the squad " + merc.getName());

            }

            updateMercList();
            updateCrewList();
        } else { shipDialogueField.setText("You aren't charming enough to hire mercenaries!"); }
    }
    
    @FXML
    void viewPlayerCardButtonAction(ActionEvent event) {

    }

    @FXML
    void backButtonAction(ActionEvent event) {
        parentController.setScreen("UniverseMap");
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
                if(merc != null) {
                    MercenaryShopController.MercsRow row = new MercenaryShopController.MercsRow(merc);
                
                    this.addChild(row);
                }
            }
        }
        public void addChild(Node node) {
            vBox.getChildren().add(node);
        }
        public void addHireButtons() {
            //Node node = this.vBox.getChildren().get(0);
            for (Node child: this.vBox.getChildren()) {
                MercenaryShopController.MercsRow row = (MercenaryShopController.MercsRow) child;
                row.getChildren().add(createHireButton(row.getMerc()));
            }

        }
    }
    
    /**
     * MercsRow is a HBox that contains two labels and a buy/sell button
     */
    private class MercsRow extends HBox {
        private Mercenary merc;
        public MercsRow(Mercenary aMerc) {
            merc = aMerc;
            this.getChildren().add(new Label(merc.getName()));
            Label specialty;
            Button button;

                specialty = new Label(merc.getSpecialty());
            this.getChildren().add(specialty);
            this.setOnMouseEntered((MouseEvent event) -> {
                detailList.getChildren().clear();
                detailList.getChildren().add(new Label(merc.getName().toString()));
                for(Skill skill: merc.getSkills().values()) {
                    detailList.getChildren().add(new Label(skill.toString()));
                }
            });
            this.setId("mercs-row");
            this.setAlignment(Pos.CENTER_RIGHT);
            this.setSpacing(30);
        }
        
        private Mercenary getMerc() {
            return merc;
        }
    }
    
    private Button createHireButton(Mercenary merc) {
        Button button = new Button("Hire");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hireMercenary(merc);
            }
        });
        return button;
    }
}