module com.zukuk {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.zukuk to javafx.fxml;
    opens com.zukuk.controller to javafx.fxml;
    exports com.zukuk;
}