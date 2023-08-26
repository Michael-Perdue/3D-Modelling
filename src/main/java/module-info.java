module com.example.render {
    requires javafx.controls;
    requires javafx.fxml;


    opens render to javafx.fxml;
    exports render;
}