package spacetrader;

import java.io.FileInputStream;
import java.io.InputStream;
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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;
import spacetrader.persist.Persistence;
import spacetrader.player.Player;
import spacetrader.star_system.Planet;
import spacetrader.star_system.Planet.TechLevel;
import spacetrader.star_system.PlanetView;
import spacetrader.star_system.StarSystem;
import spacetrader.star_system.StarSystemView;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * FXML Controller class for the UniverseMap.
 *
 * @author Team TYN
 */
public class UniverseMapController extends AnimationTimer
            implements Initializable, ControlledScreen {
    /**
     * The length of time an information pane transition lasts.
     */
    private static final double INFO_DURATION = 0.1;
    /**
     * The y translate required to completely hide the info pane.
     */
    private static final int HIDDEN_INFO = 193;
    /**
     * The y translate required to show only the system portion of the info
     * pane.
     */
    private static final int SYSTEM_INFO = 128;
    /**
     * The scale factor used for the highlight.
     */
    private static final double HIGHLIGHT_SCALE = 1.2;
    /**
     * The panning speed.
     */
    private static final double PAN_SPEED = 1.5;
    /**
     * The lower bound of the camera zoom in planet level scope.
     */
    private static final double UNIVERSE_ZOOM_LOW = -1000;
    /**
     * The upper bound of the camera zoom in planet level scope.
     */
    private static final double UNIVERSE_ZOOM_HIGH = -3000;
    /**
     * The lower bound of the camera zoom in planet level scope.
     */
    private static final double SYSTEM_ZOOM_LOW = -100;
    /**
     * The upper bound of the camera zoom in planet level scope.
     */
    private static final double SYSTEM_ZOOM_HIGH = -300;
    /**
     * The lower bound of the camera zoom in planet level scope.
     */
    private static final double PLANET_ZOOM_LOW = -7;
    /**
     * The upper bound of the camera zoom in planet level scope.
     */
    private static final double PLANET_ZOOM_HIGH = -17;
    /**
     * The String used to describe Unknown planets.
     */
    private static final String UNKNOWN_STRING = "Unknown";

    /**
     * The AnchorPane that holds the entire scene.
     */
    @FXML
    private AnchorPane parent;
    /**
     * The pane that holds the three dimensional subscene.
     */
    @FXML
    private Pane subScenePane;
    /**
     * The information pane.
     */
    @FXML
    private Pane infoPane;
    /**
     * The system field in the information pane.
     */
    @FXML
    private Label systemField;
    /**
     * The distance field in the information pane.
     */
    @FXML
    private Label distanceField;
    /**
     * The fuel cost field in the information pane.
     */
    @FXML
    private Label fuelCostField;
    /**
     * The planet name field in the information pane.
     */
    @FXML
    private Label planetField;
    /**
     * The government field in the information pane.
     */
    @FXML
    private Label governmentField;
    /**
     * The tech level field in the information pane.
     */
    @FXML
    private Label techLevelField;
    /**
     * The resource level field in the information pane.
     */
    @FXML
    private Label resourceField;
    /**
     * The travel button. This button is only active when a planet is selected
     * the player has enough fuel to travel.
     */
    @FXML
    private Button travelButton;
    /**
     * The market button. This button is only active when a planet is selected
     * and the player is currently on the selected planet.
     */
    @FXML
    private Button marketButton;
    /**
     * The space station button. This button is only active when a planet is
     * selected, the player is currently on the selected planet, and the planet
     * has a high tech level.
     */
    @FXML
    private Button spaceStationButton;
    /**
     * The mercenary button.
     */
    @FXML
    private Button mercenaryButton;
    /**
     * The save button.
     */
    @FXML
    private Button saveButton;
    
     @FXML
    private Button cpuButton;

    @FXML
    private Label playerDayField;

    @FXML
    private Label playerFuelField;
    
    @FXML
    private Label playerCargoSpaceField;
    
   
        
    

    /**
     * The tentative selection.
     */
    private Sphere highlighted;
    /**
     * The selected system.
     */
    private StarSystemView selectedSystem;
    /**
     * The selected planet.
     */
    private PlanetView selectedPlanet;
    /**
     * The parent controller for this controller.
     */
    private ScreensController parentController;

    /**
     * The subscene used to render the universe.
     */
    private UniverseMapSubScene subScene;
    /**
     * The list of system views.
     */
    private ArrayList<StarSystemView> systemViews;
    /**
     * The camera used to view the universe through the subscene.
     */
    private PerspectiveCamera camera;
    /**
     * The top level camera Xform.
     */
    private Xform topXform;
    /**
     * The base level Xform.
     */
    private Xform baseXform;
    /**
     * The skybox.
     */
    private MeshView skybox;
    /**
     * The highlight used to indicate the tentative selection.
     */
    private Sphere highlight;

    /**
     * The animation that hides the information pane.
     */
    private Timeline hideInfo;
    /**
     * The animation that shows the system info.
     */
    private Timeline systemInfo;
    /**
     * The animation that shows the planet info.
     */
    private Timeline planetInfo;

    /**
     * The x position of the mouse when a mouse event is fired.
     */
    private double mousePosX;
    /**
     * The y position of the mouse when a mouse event is fired.
     */
    private double mousePosY;
    
    /**
     * The audio stream that plays our awesome music.
     */
    private static AudioStream audioStream;

    @Override
    public final void initialize(final URL url, final ResourceBundle rb) {
        infoPane.setTranslateY(HIDDEN_INFO);
        travelButton.translateYProperty().bind(infoPane.translateYProperty());

        hideInfo = new Timeline(
                new KeyFrame(Duration.seconds(INFO_DURATION),
                        new KeyValue(infoPane.translateYProperty(), HIDDEN_INFO)
                )
        );
        systemInfo = new Timeline(
                new KeyFrame(Duration.seconds(INFO_DURATION),
                        new KeyValue(infoPane.translateYProperty(), SYSTEM_INFO)
                )
        );
        planetInfo = new Timeline(
                new KeyFrame(Duration.seconds(INFO_DURATION),
                        new KeyValue(infoPane.translateYProperty(), 0)
                )
        );
        
        loadSound();


    }

    @Override
    public final void setScreenParent(
            final ScreensController aParentController) {
        parentController = aParentController;
    }

    @Override
    public final void lazyInitialize() {
        subScene = new UniverseMapSubScene(new Group(),
                SpaceTrader.SCREEN_WIDTH, SpaceTrader.SCREEN_HEIGHT);
        systemViews = subScene.getSystemViews();
        camera = (PerspectiveCamera) subScene.getCamera();
        topXform = subScene.getTopXform();
        baseXform = subScene.getBaseXform();
        skybox = subScene.getSkybox();
        highlight = subScene.getHighlight();

        subScenePane.getChildren().add(subScene);
        handleHighlight();
        handleMouse();
        start();
        playSound();
    }
    /**
     * Loads sounds.
     */
    private void loadSound() {
      try {
        String workingDir = System.getProperty("user.dir");
        String soundFile = "/src/spacetrader/Kalimba.wav";
        InputStream inputStream = new FileInputStream(workingDir + soundFile);
        audioStream = new AudioStream(inputStream);
      } catch (Exception ex) {
          System.out.println(ex);
      }
    }
    
    public static void stopSound() {
        try {
        audioStream.close();
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Plays sounds.
     */
    public static void playSound() {
      try {
        AudioPlayer.player.start(audioStream);
      } catch (Exception ex) {
          System.out.println(ex);
      }
    }

    @Override
    public final void handle(final long now) {
        if (selectedSystem != null) {
            selectedSystem.incrementOrbits();
        }

        skybox.setTranslateX(camera.getLocalToSceneTransform().getTx());
        skybox.setTranslateY(camera.getLocalToSceneTransform().getTy());
        skybox.setTranslateZ(camera.getLocalToSceneTransform().getTz());

        if (highlighted != null) {
            highlight.setTranslateX(
                    highlighted.getLocalToSceneTransform().getTx());
            highlight.setTranslateY(
                    highlighted.getLocalToSceneTransform().getTy());
            highlight.setTranslateZ(
                    highlighted.getLocalToSceneTransform().getTz());
        }
    }

    /**
     * Sets the highlighted body.
     * @param h The sphere to be highlighted.
     */
    private void setHighlighted(final Sphere h) {
        highlighted = h;
        highlight.setRadius(HIGHLIGHT_SCALE * h.getRadius());
        highlight.setVisible(true);
    }

    /**
     * Sets up the highlighting mechanic.
     */
    public final void handleHighlight() {
        EventHandler<MouseEvent> bodySelect;
        bodySelect = new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
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
            }
        };

        EventHandler<MouseEvent> bodyDeselect = new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
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
    }

    /**
     * Sets up the subscene mouse event handling.
     */
    public final void handleMouse() {
        subScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
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
                                    selectedSystem.updateTextures(
                                            PlanetView.WIDTH_LOW,
                                            PlanetView.HEIGHT_LOW);
                                    selectedSystem.setLightOn(false);
                                }
                                selectedSystem = system;
                                selectedSystem.expand();
                                selectedSystem.updateTextures(
                                        PlanetView.WIDTH_MED,
                                        PlanetView.HEIGHT_MED);
                                selectedSystem.setLightOn(true);
                                subScene.cameraToSystem(selectedSystem);
                            }
                        } else if (system == selectedSystem) {
                            selectedPlanet.updateTextures(
                                    PlanetView.WIDTH_MED,
                                    PlanetView.HEIGHT_MED, null);
                            selectedPlanet = null;
                            subScene.cameraToSystem(selectedSystem);
                        }
                    } else if (intersect instanceof PlanetView) {
                        PlanetView planet = (PlanetView) intersect;
                        if (selectedSystem != null && selectedPlanet == null) {
                            if (selectedSystem.containsPlanet(planet)) {
                                selectedPlanet = planet;
                                selectedPlanet.updateTextures(
                                        PlanetView.WIDTH_HIGH,
                                        PlanetView.HEIGHT_HIGH, null);
                                subScene.cameraToPlanet(
                                        selectedSystem, selectedPlanet);
                            }
                        }
                    }
                }
            }
        });

        subScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                double mouseOldX = mousePosX;
                double mouseOldY = mousePosY;
                mousePosX = event.getSceneX();
                mousePosY = event.getSceneY();

                if (selectedPlanet != null) {
                    baseXform.setRz((baseXform.getRz()
                            + (mousePosX - mouseOldX) / 2) % 360);
                    baseXform.setRx(Math.max(Math.min(baseXform.getRx()
                            - (mousePosY - mouseOldY) / 2, 180), 0));
                } else if (selectedSystem != null) {
                    baseXform.setRy((baseXform.getRy()
                            + (mousePosX - mouseOldX) / 2) % 360);
                    baseXform.setRx(Math.max(Math.min(baseXform.getRx()
                            - (mousePosY - mouseOldY) / 2, 90), -90));
                } else {
                    topXform.setTx(Math.max(Math.min(topXform.getTx()
                            - (mousePosX - mouseOldX) * PAN_SPEED,
                            GameModel.UNIVERSE_WIDTH), 0));
                    topXform.setTy(Math.max(Math.min(topXform.getTy()
                            - (mousePosY - mouseOldY) * PAN_SPEED,
                            GameModel.UNIVERSE_HEIGHT), 0));
                }
            }
        });

        subScene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(final ScrollEvent event) {
                if (selectedPlanet != null) {
                    camera.setTranslateZ(Math.min(camera.getTranslateZ()
                            + camera.getTranslateZ() * event.getDeltaY()
                                    / UniverseMapSubScene.UNIVERSE_ZOOM,
                            PLANET_ZOOM_LOW));
                    if (camera.getTranslateZ() < PLANET_ZOOM_HIGH) {
                        selectedPlanet.updateTextures(
                                PlanetView.WIDTH_MED,
                                PlanetView.HEIGHT_MED, null);
                        selectedPlanet = null;
                        updateSystemInfo(selectedSystem);
                        systemInfo.play();
                        subScene.cameraToSystem(selectedSystem);
                    }
                } else if (selectedSystem != null) {
                    camera.setTranslateZ(Math.min(camera.getTranslateZ()
                            + camera.getTranslateZ() * event.getDeltaY()
                                    / UniverseMapSubScene.UNIVERSE_ZOOM,
                            SYSTEM_ZOOM_LOW));
                    if (camera.getTranslateZ() < SYSTEM_ZOOM_HIGH) {
                        selectedSystem.collapse();
                        selectedSystem.updateTextures(
                                PlanetView.WIDTH_LOW,
                                PlanetView.HEIGHT_LOW);
                        selectedSystem.setLightOn(false);
                        selectedSystem = null;
                        hideInfo.play();
                        subScene.cameraToUniverse();
                    }
                } else {
                    camera.setTranslateZ(
                            Math.max(Math.min(camera.getTranslateZ()
                                    + camera.getTranslateZ() * event.getDeltaY()
                                            / UniverseMapSubScene.UNIVERSE_ZOOM,
                                    UNIVERSE_ZOOM_LOW), UNIVERSE_ZOOM_HIGH));
                }
            }
        });
    }

    /**
     * Updates the system information in the information pane.
     *
     * @param system The system described in the system information.
     */
    public final void updateSystemInfo(final StarSystemView system) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        Player player = GameModel.getPlayer();
        double distance = player.getSystem()
                .getSystemDistance(system.getSystem());
        double fuelCost = distance / player.getShip().getFuelEfficiency();

        systemField.setText(system.getSystem().getName());
        distanceField.setText(nf.format(distance) + " pc");
        fuelCostField.setText(nf.format(fuelCost) + " gallons");

        travelButton.setDisable(true);
        marketButton.setDisable(true);
        spaceStationButton.setDisable(true);
        mercenaryButton.setDisable(true);
    }

    /**
     * Updates the planet information in the information pane.
     *
     * @param planet The planet described in the planet information.
     */
    public final void updatePlanetInfo(final PlanetView planet) {
        Player player = GameModel.getPlayer();

        if (planet == selectedPlanet) {
            travelButton.setDisable(false);
        }
        if (player.getPlanet() == planet.getPlanet()) {
            travelButton.setDisable(true);
            marketButton.setDisable(false);
            mercenaryButton.setDisable(false);
            if (player.getPlanet().getTechLevel() == TechLevel.HIGHTECH) {
                spaceStationButton.setDisable(false);
            }
        } else {
            travelButton.setText("Travel");
        }

        if (player.knowsPlanet(planet.getPlanet())) {
            planetField.setText(planet.getPlanet().getName());
            governmentField.setText(planet.getPlanet().getGovernment() + " ");
            techLevelField.setText(planet.getPlanet().getTechLevel() + " ");
            resourceField.setText(planet.getPlanet().getResourceLevel() + " ");
        } else {
            planetField.setText(UNKNOWN_STRING);
            governmentField.setText(UNKNOWN_STRING);
            techLevelField.setText(UNKNOWN_STRING);
            resourceField.setText(UNKNOWN_STRING);
        }
    }

    /**
     * The event handler for the travel button.
     *
     * @param event The event fired when the button is pressed.
     */
    @FXML
    private void travelButtonAction(final ActionEvent event) {
        Player player = GameModel.getPlayer();
        StarSystem system = selectedSystem.getSystem();
        Planet planet = selectedPlanet.getPlanet();

        GameModel.setDay(GameModel.getDay() + 1);

        player.setSystem(system);
        player.setPlanet(planet);
        updateSystemInfo(selectedSystem);
        updatePlanetInfo(selectedPlanet);
    }
    
    

    /**
     * The event handler for the market button.
     *
     * @param event The event fired when the button is pressed.
     */
    @FXML
    private void marketButtonAction(final ActionEvent event) {
        parentController.setScreen("Market");
    }

    /**
     * The event handler for the space station button.
     *
     * @param event The event fired when the button is pressed.
     */
    @FXML
    private void spaceStationButtonAction(final ActionEvent event) {
        parentController.setScreen("SpaceStation");
    }

    /**
     * The event handler for the mercenary button.
     *
     * @param event The event fired when the button is pressed.
     */
    @FXML
    private void mercenaryButtonAction(final ActionEvent event) {
        parentController.setScreen("MercenaryShop");
    }

    /**
     * The event handler for the mercenary button.
     *
     * @param event The event fired when the button is pressed.
     */
    @FXML
    private void cpuButtonAction(final ActionEvent event) {
       System.out.println(parent);
       
       //System.out.println(ComputerController.openCPUView());
       subScenePane = ComputerController.openCPUView(subScenePane);
       //parent.getChildren().add();
    }
    
    /**
     * The event handler for the save button.
     *
     * @param event The event fired when the button is pressed.
     */
    @FXML
    private void saveButtonAction(final ActionEvent event) {
        Persistence.saveGame();
    }
}
