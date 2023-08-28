package modelling;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        stage.setTitle("Hello!");
        Drawing drawing = Drawing.getInstance();
        Scene scene = drawing.generateScene();
        scene.getStylesheets().add(Main.class.getResource("/main.css").toExternalForm());
        stage.setScene(scene);
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
                    case RIGHT, D -> drawing.setX(1,true);
                    case LEFT, A -> drawing.setX(-1,true);
                    case UP, W -> drawing.setY(-1,true);
                    case DOWN, S -> drawing.setY(1,true);
                    case E -> drawing.setZ(1,true);
                    case Q -> drawing.setZ(-1,true);
                }
            }
        });
        stage.addEventHandler(ScrollEvent.SCROLL, scroll -> drawing.scroll(scroll.getDeltaY()));
    }

    public static void main(String[] args) {
        launch();
    }
}