package spacetrader;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;
import spacetrader.player.Player;
import spacetrader.star_system.Planet;
import spacetrader.star_system.PlanetView;
import spacetrader.star_system.StarSystem;
import spacetrader.star_system.StarSystemView;

/**
 * FXML Controller class
 * @author TYN
 */
public class UniverseMapController extends AnimationTimer implements Initializable, ControlledScreen {
    private Sphere highlighted;
    private StarSystemView selectedSystem;
    private PlanetView selectedPlanet;
    private ScreensController parentController;
    private UniverseView universeView;
    
    private SubScene subScene;
    private ArrayList<StarSystemView> systemViews;
    private PerspectiveCamera camera;
    private Xform topXform;
    private Xform baseXform;
    private MeshView skybox;
    private Sphere highlight;
    
    @FXML private Pane subScenePane;
    @FXML private Pane infoPane;
    @FXML private Label systemField;
    @FXML private Label distanceField;
    @FXML private Label fuelCostField;
    @FXML private Label planetField;
    @FXML private Label governmentField;
    @FXML private Label techLevelField;
    @FXML private Label environmentField;
    @FXML private Button travelButton;
    @FXML private Button marketButton;
    @FXML private Button spaceStationButton;
    
    private Timeline hideInfo;
    private Timeline systemInfo;
    private Timeline planetInfo;
    
    double mousePosX;
    double mousePosY;
    double cameraVelocityX;
    double cameraVelocityY;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        infoPane.setTranslateY(193);
        travelButton.translateYProperty().bind(infoPane.translateYProperty());
        
