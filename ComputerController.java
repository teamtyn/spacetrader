/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
/**
 *
 * @author Local Clayton
 */
public class ComputerController {
    private static Pane currParent;
    public static Pane openCPUView(Pane parent) {
        currParent = parent;
        modifyParent(parent);
        VBox box = setUpBox();
        parent.getChildren().add(box);
        return parent;
    }
    
    public static void closeCPUView(Pane parent, Pane cpuView) {
        
    }
    
    public static VBox setUpBox() {
        VBox box = new VBox();
        establishBoxProperties(box);
        propogateElementsInBox(box);
        return box;
    }
    
    public static void propogateElementsInBox(VBox box) {
        box.getChildren().add(new Button("HELLO I AM COMPUTER."));
        box.getChildren().add(new Label("HELLO I AM COMPUTER."));
    }
    
    public static void establishBoxProperties(VBox box) {
        box.setAlignment(Pos.CENTER);
        box.setCenterShape(true);
        box.setFillWidth(true);
        box.minHeight(300);
        box.minWidth(300);
        box.setLayoutX((currParent.getLayoutBounds().getWidth()/ 2) - (box.getWidth() / 2));
        box.setLayoutY((currParent.getLayoutBounds().getHeight()/ 2) - (box.getHeight() / 2));
        box.setStyle("-fx-background-color: rgb(0,3,80)");

    }
    
    public static void modifyParent(Pane parent) {
        parent.setEffect(new BoxBlur());
        parent.setPickOnBounds(true);
        parent.setMouseTransparent(true);
    }
}
