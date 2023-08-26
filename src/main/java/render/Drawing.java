package render;

import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import java.util.ArrayList;

public class Drawing {

    private final static int height = 1000;
    private final static int width = 1000;
    private final static Group group = new Group();
    private static ArrayList<RenderableObject> shapes = new ArrayList<RenderableObject>();
    private static PerspectiveCamera camera = new PerspectiveCamera(false);

    public static void createSphere(){
        Sphere3D sphere= new Sphere3D(200);
        shapes.add(sphere);
        group.getChildren().add(sphere);
        sphere.translateYProperty().set(height/2);
        sphere.translateXProperty().set(width/2);
    }

    public static void createBox(){
        Box3D box= new Box3D(70,70,40);
        shapes.add(box);
        group.getChildren().add(box);
        box.translateYProperty().set(height/2);
        box.translateXProperty().set(height/2);
    }

    public static Scene getScene(){
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        Scene scene = new Scene(group,width,height);
        scene.setCamera(camera);
        scene.setFill(Color.GREY);
        createBox();
        return scene;
    }

    public static void rotateX(double angle){
        shapes.forEach(shape -> {
            shape.setRotationX(angle);
        });
    }

    public static void rotateY(double angle){
        shapes.forEach(shape -> {
            shape.setRotationY(angle);
        });
    }

    public static void rotateZ(double angle){
        shapes.forEach(shape -> {
            shape.setRotationZ(angle);
        });
    }

}