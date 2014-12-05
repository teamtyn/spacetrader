/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.util.Duration;

/**
 *
 * @author Administrator
 */
public class Debris extends Xform{
    private double dx;
    private double dy;
    private double dz;
    private boolean isDone;

    public Debris(
            double x, double y, double dx, double dy, double angle, Color c) {
        super();
        Random r = GameModel.getRandom();

        this.dx = dx;
        this.dy = dy;
        dz = 0;
        isDone = false;
        
        setTranslateX(x);
        setTranslateY(y);
        setRz(angle);

        ArrayList<MeshView> debris = new ArrayList<>();
        ArrayList<KeyValue> keyValues = new ArrayList<>();
        
        for (int i = 0; i < GameModel.getRandom().nextInt(10) + 3; i++) {
            TriangleMesh debrisMesh = new TriangleMesh();
            debrisMesh.getPoints().addAll(
                    1f * r.nextFloat() - 0.5f,
                    1f * r.nextFloat() - 0.5f,
                    1f * r.nextFloat() - 0.5f,
                    1f * r.nextFloat() - 0.5f,
                    1f * r.nextFloat() - 0.5f,
                    1f * r.nextFloat() - 0.5f,
                    1f * r.nextFloat() - 0.5f,
                    1f * r.nextFloat() - 0.5f,
                    1f * r.nextFloat() - 0.5f
            );
            debrisMesh.getTexCoords().addAll(0f, 0f);
            debrisMesh.getFaces().addAll(0, 0, 1, 0, 2, 0);
            MeshView piece = new MeshView(debrisMesh);
            piece.setCullFace(CullFace.NONE);
            PhongMaterial debrisMaterial = new PhongMaterial(c);
            piece.setMaterial(debrisMaterial);
            piece.setRotationAxis(new Point3D(
                    r.nextDouble(), r.nextDouble(), r.nextDouble()));
            EncounterSubScene.NO_SHADE.getScope().add(piece);

            keyValues.add(new KeyValue(piece.translateXProperty(), 200 * r.nextDouble()));
            keyValues.add(new KeyValue(piece.translateYProperty(), 20 * r.nextDouble() - 10));
            keyValues.add(new KeyValue(piece.translateZProperty(), 100 * r.nextDouble() - 50));
            keyValues.add(new KeyValue(piece.rotateProperty(), 4 * 360));
            
            getChildren().add(piece);
        }
        
        Timeline spread = new Timeline(
                new KeyFrame(Duration.seconds(2), "Spread",
                        (ActionEvent event) -> {
                            isDone = true;
                        }, keyValues)
        );
        spread.play();
        
        //getChildren().add(new Box(1,1,1));
    }
    
    public void update() {
        setTx(getTx() + dx);
        setTy(getTy() + dy);
        setTz(getTz() + dz);
        
        dz += 0.01; 
    }
    
    public boolean getIsDone() {
        return isDone;
    }
}
