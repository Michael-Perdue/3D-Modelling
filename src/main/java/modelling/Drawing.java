package modelling;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Drawing {

    private final int height = 1000;
    private final int width = 1000;
    private SubScene scene;
    private final Group group = new Group();
    private ArrayList<RenderableObject> selectedObject= new ArrayList<RenderableObject>();
    private ArrayList<RenderableObject> shapes = new ArrayList<RenderableObject>();
    private PerspectiveCamera camera = new PerspectiveCamera(true);
    private double startX = 0, startY = 0, startAngleX = 0, startAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0) , angleY = new SimpleDoubleProperty(0);
    private boolean rightClick = false;
    private ToggleButton rotateButton,moveButton,selectButton;
    private static Drawing instance;

    public static Drawing getInstance(){
        if(instance == null)
            new Drawing();
        return instance;
    }

    private Drawing(){
        instance = this;
    }

    private void setupCameraControl() {
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

    public void createSphere(){
        Sphere3D sphere= new Sphere3D(50);
        shapes.add(sphere);
        group.getChildren().add(sphere.getShape3D());
        sphere.getShape3D().setOnMouseClicked(clicked -> {
            if(!selectedObject.contains(sphere)) {
                selectedObject.add(sphere);
                if (selectButton.isSelected())
                    new ConfigBox().generateBox();
                ((PhongMaterial)sphere.getShape3D().getMaterial()).setSpecularColor(Color.AQUA);
            }else {
                selectedObject.remove(sphere);
                ((PhongMaterial)sphere.getShape3D().getMaterial()).setSpecularColor(null);
            }
        });
    }

    public Box3D createBox(double d,double h,double w,double x,double y, double z){
        Box3D box = createBox(d,h,w);
        box.setX(x,false);
        box.setY(y,false);
        box.setZ(z,false);
        return box;
    }

    public Box3D createBox(double d,double h,double w){
        Box3D box= new Box3D(d,h,w);
        shapes.add(box);
        group.getChildren().add(box.getShape3D());
        box.getShape3D().setOnMouseClicked(clicked -> {
            if(clicked.getButton() == MouseButton.PRIMARY) {
                if(!selectedObject.contains(box)) {
                    selectedObject.add(box);
                    if (selectButton.isSelected())
                        new ConfigBox().generateBox();
                    Box outline = (Box)box.createOutline();
                    group.getChildren().add(outline);
                }else{
                    selectedObject.remove(box);
                    System.out.println(group.getChildren().remove(box.getOutline()));
                    box.removeOutline();
                }
            }
        });
        return box;
    }

    public VBox generateButtons(){
        ButtonBar buttonBar = new ButtonBar();
        rotateButton = new ToggleButton("Rotate");
        rotateButton.setPrefSize(80,20);
        moveButton = new ToggleButton("Move");
        moveButton.setPrefSize(80,20);
        selectButton = new ToggleButton("Select");
        selectButton.setPrefSize(80,20);
        ToggleButton squareButton = new ToggleButton("Add Square");
        squareButton.setPrefSize(100,20);
        ToggleGroup toggleGroup = new ToggleGroup();
        rotateButton.setToggleGroup(toggleGroup);
        moveButton.setToggleGroup(toggleGroup);
        selectButton.setToggleGroup(toggleGroup);
        squareButton.setToggleGroup(toggleGroup);
        squareButton.setOnAction(clicked ->new ConfigBox().generateSquareBox());
        Button resetCameraButton = new Button("Reset Camera");
        resetCameraButton.setOnAction(clicked ->{
            angleX.set(0);
            angleY.set(0);
        });
        resetCameraButton.setPrefSize(110,20);
        ButtonBar.setButtonData(squareButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(rotateButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(moveButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(selectButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(resetCameraButton, ButtonBar.ButtonData.APPLY);
        buttonBar.getButtons().addAll(squareButton,rotateButton,selectButton,moveButton,resetCameraButton);
        VBox vBox = new VBox(buttonBar);
        vBox.setStyle("-fx-background-color: GREY");
        return vBox;
    }

    public boolean rotateSelected(){
        return rotateButton.isSelected();
    }

    public boolean moveSelected(){
        return moveButton.isSelected();
    }

    public Scene generateScene(){
        camera.setTranslateZ(-400);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        Box3D box1 = createBox(50,50,50);
        Box3D box2 = createBox(30,20,60);
        PhongMaterial cobble = new PhongMaterial();
        cobble.setDiffuseMap(new Image(Drawing.class.getResourceAsStream("/cobble.png")));
        cobble.setBumpMap(new Image(Drawing.class.getResourceAsStream("/cobbleNormal.png")));
        PhongMaterial test = new PhongMaterial();
        test.setDiffuseColor(Color.WHITE);
        test.setSpecularColor(Color.WHITE);
        box1.getShape3D().setMaterial(cobble);
        box2.getShape3D().setMaterial(test);
        scene = new SubScene(group,width,height,true, SceneAntialiasing.DISABLED);
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

    public void scroll(double movement){
        group.translateZProperty().set(group.getTranslateZ() + movement);
    }

    public void rotateX(double angle){
        selectedObject.forEach(shape-> shape.setRotationX(angle));
    }

    public void rotateY(double angle){
        selectedObject.forEach(shape-> shape.setRotationY(angle));
    }

    public void rotateZ(double angle){
        selectedObject.forEach(shape-> shape.setRotationZ(angle));
    }

    public void setX(double offset,boolean accumulative){
        selectedObject.forEach(shape-> shape.setX(offset,accumulative));
    }

    public void setY(double offset,boolean accumulative){
        selectedObject.forEach(shape-> shape.setY(offset,accumulative));
    }

    public void setZ(double offset, boolean accumulative){
        selectedObject.forEach(shape-> shape.setZ(offset,accumulative));
    }

    public RenderableObject getSelectedObject() {
        return selectedObject.get(0);
    }
}