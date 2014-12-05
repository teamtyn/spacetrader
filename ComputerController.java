/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import com.gtranslate.Audio;
import com.gtranslate.Language;
import java.io.InputStream;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import spacetrader.persist.Persistence;
import spacetrader.voice.VoiceRecognizer;
/**
 *
 * @author Local Clayton
 */
public class ComputerController {
    private static Pane currParent;
    private static String name = GameModel.getPlayer().getName();
    private static String introduction = "Hello Captain " + name + ", I am COMPUTER. It is really nice to meet you.";
    private static boolean newb = true;
    private static String response = "Yes, Captain " + name + "?";
    private static StackPane stackPane;
    private static Button voiceButton;
    public static Pane openCPUView(Pane parent) {
        currParent = parent;
        System.out.println(parent);
        modifyParent(parent);
        StackPane view = setUpView();
        stackPane = view;
//        Stage stage = new Stage(StageStyle.UNDECORATED);
//            stage.setTitle("COMPUTER");
//            stage.setScene(new Scene(view, 450, 450));
//            stage.show();
            
            
        parent.getChildren().add(view);
        sayString(getQuote());
        Audio audio = Audio.getInstance();
        try{
        
        //This is really quite fun. VIETNAMESE is a good one.
        InputStream sound;

            sound  = audio.getAudio(getQuote(), Language.ENGLISH);
            newb = false;

        Task speak = new Task() {
            
            @Override protected Object call() throws Exception {
                audio.play(sound);
                return new Object();
            }
        };
        new Thread(speak).start();
        
        //audio.play(sound);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return parent;
    }
    
    public static void sayString(String string) {
        Audio audio = Audio.getInstance();
        try{
            InputStream sound;
            sound  = audio.getAudio(string, Language.ENGLISH);
            Task speak = new Task() {

                @Override protected Object call() throws Exception {
                        audio.play(sound);
                        return new Object();
                }
            };
        new Thread(speak).start();
        } catch (Exception ex) {
                System.out.println(ex);
        }
        
    }
    
    public static void closeCPUView(StackPane stack) {
        currParent.getChildren().remove(stack);
    }
    
    private static StackPane setUpView() {
        StackPane stack = createStackPane();
        Rectangle rect = createRectangle();
        VBox box = setUpBox(stack);
        stack.getChildren().setAll(rect, box);
        return stack;
    }
    
    private static StackPane createStackPane() {
        StackPane stack = new StackPane();
        stack.minHeight((currParent.getLayoutBounds().getHeight()));
        stack.minWidth((currParent.getLayoutBounds().getWidth()));
        stack.maxHeight(Double.MAX_VALUE);
        stack.maxWidth(Double.MAX_VALUE);
        //stack.setLayoutX((currParent.getLayoutBounds().getWidth() / 4));
        //stack.setLayoutY((currParent.getLayoutBounds().getHeight() / 4));
        //stack.setStyle("-fx-background-color: rgb(0,3,80)");

        return stack;
    }

    
    private static Rectangle createRectangle() {
        Rectangle box = new Rectangle((currParent.getLayoutBounds().getWidth()),
                (currParent.getLayoutBounds().getHeight()));
        box.maxHeight(Double.MAX_VALUE);
        box.maxWidth(Double.MAX_VALUE);
        //box.setLayoutX((currParent.getLayoutBounds().getWidth()) - (box.getWidth() / 2));
        //box.setLayoutY((currParent.getLayoutBounds().getHeight()) - (box.getHeight() / 2));
        //box.setStyle("-fx-background-color: rgb(50,3,80)");
        box.setStyle("-fx-border-color: rgb(100,100,100)");
        box.setStyle("-fx-opacity: 0.95");
        //box.setId("cpu");
//        box.styleProperty().bind(Bindings
//            .concat("-fx-background-radius:200;"));
        
        return box;
    }
    
    public static VBox setUpBox(StackPane stack) {
        VBox box = new VBox();
        
        box = establishBoxProperties(box);
        propogateElementsInBox(box, stack);
        return box;
    }
    
    private static String getQuote() {
        if(newb) {
            return introduction;
        } else return response;
    }
    
    public static void propogateElementsInBox(VBox box, StackPane stack) {
        box.getChildren().clear();
        box.getChildren().add(new Label(getQuote()));
        box.getChildren().add(createMuteButton());
        box.getChildren().add(createBackButton(stack));
        box.getChildren().add(createVoiceButton());
        box.getChildren().add(createSaveButton());
    }
    
    private static Button createSaveButton() {
        Button saveButton = new Button("SAVE");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                    Persistence.saveGame();
            }
        });
        return saveButton;
    }
    
    private static Button createVoiceButton() {
        voiceButton = new Button("VOICE");
        voiceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                disableVoiceButton();
                String command = VoiceRecognizer.recognizeVoice();
                voiceCommand(command);

                
            }
        });
        return voiceButton;
    }
    
    private static void voiceCommand(String command) {
                enableVoiceButton();
                System.out.println(command);
                if(command.equals("play")) {
                    UniverseMapController.playSound();
                } else if (command.equals("exit")) {
                    closeCPUView(stackPane);
                } else if (command.equals("hello")) {
                    sayString("Ohh, hello Captain " + name + "!");
                } else if (command.equals("stop")) {
                    UniverseMapController.muteSound();
                } else if(command.equals("save")) {
                    Persistence.saveGame();
                }
    }
    public static void disableVoiceButton() {
        voiceButton.setDisable(true);
    }
    
    public static void enableVoiceButton() {
        voiceButton.setDisable(false);
    }

    public static VBox establishBoxProperties(VBox box) {
        box.setAlignment(Pos.CENTER);
        //box.setCenterShape(true);
        box.setFillWidth(true);
        box.minHeight(940);
        box.minWidth(600);
        box.setLayoutX((currParent.getLayoutBounds().getWidth()/ 2) - (box.getWidth() / 2));
        box.setLayoutY((currParent.getLayoutBounds().getHeight()/ 2) - (box.getHeight() / 2));
        //box.setStyle("-fx-background-color: rgb(0,3,80)");
        return box;

    }
    
    public static void modifyParent(Pane parent) {
//        parent.setEffect(new BoxBlur());
        //parent.setPickOnBounds(true);
        //parent.setMouseTransparent(true);
    }
    
    private static Button createMuteButton() {
        Button muteButton = new MuteButton();
        return muteButton;
    }
    public static class MuteButton extends Button {
        private EventHandler<ActionEvent> muteHandler;
        private EventHandler<ActionEvent> unMuteHandler;
        public MuteButton() {
            super("MUSIC");
            muteHandler = createMuteHandler(this);
            unMuteHandler = createUnMuteHandler(this);
            this.setOnAction(createUnMuteHandler(this));
        }
        
        private EventHandler<ActionEvent> createMuteHandler(MuteButton butt) {
            EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {
                    UniverseMapController.muteSound();
                    //makeButtonSayUNMUTE(butt);
                    butt.setOnAction(unMuteHandler);
                }
            };
            return handler;
        }
                private EventHandler<ActionEvent> createUnMuteHandler(MuteButton butt) {
            EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {
                    UniverseMapController.playSound();
                    //makeButtonSayMUTE(butt);
                    butt.setOnAction(muteHandler);
                }
            };
            return handler;
        }
    }
    
    private static void makeButtonSayMUTE(Button butt) {
        butt.setText("MUTE");
    }
    private static void makeButtonSayUNMUTE(Button butt) {
        butt.setText("UNMUTE");
    }
    
    private static Button createBackButton(StackPane stack) {
        Button backButton = new Button("BACK");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                closeCPUView(stack);
            }
        });
        return backButton;
    }
}