        hideInfo = new Timeline(
            new KeyFrame(Duration.seconds(0.1),
                new KeyValue(infoPane.translateYProperty(), 193)
            )
        );
        systemInfo = new Timeline(
            new KeyFrame(Duration.seconds(0.1),
                new KeyValue(infoPane.translateYProperty(), 128)
            )
        );
        planetInfo = new Timeline(
            new KeyFrame(Duration.seconds(0.1),
                new KeyValue(infoPane.translateYProperty(), 0)
            )
        );  
    }

    @Override
    public void lazyInitialize() {
        universeView = new UniverseView();
        subScene = universeView.getSubScene();
        systemViews = universeView.getSystemViews();
        camera = universeView.getCamera();
        topXform = universeView.getTopXform();
        baseXform = universeView.getBaseXform();
        skybox = universeView.getSkybox();
        highlight = universeView.getHighlight();
        
        subScenePane.getChildren().add(subScene);
        handleMouse();
        start();
    }

    @Override
    public void handle(long now) {
        if (selectedSystem != null) {
            selectedSystem.incrementOrbits();
        }
        
        skybox.setTranslateX(camera.getLocalToSceneTransform().getTx());
        skybox.setTranslateY(camera.getLocalToSceneTransform().getTy());
        skybox.setTranslateZ(camera.getLocalToSceneTransform().getTz());
        
        
        if (highlighted != null) {
            highlight.setTranslateX(highlighted.getLocalToSceneTransform().getTx());
            highlight.setTranslateY(highlighted.getLocalToSceneTransform().getTy());
            highlight.setTranslateZ(highlighted.getLocalToSceneTransform().getTz());
        }
    }
    
    private void setHighlighted(Sphere h) {
        highlighted = h;
        highlight.setRadius(1.2 * h.getRadius());
        highlight.setVisible(true);
    }
    
    public void handleMouse() {
        PerspectiveCamera camera = universeView.getCamera();
        
        EventHandler<MouseEvent> bodySelect;
        bodySelect = (MouseEvent event) -> {
            PickResult pickResult = event.getPickResult();
            if (pickResult != null) {
                Node intersect = pickResult.getIntersectedNode();
                if (intersect instanceof StarSystemView) {
                    StarSystemView system = (StarSystemView) intersect;
                    if (selectedPlanet == null) {
                        if (system != selectedSystem) {
                            setHighlighted(system);
                            updateSystemInfo(system);
                            systemInfo.play();
                        }
                    } else if (system == selectedSystem) {
                        setHighlighted(system);
                        updateSystemInfo(system);
                        systemInfo.play();
                    }
                } else if (intersect instanceof PlanetView) {
                    PlanetView planet = (PlanetView) intersect;
                    if (selectedSystem != null && selectedPlanet == null) {
                        if (selectedSystem.containsPlanet(planet)) {
                            setHighlighted(planet);
                            updatePlanetInfo(planet);
                            planetInfo.play();
                        }
                    }
                }
            }
        };
        
        EventHandler<MouseEvent> bodyDeselect = (MouseEvent event) -> {
            highlighted = null;
            highlight.setVisible(false);
            if (selectedPlanet == null) {
                if (selectedSystem == null) {
                    hideInfo.play();
                } else {
                    updateSystemInfo(selectedSystem);
                    systemInfo.play();
                }
            } else {
                updatePlanetInfo(selectedPlanet);
                planetInfo.play();
            }
        };
        
        for (StarSystemView systemView : systemViews) {
            systemView.setOnMouseEntered(bodySelect);
            systemView.setOnMouseExited(bodyDeselect);
            for (PlanetView planetView : systemView.getPlanetViews()) {
                planetView.setOnMouseEntered(bodySelect);
                planetView.setOnMouseExited(bodyDeselect);
            }
        }
        
        subScene.setOnMousePressed((MouseEvent event) -> {
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            
            PickResult pickResult = event.getPickResult();
            if (pickResult != null) {
                Node intersect = pickResult.getIntersectedNode();
                if (intersect instanceof StarSystemView) {
                    StarSystemView system = (StarSystemView) intersect;
                    if (selectedPlanet == null) {
                        if (system != selectedSystem) {
                            if (selectedSystem != null) {
                                selectedSystem.collapse();
                                selectedSystem.updateTextures(100, 50);
                                selectedSystem.setLightOn(false);
                            }
                            selectedSystem = system;
                            selectedSystem.expand();
                            selectedSystem.updateTextures(1000, 500);
                            selectedSystem.setLightOn(true);
                            universeView.cameraToSystem(selectedSystem);
                        }
                    } else if (system == selectedSystem) {
                        selectedPlanet.updateTextures(1000, 500, null);
                        selectedPlanet = null;
                        universeView.cameraToSystem(selectedSystem);
                    }
                } else if (intersect instanceof PlanetView) {
                    PlanetView planet = (PlanetView) intersect;
                    if (selectedSystem != null && selectedPlanet == null) {
                        if (selectedSystem.containsPlanet(planet)) {
                            selectedPlanet = planet;
                            selectedPlanet.updateTextures(2000, 1000, null);
                            universeView.cameraToPlanet(selectedSystem, selectedPlanet);
                        }
                    }
                }
            }
        });
        
        subScene.setOnMouseDragged((MouseEvent event) -> {
            double mouseOldX = mousePosX;
            double mouseOldY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            
            if (selectedPlanet != null) {
                baseXform.setRz((baseXform.rz.getAngle() + (mousePosX - mouseOldX) / 2) % 360);
                baseXform.setRx(Math.max(Math.min(baseXform.rx.getAngle() - (mousePosY - mouseOldY) / 2, 180), 0));
            } else if (selectedSystem != null) {
                baseXform.setRy((baseXform.ry.getAngle() + (mousePosX - mouseOldX) / 2) % 360);
                baseXform.setRx(Math.max(Math.min(baseXform.rx.getAngle() - (mousePosY - mouseOldY)/2, 90), -90));
            } else {
                topXform.setTx(Math.max(Math.min(topXform.t.getX() - (mousePosX - mouseOldX) * 1.5, GameModel.UNIVERSE_WIDTH), 0));
                topXform.setTy(Math.max(Math.min(topXform.t.getY() - (mousePosY - mouseOldY) * 1.5, GameModel.UNIVERSE_HEIGHT), 0));
            }
        });
        
        subScene.setOnScroll((ScrollEvent event) -> {
            if (selectedPlanet != null) {
                camera.setTranslateZ(Math.min(camera.getTranslateZ() - camera.getTranslateZ() * event.getDeltaY() / 2000, -7));
                if (camera.getTranslateZ() < -17) {
                    selectedPlanet.updateTextures(1000, 500, null);
                    selectedPlanet = null;
                    updateSystemInfo(selectedSystem);
                    systemInfo.play();
                    universeView.cameraToSystem(selectedSystem);
                }
            } else if (selectedSystem != null) {
                camera.setTranslateZ(Math.min(camera.getTranslateZ() - camera.getTranslateZ() * event.getDeltaY() / 2000, -100));
                if (camera.getTranslateZ() < -300) {
                    selectedSystem.collapse();
                    selectedSystem.updateTextures(100, 50);
                    selectedSystem.setLightOn(false);
                    selectedSystem = null;
                    hideInfo.play();
                    universeView.cameraToUniverse();
                }
            } else {
                camera.setTranslateZ(Math.max(Math.min(camera.getTranslateZ() - camera.getTranslateZ() * event.getDeltaY() / 2000, -1000), -3000));
            }
        });
    }
    
    public void updateSystemInfo(StarSystemView system) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        
        Player player = GameModel.getPlayer();
        double distance = player.getSystem().getSystemDistance(system.getSystem());
        double fuelCost = distance / player.getShip().getFuelEfficiency();
        
        systemField.setText(system.getSystem().getName());
        distanceField.setText(nf.format(distance) + " pc");
        fuelCostField.setText(nf.format(fuelCost) + " gallons");
        
        travelButton.setDisable(true);
        marketButton.setDisable(true);
        spaceStationButton.setDisable(true);
    }
    
    public void updatePlanetInfo(PlanetView planet) {
        Player player = GameModel.getPlayer();
        
        if (planet == selectedPlanet) {
            travelButton.setDisable(false);
        }
        if (player.getPlanet() == planet.getPlanet()) {
            travelButton.setText("To Surface");
            marketButton.setDisable(false);
            spaceStationButton.setDisable(false);
        } else {
            travelButton.setText("Travel");
        }
        
        if (player.knowsPlanet(planet.getPlanet())) {
            planetField.setText(planet.getPlanet().getName());
            governmentField.setText("" + planet.getPlanet().getGovernment());
            techLevelField.setText("" + planet.getPlanet().getTechLevel());
            environmentField.setText("" + planet.getPlanet().getResourceLevel());
        } else {
            planetField.setText("Unknown");
            governmentField.setText("Unknown");
            techLevelField.setText("Unknown");
            environmentField.setText("Unknown");
        }    
    }

    @FXML
    private void travelButtonAction(ActionEvent event) {
        Player player = GameModel.getPlayer();
        StarSystem system = selectedSystem.getSystem();
        Planet planet = selectedPlanet.getPlanet();
        
        GameModel.setDay(GameModel.getDay() + 1);

        player.setSystem(system);
        player.setPlanet(planet);
        updateSystemInfo(selectedSystem);
        updatePlanetInfo(selectedPlanet);
        // TODO: Remove fuel, potentially disable button
    }

    @FXML
    private void marketButtonAction(ActionEvent event) {
        if (ScreensController.isInitialized("Market")) {
            ((MarketController)ScreensController.getController("Market")).display();
        }
        parentController.setScreen("Market");
    }

    @FXML
    private void spaceStationButtonAction(ActionEvent event) {
        parentController.setScreen("SpaceStation");
    }
    
    @Override
    public void setScreenParent(ScreensController parentController) {
        this.parentController = parentController;
    }
}
