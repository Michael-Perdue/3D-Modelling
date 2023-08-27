module com.example.render {
    requires javafx.controls;
    requires javafx.fxml;


    opens modelling to javafx.fxml;
    exports modelling;
}