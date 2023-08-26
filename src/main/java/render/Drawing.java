package render;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;

public class Drawing {

    private final static int height = 1000;
    private final static int width = 1000;
    private static SubScene scene;
    private final static Group group = new Group();
    private static RenderableObject selectedObject;
    private static ArrayList<RenderableObject> shapes = new ArrayList<RenderableObject>();
    private static PerspectiveCamera camera = new PerspectiveCamera(true);
    private static double startX = 0, startY = 0, startAngleX = 0, startAngleY = 0;
    private static final DoubleProperty angleX = new SimpleDoubleProperty(0) , angleY = new SimpleDoubleProperty(0);
    private static boolean rightClick = false;
    private static ToggleButton rotateButton,moveButton,selectButton;

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
            if(selectButton.isSelected())
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
                if(selectButton.isSelected())
                    ConfigBox.generateBox();
            }
        });
    }

    public static VBox generateButtons(){
        ButtonBar buttonBar = new ButtonBar();
        rotateButton = new ToggleButton("Rotate");
        rotateButton.setPrefSize(80,20);
        moveButton = new ToggleButton("Move");
        moveButton.setPrefSize(80,20);
        selectButton = new ToggleButton("Select");
        selectButton.setPrefSize(80,20);
        ToggleGroup toggleGroup = new ToggleGroup();
        rotateButton.setToggleGroup(toggleGroup);
        moveButton.setToggleGroup(toggleGroup);
        selectButton.setToggleGroup(toggleGroup);
        Button resetCameraButton = new Button("Reset Camera");
        resetCameraButton.setOnAction(clicked ->{
            angleX.set(0);
            angleY.set(0);
        });
        resetCameraButton.setPrefSize(110,20);
        ButtonBar.setButtonData(rotateButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(moveButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(selectButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(resetCameraButton, ButtonBar.ButtonData.APPLY);
        buttonBar.getButtons().addAll(rotateButton,selectButton,moveButton,resetCameraButton);
        VBox vBox = new VBox(buttonBar);
        vBox.setStyle("-fx-background-color: GREY");
        return vBox;
    }

    public static boolean rotateSelected(){
        return rotateButton.isSelected();
    }

    public static boolean moveSelected(){
        return moveButton.isSelected();
    }

    public static Scene generateScene(){
        camera.setTranslateZ(-400);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        createBox(70,70,40);
        createBox(30,20,60);
        scene = new SubScene(group,width,height,true, SceneAntialiasing.BALANCED);
        scene.setCamera(camera);
        scene.setFill(Color.GREY);
        setupCameraControl();
        VBox vBox = generateButtons();
        vBox.getChildren().add(scene);
        vBox.setAlignment(Pos.TOP_LEFT);
        Scene mainScene = new Scene(vBox);
        mainScene.setFill(Color.GREY);
        return mainScene;
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

    public static void setX(double offset){
        selectedObject.setX(offset);
    }

    public static void setY(double offset){
        selectedObject.setY(offset);
    }

    public static void setZ(double offset){
        selectedObject.setZ(offset);
    }


    public static RenderableObject getSelectedObject() {
        return selectedObject;
    }
}