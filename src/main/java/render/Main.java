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
                    case RIGHT:
                    case D:
                        Drawing.rotateX(10);
                        break;
                    case LEFT:
                    case A:
                        Drawing.rotateX(-10);
                        break;
                    case UP:
                    case W:
                        Drawing.rotateY(10);
                        break;
                    case DOWN:
                    case S:
                        Drawing.rotateY(-10);
                        break;
                    case E:
                        Drawing.rotateZ(10);
                        break;
                    case Q:
                        Drawing.rotateZ(-10);
                        break;
                }
            } else if (Drawing.moveSelected()) {
                switch (key.getCode()) {
                    case RIGHT:
                    case D:
                        Drawing.setX(1);
                        break;
                    case LEFT:
                    case A:
                        Drawing.setX(-1);
                        break;
                    case UP:
                    case W:
                        Drawing.setY(1);
                        break;
                    case DOWN:
                    case S:
                        Drawing.setY(-1);
                        break;
                    case E:
                        Drawing.setZ(1);
                        break;
                    case Q:
                        Drawing.setZ(-1);
                        break;
                }
            }
        });
        stage.addEventHandler(ScrollEvent.SCROLL, scroll -> Drawing.scroll(scroll.getDeltaY()));
    }

    public static void main(String[] args) {
        launch();
    }
}