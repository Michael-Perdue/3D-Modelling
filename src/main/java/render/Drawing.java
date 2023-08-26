package render;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;

public class Drawing {

    private final static int height = 1000;
    private final static int width = 1000;
    private static Scene scene;
    private final static Group group = new Group();
    private static RenderableObject selectedObject;
    private static ArrayList<RenderableObject> shapes = new ArrayList<RenderableObject>();
    private static PerspectiveCamera camera = new PerspectiveCamera(true);
    private static double startX = 0, startY = 0, startAngleX = 0, startAngleY = 0;
    private static final DoubleProperty angleX = new SimpleDoubleProperty(0) , angleY = new SimpleDoubleProperty(0);
    private static boolean rightClick = false;

    private static void setupCameraControl() {
        Rotate xAxisRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate yAxisRotate = new Rotate(0, Rotate.Y_AXIS);
        xAxisRotate.angleProperty().bind(angleX);
        yAxisRotate.angleProperty().bind(angleY);
        group.getTransforms().addAll(
                xAxisRotate,
                yAxisRotate
        );
        scene.setOnMousePressed(clicked -> {
            //Checks if the users last mouse click was the right one
            if(clicked.getButton() == MouseButton.SECONDARY) {
                //stores the starting point of drag
                startX = clicked.getSceneX();
                startY = clicked.getSceneY();
                //stores the angle of the camera before drag
                startAngleX = angleX.get();
                startAngleY = angleY.get();
                //sets it true that the user last mouse click was the right one
                rightClick = true;
            }else {
                rightClick = false;
            }
        });
        scene.setOnMouseDragged(dragged -> {
            //Checks if the users last mouse click was the right one
            if(rightClick) {
                //Sets the cameras new angle
                angleX.set(startAngleX - (startY - dragged.getSceneY()));
                angleY.set(startAngleY + startX - dragged.getSceneX());
            }
        });
    }

    public static void createSphere(){
        Sphere3D sphere= new Sphere3D(50);
        shapes.add(sphere);
        group.getChildren().add(sphere);
        sphere.setOnMouseClicked(clicked -> {
            selectedObject =sphere;
            ConfigBox.generateBox();
        });
    }

    public static void createBox(double d,double h,double w){
        Box3D box= new Box3D(d,h,w);
        shapes.add(box);
        group.getChildren().add(box);
        selectedObject = box;
        box.setOnMouseClicked(clicked -> {
            if(clicked.getButton() == MouseButton.PRIMARY) {
                selectedObject = box;
                ConfigBox.generateBox();
            }
        });
    }

    public static Scene generateScene(){
        camera.setTranslateZ(-400);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        scene = new Scene(group,width,height,true);
        scene.setCamera(camera);
        scene.setFill(Color.GREY);
        setupCameraControl();
        createBox(70,70,40);
        createBox(30,20,60);
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