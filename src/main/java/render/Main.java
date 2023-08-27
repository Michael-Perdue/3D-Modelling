package render;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        stage.setTitle("Hello!");
        Drawing drawing = Drawing.getInstance();
        stage.setScene(drawing.generateScene());
        stage.show();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if(drawing.rotateSelected()) {
                switch (key.getCode()) {
                    case RIGHT, D -> drawing.rotateY(1);
                    case LEFT, A -> drawing.rotateY(-1);
                    case UP, W -> drawing.rotateX(1);
                    case DOWN, S -> drawing.rotateX(-1);
                    case E -> drawing.rotateZ(1);
                    case Q -> drawing.rotateZ(-1);
                }
            } else if (drawing.moveSelected()) {
                switch (key.getCode()) {
                    case RIGHT, D -> drawing.setX(1);
                    case LEFT, A -> drawing.setX(-1);
                    case UP, W -> drawing.setY(-1);
                    case DOWN, S -> drawing.setY(1);
                    case E -> drawing.setZ(1);
                    case Q -> drawing.setZ(-1);
                }
            }
        });
        stage.addEventHandler(ScrollEvent.SCROLL, scroll -> drawing.scroll(scroll.getDeltaY()));
    }

    public static void main(String[] args) {
        launch();
    }
}