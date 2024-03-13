module com.example.osproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.osproject to javafx.fxml;
    exports com.example.osproject;
}