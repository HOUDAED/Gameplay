module com.zukuk {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.json;
    requires java.net.http;

    opens com.zukuk to javafx.fxml;
    opens com.zukuk.controller to javafx.fxml;
    opens com.zukuk.model to javafx.fxml;
    opens com.zukuk.service to javafx.fxml;
    exports com.zukuk;
    exports com.zukuk.service;
}