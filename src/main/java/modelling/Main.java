package modelling;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class Main extends Application {

    /***
     * This function is called on start, gets the Drawing class to generate the scene,then binds a set of keys
     * to call various functions when pressed and then shows the stage with the scene in it.
     * @param stage the stage which the program opens upon launch
     */
    @Override
    public void start(Stage stage){
        stage.setTitle("3D modelling");
        DrawingGUI drawingGUI = DrawingGUI.getInstance();
        // Generates the scene
        Scene scene = drawingGUI.generateScene();
        //sets the css for the whole scene
        scene.getStylesheets().add(Main.class.getResource("/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        // Binds a series of keys with the relevant function to be called upon pressing of the key
        stage.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            // Defines which functions should be called when a key is pressed in rotate mode
            if(drawingGUI.rotateSelected()) {
                switch (key.getCode()) {
                    case RIGHT, D -> drawingGUI.rotateY(1);
                    case LEFT, A -> drawingGUI.rotateY(-1);
                    case UP, W -> drawingGUI.rotateX(1);
                    case DOWN, S -> drawingGUI.rotateX(-1);
                    case E -> drawingGUI.rotateZ(1);
                    case Q -> drawingGUI.rotateZ(-1);
                }
            // Defines which functions should be called when a key is pressed in move mode
            } else if (drawingGUI.moveSelected()) {
                switch (key.getCode()) {
                    case RIGHT, D -> drawingGUI.setX(1,true);
                    case LEFT, A -> drawingGUI.setX(-1,true);
                    case UP, W -> drawingGUI.setY(-1,true);
                    case DOWN, S -> drawingGUI.setY(1,true);
                    case E -> drawingGUI.setZ(1,true);
                    case Q -> drawingGUI.setZ(-1,true);
                }
            }
        });
        // Binds the scroll wheel to call drawing.scroll with the scrolls delta upon the user scrolling
        stage.addEventHandler(ScrollEvent.SCROLL, scroll -> drawingGUI.scroll(scroll.getDeltaY()));
    }

    public static void main(String[] args) {
        launch();
    }
}