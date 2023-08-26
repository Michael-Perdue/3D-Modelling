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
    public void start(Stage stage) throws IOException {
        stage.setTitle("Hello!");
        stage.setScene(Drawing.generateScene());
        stage.show();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if(Drawing.rotateSelected()) {
                switch (key.getCode()) {
                    case RIGHT, D -> Drawing.rotateY(1);
                    case LEFT, A -> Drawing.rotateY(-1);
                    case UP, W -> Drawing.rotateX(1);
                    case DOWN, S -> Drawing.rotateX(-1);
                    case E -> Drawing.rotateZ(1);
                    case Q -> Drawing.rotateZ(-1);
                }
            } else if (Drawing.moveSelected()) {
                switch (key.getCode()) {
                    case RIGHT, D -> Drawing.setX(1);
                    case LEFT, A -> Drawing.setX(-1);
                    case UP, W -> Drawing.setY(-1);
                    case DOWN, S -> Drawing.setY(1);
                    case E -> Drawing.setZ(1);
                    case Q -> Drawing.setZ(-1);
                }
            }
        });
        stage.addEventHandler(ScrollEvent.SCROLL, scroll -> Drawing.scroll(scroll.getDeltaY()));
    }

    public static void main(String[] args) {
        launch();
    }
}