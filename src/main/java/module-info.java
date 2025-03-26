module com.uno {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.uno to javafx.fxml;
    exports com.uno;
}