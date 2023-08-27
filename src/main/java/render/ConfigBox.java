package render;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape3D;
import javafx.stage.Stage;

import java.io.File;

public class ConfigBox {
    public static void generateBox(){

        VBox vBox = new VBox( 10);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        vBox.setPrefWidth(200);

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
        Button button = new Button("Rotate");
        button.setOnAction(click -> {
            Drawing.getInstance().rotateY(Double.parseDouble(ytextField.getText()));
            Drawing.getInstance().rotateZ(Double.parseDouble(ztextField.getText()));
            Drawing.getInstance().rotateX(Double.parseDouble(xtextField.getText()));
        });
        vBox.getChildren().add(button);

        Label labelM = new Label("Edit Location");
        vBox.getChildren().add(labelM);
        TilePane xaxisMPane = new TilePane();
        TilePane yaxisMPane = new TilePane();
        TilePane zaxisMPane = new TilePane();
        Label xaxisMLabel = new Label("X Axis Position");
        Label yaxisMLabel = new Label("Y Axis Position");
        Label zaxisMLabel = new Label("Z Axis Position");
        Shape3D model = Drawing.getInstance().getSelectedObject().getShape3D();
        TextField xMtextField = new TextField(String.valueOf(model.getTranslateX()));
        TextField yMtextField = new TextField(String.valueOf(model.getTranslateY()));
        TextField zMtextField = new TextField(String.valueOf(model.getTranslateZ()));
        xaxisMPane.getChildren().add(xMtextField);
        xaxisMPane.getChildren().add(xaxisMLabel);
        yaxisMPane.getChildren().add(yMtextField);
        yaxisMPane.getChildren().add(yaxisMLabel);
        zaxisMPane.getChildren().add(zMtextField);
        zaxisMPane.getChildren().add(zaxisMLabel);
        vBox.getChildren().add(xaxisMPane);
        vBox.getChildren().add(yaxisMPane);
        vBox.getChildren().add(zaxisMPane);
        Button buttonMove = new Button("Move");
        buttonMove.setOnAction(click -> {
            model.setTranslateZ(Double.parseDouble(zMtextField.getText()));
            model.setTranslateY(Double.parseDouble(yMtextField.getText()));
            model.setTranslateX(Double.parseDouble(xMtextField.getText()));
        });
        vBox.getChildren().add(buttonMove);

        Scene scene = new Scene(vBox,200,520);
        Stage stage = new Stage();
        stage.setTitle("Config shape");
        stage.setScene(scene);
        stage.show();
    }
}
