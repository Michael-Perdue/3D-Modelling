package modelling;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.stage.Stage;

public class ConfigBox {

    private TextField depthTextField, heightTextField,widthTextField;
    private TextField xMtextField, yMtextField,zMtextField;

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
                Drawing.getInstance().rotateY(Double.parseDouble(ytextField.getText()));
                Drawing.getInstance().rotateZ(Double.parseDouble(ztextField.getText()));
                Drawing.getInstance().rotateX(Double.parseDouble(xtextField.getText()));
            });
            vBox.getChildren().add(buttonRotate);
        }
    }

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
            model = Drawing.getInstance().getSelectedObject().getShape3D();
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
                Drawing.getInstance().setZ(Double.parseDouble(zMtextField.getText()), false);
                Drawing.getInstance().setY(Double.parseDouble(yMtextField.getText()), false);
                Drawing.getInstance().setX(Double.parseDouble(xMtextField.getText()), false);
            });
            vBox.getChildren().add(buttonMove);
        }
    }

    private void addBoxDimensions(VBox vBox,boolean button){
        Label labelM = new Label("Set Dimensions");
        vBox.getChildren().add(labelM);
        TilePane depthPane = new TilePane();
        TilePane heightPane = new TilePane();
        TilePane widthPane = new TilePane();
        Label depthLabel = new Label("Depth");
        Label heightLabel = new Label("Height");
        Label widthLabel = new Label("Width");
        Shape3D model;
        try{
            model = Drawing.getInstance().getSelectedObject().getShape3D();
        }catch (Exception e){
            model = new Box(0,0,0);
        }
        depthTextField = new TextField(String.valueOf(model.getTranslateX()));
        heightTextField = new TextField(String.valueOf(model.getTranslateY()));
        widthTextField  = new TextField(String.valueOf(model.getTranslateZ()));
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
            Button buttonMove = new Button("Change Dimensions");
            buttonMove.setOnAction(click -> {
                Drawing.getInstance().setZ(Double.parseDouble(widthTextField.getText()), false);
                Drawing.getInstance().setY(Double.parseDouble(heightTextField.getText()), false);
                Drawing.getInstance().setX(Double.parseDouble(depthTextField.getText()), false);
            });
            vBox.getChildren().add(buttonMove);
        }
    }

    public void generateSquareBox(){
        VBox vBox = new VBox( 10);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        vBox.setPrefWidth(200);
        addBoxDimensions(vBox,false);
        addLocation(vBox,false);
        Button buttonCreate = new Button("Create Box");
        Stage stage = new Stage();
        buttonCreate.setOnAction(click -> {
            Drawing.getInstance().createBox(
                    Double.parseDouble(depthTextField.getText()),
                    Double.parseDouble(heightTextField.getText()),
                    Double.parseDouble(widthTextField.getText()),
                    Double.parseDouble(xMtextField.getText()),
                    Double.parseDouble(yMtextField.getText()),
                    Double.parseDouble(zMtextField.getText()));
            stage.close();
        });
        vBox.getChildren().add(buttonCreate);
        Scene scene = new Scene(vBox,200,520);
        stage.setTitle("Create shape");
        stage.setScene(scene);
        stage.show();
    }

    public void generateBox(){

        VBox vBox = new VBox( 10);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        vBox.setPrefWidth(200);
        addRotation(vBox,true);
        addLocation(vBox,true);

        Scene scene = new Scene(vBox,200,520);
        Stage stage = new Stage();
        stage.setTitle("Config shape");
        stage.setScene(scene);
        stage.show();
    }
}
