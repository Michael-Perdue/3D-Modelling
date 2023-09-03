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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class DrawingGUI {

    private final int height = 1000, width = 1000;
    private SubScene scene;
    private final Group group = new Group();
    private ArrayList<RenderableObject> selectedObject= new ArrayList<RenderableObject>();
    private PerspectiveCamera camera = new PerspectiveCamera(true);
    private double startX = 0, startY = 0, startAngleX = 0, startAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0) , angleY = new SimpleDoubleProperty(0);
    private boolean rightClick = false;
    private LightManager lightManager = new LightManager();
    private ToggleButton rotateButton,moveButton, selectButton;
    private static DrawingGUI instance;

    public static DrawingGUI getInstance(){
        if(instance == null)
            new DrawingGUI();
        return instance;
    }

    private DrawingGUI(){
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


    public void renderableObjectClicked(RenderableObject object){
        if (!selectedObject.contains(object)) {
            selectedObject.add(object);
            if (selectButton.isSelected())
                new ConfigBox().generateBox();
            Shape3D outline = object.createOutline();
            group.getChildren().add(outline);
        } else {
            selectedObject.remove(object);
            group.getChildren().remove(object.getOutline());
            object.removeOutline();
        }
    }

    public Sphere3D createSphere(double r){
        Sphere3D sphere= new Sphere3D(r);
        group.getChildren().add(sphere.getShape3D());
        sphere.getShape3D().setOnMouseClicked(clicked -> {
            if(clicked.getButton() == MouseButton.PRIMARY) {
                renderableObjectClicked(sphere);
            }
        });
        return sphere;
    }

    public Sphere3D createSphere(double r,double x,double y, double z){
        Sphere3D sphere = createSphere(r);
        sphere.setX(x,false);
        sphere.setY(y,false);
        sphere.setZ(z,false);
        return sphere;
    }

    public Box3D createBox(double d,double h,double w){
        Box3D box= new Box3D(d,h,w);
        group.getChildren().add(box.getShape3D());
        box.getShape3D().setOnMouseClicked(clicked -> {
            if(clicked.getButton() == MouseButton.PRIMARY) {
                renderableObjectClicked(box);
            }
        });
        return box;
    }

    public Box3D createBox(double d,double h,double w,double x,double y, double z){
        Box3D box = createBox(d,h,w);
        box.setX(x,false);
        box.setY(y,false);
        box.setZ(z,false);
        return box;
    }

    public void deleteSelected(){
        // Must make a copy of the list to avoid concurrent modification errors!
        ArrayList<RenderableObject> tempList = new ArrayList<>(selectedObject);
        tempList.forEach(shape -> {
            group.getChildren().remove(shape.getShape3D());
            group.getChildren().remove(shape.getOutline());
            if(shape.getPointLight() != null) {
                group.getChildren().remove(shape.getPointLight());
                lightManager.removeLight(shape);
            }
            shape.removeOutline();
            selectedObject.remove(shape);
        });
    }

    public void duplicateSelected(){
        // Must make a copy of the list to avoid concurrent modification errors!
        ArrayList<RenderableObject> tempList = new ArrayList<>(selectedObject);
        tempList.forEach(shape -> {
            renderableObjectClicked(shape);
            if(shape.getType().equals("box")) {
                Box current = (Box)shape.getShape3D();
                Box3D duplicate = createBox(
                        current.getWidth(),
                        current.getHeight(),
                        current.getDepth(),
                        current.getTranslateX()+1,
                        current.getTranslateY(),
                        current.getTranslateZ());
                shape.deepCopyObject(duplicate,false);
                renderableObjectClicked(duplicate);
            }
            if(shape.getType().equals("sphere")) {
                Sphere current = (Sphere) shape.getShape3D();
                Sphere3D duplicate = createSphere(
                        current.getRadius(),
                        current.getTranslateX()+1,
                        current.getTranslateY(),
                        current.getTranslateZ());
                shape.deepCopyObject(duplicate,false);
                renderableObjectClicked(duplicate);
            }
        });
    }

    public void setFont(Node button){button.setStyle("-fx-font-size: 10px;");}

    public VBox generateButtons(){
        ButtonBar buttonBar = new ButtonBar();
        ToggleGroup toggleGroup = new ToggleGroup();

        rotateButton = new ToggleButton("Rotate");
        rotateButton.setMaxWidth(50);
        moveButton = new ToggleButton("Move");
        moveButton.setMaxWidth(50);
        selectButton = new ToggleButton("Configure");
        selectButton.setMaxWidth(50);
        selectButton.setOnAction(clicked -> toggleAllObjects());
        ToggleButton squareButton = new ToggleButton("Add Square");
        squareButton.setMaxWidth(50);
        squareButton.setOnAction(clicked -> {
            new ConfigBox().generateSquareBox();
            toggleGroup.selectToggle(null);
        });
        ToggleButton sphereButton = new ToggleButton("Add Sphere");
        sphereButton.setMaxWidth(50);
        sphereButton.setOnAction(clicked -> {
            new ConfigBox().generateSphereBox();
            toggleGroup.selectToggle(null);
        });
        ToggleButton deleteButton = new ToggleButton("Delete");
        deleteButton.setMaxWidth(50);
        deleteButton.setOnAction(clicked -> {
            deleteSelected();
            toggleGroup.selectToggle(null);
        });
        ToggleButton duplicateButton = new ToggleButton("Duplicate");
        duplicateButton.setMaxWidth(50);
        duplicateButton.setOnAction(clicked -> {
            duplicateSelected();
            toggleGroup.selectToggle(null);
        });
        duplicateButton.setStyle("-fx-font-size: 10px;");

        rotateButton.setToggleGroup(toggleGroup);
        moveButton.setToggleGroup(toggleGroup);
        selectButton.setToggleGroup(toggleGroup);
        squareButton.setToggleGroup(toggleGroup);
        deleteButton.setToggleGroup(toggleGroup);
        duplicateButton.setToggleGroup(toggleGroup);
        sphereButton.setToggleGroup(toggleGroup);

        Button resetCameraButton = new Button("Reset Camera");
        resetCameraButton.setOnAction(clicked ->{
            angleX.set(0);
            angleY.set(0);
        });
        resetCameraButton.setMaxWidth(90);

        Button hideLightButton = new Button("Hide Light");
        hideLightButton.setOnAction(clicked ->{
            if(lightManager.isLightsVisable()) {
                lightManager.hideLights();
                hideLightButton.setText("Show Light");
            }
            else {
                lightManager.showLights();
                hideLightButton.setText("Hide Light");
            }
        });
        hideLightButton.setMaxWidth(90);

        Button disableLightButton = new Button("Disable Lighting");
        disableLightButton.setOnAction(clicked ->{
            if(lightManager.isLightsEnabled()) {
                lightManager.disableLights();
                disableLightButton.setText("Enable lighting");
            }
            else {
                lightManager.enableLights();
                disableLightButton.setText("Disable Lighting");
            }
        });
        disableLightButton.setMaxWidth(110);

        ButtonBar.setButtonData(squareButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(sphereButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(rotateButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(moveButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(selectButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(deleteButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(duplicateButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(resetCameraButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(hideLightButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(disableLightButton, ButtonBar.ButtonData.APPLY);
        buttonBar.getButtons().addAll(sphereButton,squareButton, duplicateButton, deleteButton,rotateButton,selectButton,moveButton,hideLightButton,disableLightButton,resetCameraButton);

        buttonBar.getButtons().forEach(button ->setFont(button));

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
        box1.getShape3D().setMaterial(Materials.getInstance().getCobble());
        box2.getShape3D().setMaterial(Materials.getInstance().getWood());
        box3.getShape3D().setMaterial(Materials.getInstance().getWood());
        scene = new SubScene(group,width,height,true, SceneAntialiasing.DISABLED);
        scene.setCamera(camera);
        scene.setFill(Color.GREY);
        setupCameraControl();
        VBox vBox = generateButtons();
        vBox.getChildren().add(scene);
        vBox.setAlignment(Pos.TOP_LEFT);
        Sphere3D sphere = createSphere(5,38,-17,-77);
        lightManager.addLight(sphere);
        group.getChildren().add(new AmbientLight(Color.rgb(192,192,192,0.01)));
        Scene mainScene = new Scene(vBox,1000,1000);
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

    public void setAllBoxDimensions(double depth,double height,double width){
        ArrayList<RenderableObject> tempList = new ArrayList<>(selectedObject);
        tempList.forEach(shape-> {
            try {
                Box3D box = (Box3D) shape;
                box.setAllDimensions(depth,height,width);
            }catch (Exception e){e.printStackTrace();}
        });
    }

    public void setSphereDimensions(double radius){
        ArrayList<RenderableObject> tempList = new ArrayList<>(selectedObject);
        tempList.forEach(shape-> {
            try {
                Sphere3D sphere = (Sphere3D) shape;
                sphere.setAllDimensions(radius);
            }catch (Exception e){e.printStackTrace();}
        });
    }

    public String selectedType(){
        return selectedObject.get(0).getType();
    }

    public void setMaterial(String name){
        selectedObject.forEach(shape-> shape.getShape3D().setMaterial(Materials.getInstance().getMaterial(name)));
    }

    public RenderableObject getSelectedObject() {
        return selectedObject.get(0);
    }

    public void addNode(Node node){
        group.getChildren().add(node);
    }

    public void removeNode(Node node){
        group.getChildren().remove(node);
    }

    public void addLight(RenderableObject object){
        lightManager.addLight(object);
    }

    public void removeLight(RenderableObject object){
        lightManager.removeLight(object);
    }


    public void toggleAllObjects(){
        ArrayList<RenderableObject> tempObjects = new ArrayList<>(selectedObject);
        tempObjects.forEach(shape -> renderableObjectClicked(shape));
    }

    public void removeObject(RenderableObject object){
        selectedObject.remove(object);
        group.getChildren().remove(object.shape);
        group.getChildren().remove(object.outline);
        group.getChildren().remove(object.pointLight);
    }

    public void addObject(RenderableObject object){
        selectedObject.add(object);
        group.getChildren().add(object.shape);
        group.getChildren().add(object.outline);
        group.getChildren().add(object.pointLight);
    }
}