module gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.desktop;
    requires org.burningwave.core;
    opens gui to javafx.fxml, javafx.base;

    exports gui;

}