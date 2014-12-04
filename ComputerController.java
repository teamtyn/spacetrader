/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
/**
 *
 * @author Local Clayton
 */
public class ComputerController {
    private static Pane currParent;
    public static Pane openCPUView(Pane parent) {
        currParent = parent;
        System.out.println(parent);
        modifyParent(parent);
        StackPane view = setUpView();
        parent.getChildren().add(view);
        return parent;
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
        stack.minHeight((currParent.getLayoutBounds().getHeight()/ 2));
        stack.minWidth((currParent.getLayoutBounds().getWidth()/ 2));
        stack.maxHeight(Double.MAX_VALUE);
        stack.maxWidth(Double.MAX_VALUE);
        stack.setLayoutX((currParent.getLayoutBounds().getWidth() / 4));
        stack.setLayoutY((currParent.getLayoutBounds().getHeight() / 4));
        stack.setStyle("-fx-background-color: rgb(0,3,80)");

        return stack;
    }

    
    private static Rectangle createRectangle() {
        Rectangle box = new Rectangle((currParent.getLayoutBounds().getWidth() / 2),
                (currParent.getLayoutBounds().getHeight() / 2));
        box.maxHeight(Double.MAX_VALUE);
        box.maxWidth(Double.MAX_VALUE);
        box.setLayoutX((currParent.getLayoutBounds().getWidth()) - (box.getWidth() / 2));
        box.setLayoutY((currParent.getLayoutBounds().getHeight()) - (box.getHeight() / 2));
        box.setStyle("-fx-background-color: rgb(50,3,80)");
        box.setStyle("-fx-border-color: rgb(100,100,100)");
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
    
    public static void propogateElementsInBox(VBox box, StackPane stack) {
        box.getChildren().clear();
        box.getChildren().add(new Label("HELLO CAPTAIN " 
                + GameModel.getPlayer().getName() + ", I AM COMPUTER."));
        box.getChildren().add(createMuteButton());
        box.getChildren().add(createBackButton(stack));
    }
    
    public static VBox establishBoxProperties(VBox box) {
        box.setAlignment(Pos.CENTER);
        //box.setCenterShape(true);
        box.setFillWidth(true);
        box.minHeight(940);
        box.minWidth(600);
        box.setLayoutX((currParent.getLayoutBounds().getWidth()/ 2) - (box.getWidth() / 2));
        box.setLayoutY((currParent.getLayoutBounds().getHeight()/ 2) - (box.getHeight() / 2));
        box.setStyle("-fx-background-color: rgb(0,3,80)");
        return box;

    }
    
    public static void modifyParent(Pane parent) {
        //parent.setEffect(new BoxBlur());
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
            this.setOnAction(createMuteHandler(this));
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
