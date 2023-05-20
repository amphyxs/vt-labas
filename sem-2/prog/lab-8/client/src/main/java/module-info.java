module lab8.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires serialized.objects.lib;
    requires java.sql;

    opens lab8.client to javafx.fxml;
    exports lab8.client;
    exports lab8.client.view.fxapp;
    opens lab8.client.view.fxapp to javafx.fxml;
}