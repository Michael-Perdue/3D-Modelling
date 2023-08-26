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
import javafx.stage.Stage;

import java.io.File;

public class ConfigBox {
    public static void generateBox(){
        VBox vBox = new VBox( 4);
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
        Scene scene = new Scene(vBox,200,300);
        Stage stage = new Stage();
        stage.setTitle("Config shape");
        stage.setScene(scene);
        stage.show();
    }
}
