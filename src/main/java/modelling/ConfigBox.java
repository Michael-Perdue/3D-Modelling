package modelling;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ConfigBox {

    // Stores dimensions of shape text fields to be able to get the value of them when a button is clicked
    private TextField radiusTextField, depthTextField, heightTextField,widthTextField;
    // Stores the material the user has selected when configureing a model
    private String material = "";
    // Stores the axis location text fields to be able to get the value of them when a button is clicked
    private TextField xMtextField, yMtextField,zMtextField;
    private RadioButton lightEnabledButton;
    private Stage stage = new Stage();

    /**
     * Adds the option to set the rotation of the object being configured to the VBox,
     * which is given to it through adding various text fields, labels and buttons.
     * @param vBox the VBox you want to add rotation options to
     * @param button flag for if you want a button added the vbox which updates the objects rotation on click
     */
    private void addRotation(VBox vBox, boolean button){
        Label label = new Label("Edit Rotation");
        vBox.getChildren().add(label);
        TilePane xaxisPane = new TilePane();
        TilePane yaxisPane = new TilePane();
        TilePane zaxisPane = new TilePane();
        Label xaxisLabel = new Label("X Axis Rotation");
        Label yaxisLabel = new Label("Y Axis Rotation");
        Label zaxisLabel = new Label("Z Axis Rotation");
        TextField xtextField = new TextField("0");
        TextField ytextField = new TextField("0");
        TextField ztextField = new TextField("0");
        xaxisPane.getChildren().add(xtextField);
        xaxisPane.getChildren().add(xaxisLabel);
        yaxisPane.getChildren().add(ytextField);
        yaxisPane.getChildren().add(yaxisLabel);
        zaxisPane.getChildren().add(ztextField);
        zaxisPane.getChildren().add(zaxisLabel);
        vBox.getChildren().add(xaxisPane);
        vBox.getChildren().add(yaxisPane);
        vBox.getChildren().add(zaxisPane);
        if(button) {
            Button buttonRotate = new Button("Rotate");
            buttonRotate.setOnAction(click -> {
                DrawingGUI.getInstance().rotateY(Double.parseDouble(ytextField.getText()));
                DrawingGUI.getInstance().rotateZ(Double.parseDouble(ztextField.getText()));
                DrawingGUI.getInstance().rotateX(Double.parseDouble(xtextField.getText()));
            });
            vBox.getChildren().add(buttonRotate);
        }
    }

    /**
     * Adds the option to set the location of the object being configured to the VBox,
     * which is given to it through adding various text fields, labels and buttons.
     * @param vBox the VBox you want to add rotation options to
     * @param button flag for if you want a button added the vbox which updates the objects location on click
     */
    private void addLocation(VBox vBox, boolean button){
        Label labelM = new Label("Set Location");
        vBox.getChildren().add(labelM);
        TilePane xaxisMPane = new TilePane();
        TilePane yaxisMPane = new TilePane();
        TilePane zaxisMPane = new TilePane();
        Label xaxisMLabel = new Label("X Axis Position");
        Label yaxisMLabel = new Label("Y Axis Position");
        Label zaxisMLabel = new Label("Z Axis Position");
        Shape3D model;
        try{
            model = DrawingGUI.getInstance().getSelectedObject().getShape3D();
        }catch (Exception e){
            model = new Box(0,0,0);
        }
        xMtextField = new TextField(String.valueOf(model.getTranslateX()));
        yMtextField = new TextField(String.valueOf(model.getTranslateY()));
        zMtextField = new TextField(String.valueOf(model.getTranslateZ()));
        xaxisMPane.getChildren().add(xMtextField);
        xaxisMPane.getChildren().add(xaxisMLabel);
        yaxisMPane.getChildren().add(yMtextField);
        yaxisMPane.getChildren().add(yaxisMLabel);
        zaxisMPane.getChildren().add(zMtextField);
        zaxisMPane.getChildren().add(zaxisMLabel);
        vBox.getChildren().add(xaxisMPane);
        vBox.getChildren().add(yaxisMPane);
        vBox.getChildren().add(zaxisMPane);
        if(button) {
            Button buttonMove = new Button("Move");
            buttonMove.setOnAction(click -> {
                DrawingGUI.getInstance().setZ(Double.parseDouble(zMtextField.getText()), false);
                DrawingGUI.getInstance().setY(Double.parseDouble(yMtextField.getText()), false);
                DrawingGUI.getInstance().setX(Double.parseDouble(xMtextField.getText()), false);
            });
            vBox.getChildren().add(buttonMove);
        }
    }

    /**
     * Adds the option to set the dimensions of the box object being configured to the VBox,
     * which is given to it through adding various text fields, labels and buttons.
     * @param vBox the VBox you want to add rotation options to
     * @param button flag for if you want a button added the vbox which updates the boxes dimension on click
     */
    private void addBoxDimensions(VBox vBox,boolean button){
        Label labelM = new Label("Set Dimensions");
        vBox.getChildren().add(labelM);
        TilePane depthPane = new TilePane();
        TilePane heightPane = new TilePane();
        TilePane widthPane = new TilePane();
        Label depthLabel = new Label("Depth");
        Label heightLabel = new Label("Height");
        Label widthLabel = new Label("Width");
        Box model;
        try{
            model = (Box) DrawingGUI.getInstance().getSelectedObject().getShape3D();
        }catch (Exception e){
            model = new Box(0,0,0);
        }
        depthTextField = new TextField(String.valueOf(model.getDepth()));
        heightTextField = new TextField(String.valueOf(model.getHeight()));
        widthTextField  = new TextField(String.valueOf(model.getWidth()));
        depthPane.getChildren().add(depthTextField);
        depthPane.getChildren().add(depthLabel);
        heightPane.getChildren().add(heightTextField);
        heightPane.getChildren().add(heightLabel);
        widthPane.getChildren().add(widthTextField);
        widthPane.getChildren().add(widthLabel);
        vBox.getChildren().add(depthPane);
        vBox.getChildren().add(heightPane);
        vBox.getChildren().add(widthPane);
        if(button) {
            Button buttonDimension = new Button("Change Dimensions");
            buttonDimension.setOnAction(click -> {
                DrawingGUI.getInstance().setAllBoxDimensions(
                        Double.parseDouble(widthTextField.getText()),
                        Double.parseDouble(heightTextField.getText()),
                        Double.parseDouble(depthTextField.getText()));
                stage.close();
            });
            vBox.getChildren().add(buttonDimension);
        }
    }

    /**
     * Adds the option to set the dimensions of the sphere object being configured to the VBox,
     * which is given to it through adding various text fields, labels and buttons.
     * @param vBox the VBox you want to add rotation options to
     * @param button flag for if you want a button added the vbox which updates the spheres dimension on click
     */
    private void addSphereDimensions(VBox vBox,boolean button) {
        Label labelM = new Label("Set Dimensions");
        vBox.getChildren().add(labelM);
        TilePane radiusPane = new TilePane();
        Label radiusLabel = new Label("Radius");
        Sphere model;
        try {
            model = (Sphere) DrawingGUI.getInstance().getSelectedObject().getShape3D();
        } catch (Exception e) {
            model = new Sphere(0);
        }
        radiusTextField = new TextField(String.valueOf(model.getRadius()));
        radiusPane.getChildren().add(radiusTextField);
        radiusPane.getChildren().add(radiusLabel);
        vBox.getChildren().add(radiusPane);
        if (button) {
            Button buttonDimension = new Button("Change Dimension");
            buttonDimension.setOnAction(click -> {
                DrawingGUI.getInstance().setSphereDimensions(Double.parseDouble(radiusTextField.getText()));
                stage.close();
            });
            vBox.getChildren().add(buttonDimension);
        }
    }

    private void addLight(VBox vBox,boolean button) {
        Label labelLight = new Label("Enable/Disable LightBox");
        vBox.getChildren().add(labelLight);
        VBox lightVBox = new VBox();
        lightVBox.setSpacing(10);
        lightEnabledButton = new RadioButton("Enable");
        RadioButton lightDisabledButton = new RadioButton("Disable");
        ToggleGroup toggleGroup = new ToggleGroup();
        lightEnabledButton.setToggleGroup(toggleGroup);
        lightDisabledButton.setToggleGroup(toggleGroup);
        lightVBox.getChildren().add(lightEnabledButton);
        lightVBox.getChildren().add(lightDisabledButton);
        vBox.getChildren().add(lightVBox);
        if (button) {
            RenderableObject renderableObject = DrawingGUI.getInstance().getSelectedObject();
            PointLight light = renderableObject.getPointLight();
            if (light == null)
                toggleGroup.selectToggle(lightDisabledButton);
            else
                toggleGroup.selectToggle(lightEnabledButton);
            lightEnabledButton.setOnAction(clicked -> {
                    DrawingGUI.getInstance().addLight(renderableObject);
            });
            lightDisabledButton.setOnAction(clicked ->{
                DrawingGUI.getInstance().removeLight(renderableObject);
            });
        }else {
            toggleGroup.selectToggle(lightDisabledButton);
        }
    }

    /**
     * Adds the option to set the material of the object being configured to the VBox,
     * which is given to it through adding various text fields, labels and buttons.
     * @param vBox the VBox you want to add rotation options to
     * @param button flag for if you want the radio button on the vbox to instantly update the objects material on click
     */
    public void addMaterials(VBox vBox,boolean button){
        Label materialLabel = new Label("Set Material");
        vBox.getChildren().add(materialLabel);
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioButtonNone = new RadioButton("None");
        radioButtonNone.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(radioButtonNone);
        vBox.getChildren().add(radioButtonNone);
        RenderableObject renderableObject;
        if(button)
            renderableObject = DrawingGUI.getInstance().getSelectedObject();
        else
            renderableObject = null;
        Materials.getInstance().getAllMaterials().forEach((name, material) -> {
            RadioButton radioButton = new RadioButton(name);
            radioButton.setToggleGroup(toggleGroup);
            vBox.getChildren().add(radioButton);
            if(button){
                radioButton.setOnAction(clicked-> DrawingGUI.getInstance().setMaterial(name));
                if(renderableObject.getMaterial().equals(name))
                    toggleGroup.selectToggle(radioButton);
            }else {
                radioButton.setOnAction(clicked->this.material = name);
            }
        });
    }


    public void generateSquareBox(){
        VBox vBox = new VBox( 10);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        vBox.setPrefWidth(200);
        addBoxDimensions(vBox,false);
        addLocation(vBox,false);
        addMaterials(vBox,false);
        addLight(vBox,false);
        Button buttonCreate = new Button("Create Box");
        buttonCreate.setOnAction(click -> {
            Box3D box = DrawingGUI.getInstance().createBox(
                    Double.parseDouble(widthTextField.getText()),
                    Double.parseDouble(heightTextField.getText()),
                    Double.parseDouble(depthTextField.getText()),
                    Double.parseDouble(xMtextField.getText()),
                    Double.parseDouble(yMtextField.getText()),
                    Double.parseDouble(zMtextField.getText()));
            if(!material.equals("None"))
                box.getShape3D().setMaterial(Materials.getInstance().getMaterial(material));
            if(lightEnabledButton.isSelected())
                DrawingGUI.getInstance().addLight(box);
            stage.close();
        });
        vBox.getChildren().add(buttonCreate);
        Scene scene = new Scene(vBox,370,580);
        scene.getStylesheets().add(ConfigBox.class.getResource("/main.css").toExternalForm());
        stage.setTitle("Create shape");
        stage.setScene(scene);
        stage.show();
    }

    public void generateSphereBox(){
        VBox vBox = new VBox( 10);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        vBox.setPrefWidth(200);
        addSphereDimensions(vBox,false);
        addLocation(vBox,false);
        addMaterials(vBox,false);
        addLight(vBox,false);
        Button buttonCreate = new Button("Create Sphere");
        buttonCreate.setOnAction(click -> {
            Sphere3D sphere = DrawingGUI.getInstance().createSphere(
                    Double.parseDouble(radiusTextField.getText()),
                    Double.parseDouble(xMtextField.getText()),
                    Double.parseDouble(yMtextField.getText()),
                    Double.parseDouble(zMtextField.getText()));
            if(!material.equals("None"))
                sphere.getShape3D().setMaterial(Materials.getInstance().getMaterial(material));
            if(lightEnabledButton.isSelected())
                DrawingGUI.getInstance().addLight(sphere);
        });
        vBox.getChildren().add(buttonCreate);
        Scene scene = new Scene(vBox,370,500);
        scene.getStylesheets().add(ConfigBox.class.getResource("/main.css").toExternalForm());
        stage.setTitle("Create shape");
        stage.setScene(scene);
        stage.show();
    }

    public void generateBox(){
        double vboxHeight = 660;
        VBox vBox = new VBox( 10);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        vBox.setPrefWidth(200);
        addRotation(vBox,true);
        addLocation(vBox,true);
        if(DrawingGUI.getInstance().selectedType().equals("box")) {
            addBoxDimensions(vBox, true);
            vboxHeight += 180;
        }
        if(DrawingGUI.getInstance().selectedType().equals("sphere")) {
            addSphereDimensions(vBox, true);
            vboxHeight += 80;
        }
        addLight(vBox,true);
        addMaterials(vBox,true);
        Scene scene = new Scene(vBox,370,vboxHeight);
        scene.getStylesheets().add(ConfigBox.class.getResource("/main.css").toExternalForm());
        stage.setOnCloseRequest(close -> DrawingGUI.getInstance().toggleAllObjects());
        stage.setTitle("Config shape");
        stage.setScene(scene);
        stage.show();
    }


}
