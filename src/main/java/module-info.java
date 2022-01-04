module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires javafx.graphics;
    requires org.xerial.sqlitejdbc;
    requires java.sql;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}