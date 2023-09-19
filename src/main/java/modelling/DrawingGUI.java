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

    // The 3D scene
    private SubScene scene;

    // The root group for all objects in the scene
    private final Group group = new Group();

    // List of currently selected renderable objects
    private final ArrayList<RenderableObject> selectedObject= new ArrayList<RenderableObject>();

    // The camera for controlling the view
    private final PerspectiveCamera camera = new PerspectiveCamera(true);

    // Variables for handling mouse interactions
    private double startX = 0, startY = 0, startAngleX = 0, startAngleY = 0, initialCameraX = 0, initialCameraY = 0, getInitialCameraz = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0), angleY = new SimpleDoubleProperty(0);
    private boolean rightClick = false, drag = false;

    // Manager for handling lights
    private final LightManager lightManager = new LightManager();

    // Toggle buttons for different modes
    private ToggleButton rotateButton, moveButton, selectButton;

    // Singleton instance
    private static DrawingGUI instance;

    /**
     * Get the instance of the DrawingGUI class.
     * @return The instance of DrawingGUI.
     */
    public static DrawingGUI getInstance(){
        if(instance == null)
            new DrawingGUI();
        return instance;
    }

    /**
     * Private constructor for singleton pattern.
     */
    private DrawingGUI(){
        instance = this;
    }

    /**
     * Set up camera control for the 3D scene.
     */
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
                initialCameraX = camera.getTranslateX();
                initialCameraY = camera.getTranslateY();
            }else {
                rightClick = false;
            }
        });
        scene.setOnMouseDragged(dragged -> {
            //Checks if the users last mouse click was the right one
            if(rightClick && !drag) {
                //Sets the cameras new angle
                angleX.set(startAngleX - (startY - dragged.getSceneY()));
                angleY.set(startAngleY + startX - dragged.getSceneX());
            }else if(rightClick){
                camera.setTranslateX(initialCameraX - (dragged.getSceneX() - startX) * 0.1);
                camera.setTranslateY(initialCameraY - (dragged.getSceneY() - startY) * 0.1);

            }
        });
    }

    /**
     * Handles the click event for a renderable object. If the object is not selected, it adds it to the selected objects list,
     * generates a configuration box if the "Configure" button is selected, and adds an outline to the object in the scene.
     * If the object is already selected, it removes it from the selected objects list, removes the outline, and clears the configuration box.
     * @param object The renderable object that was clicked.
     */
    public void renderableObjectClicked(RenderableObject object){
        if (!selectedObject.contains(object)) {
            selectedObject.add(object);
            // Check if "Configure" button is selected to generate a configuration box
            if (selectButton.isSelected())
                new ConfigBox().generateBox();
            // Creates outline for the shape
            Shape3D outline = object.createOutline();
            group.getChildren().add(outline);
        } else {
            // Removes the outline for the shape
            selectedObject.remove(object);
            group.getChildren().remove(object.getOutline());
            object.removeOutline();
        }
    }

    /**
     * Creates a sphere with the specified radius and adds it to the 3D scene.
     * @param r The radius of the sphere.
     * @return The created sphere object.
     */
    public Sphere3D createSphere(double r){
        Sphere3D sphere= new Sphere3D(r);
        group.getChildren().add(sphere.getShape3D());
        // Adds a mouse click event handler to handle selection
        sphere.getShape3D().setOnMouseClicked(clicked -> {
            if(clicked.getButton() == MouseButton.PRIMARY) {
                renderableObjectClicked(sphere);
            }
        });
        return sphere;
    }

    /**
     * Creates a sphere with the specified radius and position (x, y, z) and adds it to the 3D scene.
     * @param r The radius of the sphere.
     * @param x The x-coordinate of the sphere's position.
     * @param y The y-coordinate of the sphere's position.
     * @param z The z-coordinate of the sphere's position.
     * @return The created sphere object.
     */
    public Sphere3D createSphere(double r,double x,double y, double z){
        Sphere3D sphere = createSphere(r);
        sphere.setX(x,false);
        sphere.setY(y,false);
        sphere.setZ(z,false);
        return sphere;
    }

    /**
     * Creates a box with the specified dimensions (depth, height, width) and adds it to the 3D scene.
     * @param d The depth of the box.
     * @param h The height of the box.
     * @param w The width of the box.
     * @return The created box object.
     */
    public Box3D createBox(double d,double h,double w){
        Box3D box= new Box3D(d,h,w);
        group.getChildren().add(box.getShape3D());
        // Add a mouse click event handler to handle selection
        box.getShape3D().setOnMouseClicked(clicked -> {
            if(clicked.getButton() == MouseButton.PRIMARY) {
                renderableObjectClicked(box);
            }
        });
        return box;
    }

    /**
     * Creates a box with the specified dimensions (depth, height, width) and position (x, y, z) and adds it to the 3D scene.
     * @param d The depth of the box.
     * @param h The height of the box.
     * @param w The width of the box.
     * @param x The x-coordinate of the box's position.
     * @param y The y-coordinate of the box's position.
     * @param z The z-coordinate of the box's position.
     * @return The created box object.
     */
    public Box3D createBox(double d,double h,double w,double x,double y, double z){
        Box3D box = createBox(d,h,w);
        box.setX(x,false);
        box.setY(y,false);
        box.setZ(z,false);
        return box;
    }

    /**
     * Deletes the selected renderable objects, including removing them from the scene and managing lights if applicable.
     */
    public void deleteSelected(){
        // Must make a copy of the list to avoid concurrent modification errors!
        ArrayList<RenderableObject> tempList = new ArrayList<>(selectedObject);
        tempList.forEach(shape -> {
            group.getChildren().remove(shape.getShape3D());
            group.getChildren().remove(shape.getOutline());
            // Check if the object has a point light and remove it
            if(shape.getPointLight() != null) {
                group.getChildren().remove(shape.getPointLight());
                lightManager.removeLight(shape);
            }
            shape.removeOutline();
            selectedObject.remove(shape);
        });
    }

    /**
     * Duplicates the selected renderable objects, including creating duplicates with small offsets and adding them to the scene.
     */
    public void duplicateSelected(){
        // Must make a copy of the list to avoid concurrent modification errors!
        ArrayList<RenderableObject> tempList = new ArrayList<>(selectedObject);
        tempList.forEach(shape -> {
            renderableObjectClicked(shape);
            if(shape.getType().equals("box")) {
                Box current = (Box)shape.getShape3D();
                // Create a duplicate box with a small offset
                Box3D duplicate = createBox(
                        current.getWidth(),
                        current.getHeight(),
                        current.getDepth(),
                        current.getTranslateX()+1,
                        current.getTranslateY(),
                        current.getTranslateZ());
                // Copy properties and add the duplicate to the selection
                shape.deepCopyObject(duplicate,false);
                renderableObjectClicked(duplicate);
            }
            if(shape.getType().equals("sphere")) {
                Sphere current = (Sphere) shape.getShape3D();
                // Create a duplicate sphere with a small offset
                Sphere3D duplicate = createSphere(
                        current.getRadius(),
                        current.getTranslateX()+1,
                        current.getTranslateY(),
                        current.getTranslateZ());
                // Copy properties and add the duplicate to the selection
                shape.deepCopyObject(duplicate,false);
                renderableObjectClicked(duplicate);
            }
        });
    }

    /**
     * Sets the font size of a JavaFX button.
     * @param button The JavaFX button to set the font size for.
     */
    public void setFont(Node button){button.setStyle("-fx-font-size: 10px;");}

    /**
     * Generates a VBox containing various buttons and controls for the GUI.
     * @return The VBox containing the buttons and controls.
     */
    public VBox generateButtons(){
        ButtonBar buttonBar = new ButtonBar();
        ToggleGroup toggleGroup = new ToggleGroup();
        // Create "Rotate" toggle button
        rotateButton = new ToggleButton("Rotate");
        rotateButton.setMaxWidth(30);
        // Create "Move" toggle button
        moveButton = new ToggleButton("Move");
        moveButton.setMaxWidth(30);
        // Create "Configure" toggle button with an event handler to toggle all objects
        selectButton = new ToggleButton("Configure");
        selectButton.setMaxWidth(30);
        selectButton.setOnAction(clicked -> toggleAllObjects());
        // Create "Add Square" toggle button with an event handler to generate a square box
        ToggleButton squareButton = new ToggleButton("Add Square");
        squareButton.setMaxWidth(50);
        squareButton.setOnAction(clicked -> {
            new ConfigBox().generateSquareBox();
            toggleGroup.selectToggle(null);
        });
        // Create "Add Sphere" toggle button with an event handler to generate a sphere
        ToggleButton sphereButton = new ToggleButton("Add Sphere");
        sphereButton.setMaxWidth(50);
        sphereButton.setOnAction(clicked -> {
            new ConfigBox().generateSphereBox();
            toggleGroup.selectToggle(null);
        });
        // Create "Add Sphere" toggle button with an event handler to generate a sphere
        ToggleButton deleteButton = new ToggleButton("Delete");
        deleteButton.setMaxWidth(30);
        deleteButton.setOnAction(clicked -> {
            deleteSelected();
            toggleGroup.selectToggle(null);
        });
        // Create "Add Sphere" toggle button with an event handler to generate a sphere
        ToggleButton duplicateButton = new ToggleButton("Duplicate");
        duplicateButton.setMaxWidth(50);
        duplicateButton.setOnAction(clicked -> {
            duplicateSelected();
            toggleGroup.selectToggle(null);
        });
        // Create "Drag Camera" button with an event handler to toggle camera drag/rotate
        Button dragCameraButton = new Button("Drag Camera");
        dragCameraButton.setOnAction(clicked ->{
            if(!drag){
                dragCameraButton.setText("Rotate Camera");
                drag = true;
            }else {
                dragCameraButton.setText("Drag Camera");
                drag = false;
            }
        });
        dragCameraButton.setMaxWidth(100);
        // Add toggle buttons to a toggle group
        rotateButton.setToggleGroup(toggleGroup);
        moveButton.setToggleGroup(toggleGroup);
        selectButton.setToggleGroup(toggleGroup);
        squareButton.setToggleGroup(toggleGroup);
        deleteButton.setToggleGroup(toggleGroup);
        duplicateButton.setToggleGroup(toggleGroup);
        sphereButton.setToggleGroup(toggleGroup);
        // Create "Reset Camera" button with an event handler to reset camera angles
        Button resetCameraButton = new Button("Reset Camera");
        resetCameraButton.setOnAction(clicked ->{
            angleX.set(0);
            angleY.set(0);
        });
        resetCameraButton.setMaxWidth(90);
        // Create "Hide Lights" button with an event handler to toggle light visibility
        Button hideLightButton = new Button("Hide Lights");
        hideLightButton.setOnAction(clicked ->{
            if(lightManager.isLightsVisable()) {
                lightManager.hideLights();
                hideLightButton.setText("Show Lights");
            }
            else {
                lightManager.showLights();
                hideLightButton.setText("Hide Lights");
            }
        });
        hideLightButton.setMaxWidth(50);
        // Create "Disable Lighting" button with an event handler to toggle lighting
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
        // Set button data for the Apply button in the ButtonBar
        ButtonBar.setButtonData(squareButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(sphereButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(rotateButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(moveButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(selectButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(deleteButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(duplicateButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(resetCameraButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(dragCameraButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(hideLightButton, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(disableLightButton, ButtonBar.ButtonData.APPLY);
        // Add buttons to the ButtonBar
        buttonBar.getButtons().addAll(sphereButton,squareButton, duplicateButton, deleteButton,rotateButton,selectButton,moveButton,hideLightButton,disableLightButton,dragCameraButton,resetCameraButton);
        // Set font size for all buttons in the ButtonBar
        buttonBar.getButtons().forEach(button ->setFont(button));
        // Create an empty padding HBox
        HBox emptyPadding = new HBox();
        emptyPadding.setPrefWidth(7);
        // Create a VBox to contain the ButtonBar
        VBox vBox = new VBox(emptyPadding,buttonBar);
        vBox.setPadding(new Insets(7));
        vBox.setStyle("-fx-background-color: GREY");
        return vBox;
    }

    /**
     * Checks if the "Rotate" button is selected.
     * @return true if the "Rotate" button is selected, false otherwise.
     */
    public boolean rotateSelected(){
        return rotateButton.isSelected();
    }

    /**
     * Checks if the "Move" button is selected.
     * @return true if the "Move" button is selected, false otherwise.
     */
    public boolean moveSelected(){
        return moveButton.isSelected();
    }

    /**
     * Generates the 3D scene with objects and controls.
     * @return The JavaFX Scene containing the 3D scene and controls.
     */
    public Scene generateScene(){
        camera.setTranslateZ(-200);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        // Create a 3x3x3 grid of boxes
        for(int x =-1; x<2;x++){
            for(int y=-1; y<2;y++){
                for (int z=-1;z<2;z++) {
                    Box3D box = createBox(5, 5, 5, x*10, y*10, z*10);
                    if(x==0 && y==0 && z ==0)
                        box.applyMaterial("Cobble");
                    else
                        box.applyMaterial("Wood");
                }
            }
        }
        int height = 1000;
        int width = 1000;
        scene = new SubScene(group, width, height,true, SceneAntialiasing.DISABLED);
        scene.setCamera(camera);
        scene.setFill(Color.GREY);
        setupCameraControl();
        // Generate control buttons and add them to the VBox
        VBox vBox = generateButtons();
        vBox.getChildren().add(scene);
        vBox.setAlignment(Pos.TOP_LEFT);
        // Create a lightbox sphere and add it to the light manager
        Sphere3D sphere = createSphere(4,38,-19,-31);
        sphere.applyMaterial("Light Box");
        lightManager.addLight(sphere);
        group.getChildren().add(new AmbientLight(Color.rgb(192,192,192,0.01)));
        // Create the main JavaFX Scene
        Scene mainScene = new Scene(vBox,1000,1000);
        mainScene.setFill(Color.GREY);
        return mainScene;
    }

    /**
     * Scrolls the 3D scene along the Z-axis by a specified movement amount.
     * @param movement The amount by which to scroll the scene along the Z-axis.
     */
    public void scroll(double movement){
        group.translateZProperty().set(group.getTranslateZ() + movement*0.1);
    }

    /**
     * Rotates the selected objects around the X-axis by a specified angle.
     * @param angle The angle (in degrees) by which to rotate the objects around the X-axis.
     */
    public void rotateX(double angle){
        selectedObject.forEach(shape-> shape.setRotationX(angle));
    }

    /**
     * Rotates the selected objects around the Y-axis by a specified angle.
     * @param angle The angle (in degrees) by which to rotate the objects around the Y-axis.
     */
    public void rotateY(double angle){
        selectedObject.forEach(shape-> shape.setRotationY(angle));
    }

    /**
     * Rotates the selected objects around the Z-axis by a specified angle.
     * @param angle The angle (in degrees) by which to rotate the objects around the Z-axis.
     */
    public void rotateZ(double angle){
        selectedObject.forEach(shape-> shape.setRotationZ(angle));
    }

    /**
     * Sets the X-coordinate of the selected objects, either absolutely or accumulatively.
     * @param offset The X-coordinate value to set for the objects.
     * @param accumulative If true, the offset is added to the current X-coordinate; if false, it's set absolutely.
     */
    public void setX(double offset,boolean accumulative){
        selectedObject.forEach(shape-> shape.setX(offset,accumulative));
    }

    /**
     * Sets the Y-coordinate of the selected objects, either absolutely or accumulatively.
     * @param offset The Y-coordinate value to set for the objects.
     * @param accumulative If true, the offset is added to the current Y-coordinate; if false, it's set absolutely.
     */
    public void setY(double offset,boolean accumulative){
        selectedObject.forEach(shape-> shape.setY(offset,accumulative));
    }

    /**
     * Sets the Z-coordinate of the selected objects, either absolutely or accumulatively.
     * @param offset The Z-coordinate value to set for the objects.
     * @param accumulative If true, the offset is added to the current Z-coordinate; if false, it's set absolutely.
     */
    public void setZ(double offset, boolean accumulative){
        selectedObject.forEach(shape-> shape.setZ(offset,accumulative));
    }

    /**
     * Sets the dimensions (depth, height, and width) of all selected box objects.
     * @param depth  The depth dimension to set for the boxes.
     * @param height The height dimension to set for the boxes.
     * @param width  The width dimension to set for the boxes.
     */
    public void setAllBoxDimensions(double depth,double height,double width){
        ArrayList<RenderableObject> tempList = new ArrayList<>(selectedObject);
        tempList.forEach(shape-> {
            try {
                Box3D box = (Box3D) shape;
                box.setAllDimensions(depth,height,width);
            }catch (Exception e){e.printStackTrace();}
        });
    }

    /**
     * Sets the dimensions (radius) of all selected sphere objects.
     * @param radius The radius dimension to set for the spheres.
     */
    public void setSphereDimensions(double radius){
        ArrayList<RenderableObject> tempList = new ArrayList<>(selectedObject);
        tempList.forEach(shape-> {
            try {
                Sphere3D sphere = (Sphere3D) shape;
                sphere.setAllDimensions(radius);
            }catch (Exception e){e.printStackTrace();}
        });
    }

    /**
     * Gets the type of the first selected object.
     * @return The type of the first selected object as a string.
     */
    public String selectedType(){
        return selectedObject.get(0).getType();
    }

    /**
     * Sets the material of the selected objects.
     * @param name The name of the material to set on the objects.
     */
    public void setMaterial(String name){
        selectedObject.forEach(shape-> shape.getShape3D().setMaterial(Materials.getInstance().getMaterial(name)));
    }

    /**
     * Gets the first selected object.
     * @return The first selected RenderableObject.
     */
    public RenderableObject getSelectedObject() {
        return selectedObject.get(0);
    }

    /**
     * Adds a JavaFX Node to the 3D scene group.
     * @param node The Node to add to the scene.
     */
    public void addNode(Node node){
        group.getChildren().add(node);
    }

    /**
     * Removes a JavaFX Node from the 3D scene group.
     * @param node The Node to remove from the scene.
     */
    public void removeNode(Node node){
        group.getChildren().remove(node);
    }

    /**
     * Adds a light source represented by a RenderableObject to the light manager.
     * @param object The RenderableObject representing the light source to add.
     */
    public void addLight(RenderableObject object){
        lightManager.addLight(object);
    }

    /**
     * Removes a light source represented by a RenderableObject from the light manager.
     * @param object The RenderableObject representing the light source to remove.
     */
    public void removeLight(RenderableObject object){
        lightManager.removeLight(object);
    }

    /**
     * Toggles the selection of all objects (selects if unselected, and vice versa).
     */
    public void toggleAllObjects(){
        ArrayList<RenderableObject> tempObjects = new ArrayList<>(selectedObject);
        tempObjects.forEach(shape -> renderableObjectClicked(shape));
    }

    /**
     * Removes a RenderableObject from the selected objects, the scene group, and the light manager.
     * @param object The RenderableObject to remove.
     */
    public void removeObject(RenderableObject object){
        selectedObject.remove(object);
        group.getChildren().remove(object.shape);
        group.getChildren().remove(object.outline);
        group.getChildren().remove(object.pointLight);
    }

    /**
     * Adds a RenderableObject to the selected objects, the scene group, and the light manager.
     * @param object The RenderableObject to add.
     */
    public void addObject(RenderableObject object){
        selectedObject.add(object);
        group.getChildren().add(object.shape);
        group.getChildren().add(object.outline);
        group.getChildren().add(object.pointLight);
    }
}