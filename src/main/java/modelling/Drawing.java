package modelling;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
                    group.getChildren().remove(box.getOutline());
                    box.removeOutline();
                }
            }
        });
        return box;
    }

    public void deleteSelected(){
        // Must make a copy of the list to avoid concurrent modification errors!
        ArrayList<RenderableObject> tempList = new ArrayList<>(selectedObject);
        tempList.forEach(shape -> {
            group.getChildren().remove(shape.getShape3D());
            group.getChildren().remove(shape.getOutline());
            shape.removeOutline();
            selectedObject.remove(shape);
        });
    }

    public VBox generateButtons(){
        ButtonBar buttonBar = new ButtonBar();
        rotateButton = new ToggleButton("Rotate");
        rotateButton.setPrefSize(80,20);
        moveButton = new ToggleButton("Move");
        moveButton.setPrefSize(80,20);
        selectButton = new ToggleButton("Configure");
        selectButton.setPrefSize(80,20);
        ToggleButton squareButton = new ToggleButton("Add Square");
        squareButton.setPrefSize(100,20);
        squareButton.setOnAction(clicked ->new ConfigBox().generateSquareBox());
        ToggleButton deleteButton = new ToggleButton("Delete Selected");
        deleteButton.setPrefSize(110,20);
        deleteButton.setOnAction(clicked ->deleteSelected());

        ToggleGroup toggleGroup = new ToggleGroup();
        rotateButton.setToggleGroup(toggleGroup);
        moveButton.setToggleGroup(toggleGroup);
        selectButton.setToggleGroup(toggleGroup);
        squareButton.setToggleGroup(toggleGroup);
        deleteButton.setToggleGroup(toggleGroup);

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
        ButtonBar.setButtonData(deleteButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(resetCameraButton, ButtonBar.ButtonData.APPLY);
        buttonBar.getButtons().addAll(squareButton,deleteButton,rotateButton,selectButton,moveButton,resetCameraButton);

        HBox emptyPadding = new HBox();
        emptyPadding.setPrefWidth(7);

        VBox vBox = new VBox(emptyPadding,buttonBar);
        vBox.setPadding(new Insets(7));
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
        Box3D box2 = createBox(30,20,60,15,-35,-1);
        Box3D box3 = createBox(30,20,60,-18,-35,-1);
        PhongMaterial cobble = new PhongMaterial();
        cobble.setDiffuseMap(new Image(Drawing.class.getResourceAsStream("/cobble.png")));
        cobble.setBumpMap(new Image(Drawing.class.getResourceAsStream("/cobbleNormal.png")));
        PhongMaterial wood = new PhongMaterial();
        wood.setDiffuseMap(new Image(Drawing.class.getResourceAsStream("/wood.jpg")));
        wood.setBumpMap(new Image(Drawing.class.getResourceAsStream("/woodNormal.jpg")));
        box1.getShape3D().setMaterial(cobble);
        box2.getShape3D().setMaterial(wood);
        box3.getShape3D().setMaterial(wood);
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