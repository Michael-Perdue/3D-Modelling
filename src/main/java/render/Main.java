package render;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Hello!");
        stage.setScene(Drawing.getScene());
        stage.show();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            switch (key.getCode()){
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
        });
    }

    public static void main(String[] args) {
        launch();
    }
}