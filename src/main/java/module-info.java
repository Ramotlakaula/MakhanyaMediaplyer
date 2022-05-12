module com.example.makhanyavideo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.makhanyavideo to javafx.fxml;
    exports com.example.makhanyavideo;
}