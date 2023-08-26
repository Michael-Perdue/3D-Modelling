package render;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import java.util.ArrayList;

public class Drawing {

    private final static int height = 1000;
    private final static int width = 1000;
    private static Scene scene;
    private final static Group group = new Group();
    private static RenderableObject selectedObject;
    private static ArrayList<RenderableObject> shapes = new ArrayList<RenderableObject>();
    private static PerspectiveCamera camera = new PerspectiveCamera(false);
    private static Transform currentTransfrom = new Rotate();
    //Tracks drag starting point for x and y
    private static double anchorX, anchorY;
    //Keep track of current angle for x and y
    private static double anchorAngleX = 0;
    private static double anchorAngleY = 0;
    //We will update these after drag. Using JavaFX property to bind with object
    private static final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private static final DoubleProperty angleY = new SimpleDoubleProperty(0);

    public static void createSphere(){
        Sphere3D sphere= new Sphere3D(20);
        shapes.add(sphere);
        group.getChildren().add(sphere);
        sphere.translateYProperty().set(height/2);
        sphere.translateXProperty().set(width/2);
        sphere.setOnMouseClicked(clicked -> {
            selectedObject =sphere;
            ConfigBox.generateBox();
        });
    }

    public static void createBox(){
        Box3D box= new Box3D(70,70,40);
        shapes.add(box);
        group.getChildren().add(box);
        box.translateYProperty().set(height/2);
        box.translateXProperty().set(height/2);
        selectedObject = box;
        box.setOnMouseClicked(clicked -> {
            selectedObject = box;
            ConfigBox.generateBox();
        });
    }

    public static Scene generateScene(){
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        scene = new Scene(group,width,height);
        scene.setCamera(camera);
        scene.setFill(Color.GREY);
        createBox();
        return scene;
    }

    public static void scroll(double movement){
        group.translateZProperty().set(group.getTranslateZ() + movement);
    }

    public static void rotateX(double angle){
        selectedObject.setRotationX(angle);
    }

    public static void rotateY(double angle){
        selectedObject.setRotationY(angle);
    }

    public static void rotateZ(double angle){
        selectedObject.setRotationZ(angle);
    }

    public static RenderableObject getSelectedObject() {
        return selectedObject;
    }
}