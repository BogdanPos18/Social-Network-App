module com.example.lab6 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.lab6 to javafx.fxml;
    opens com.example.lab6.Controller to javafx.fxml;
    opens com.example.lab6.Domain.models to javafx.fxml, javafx.base;
    exports com.example.lab6;


}