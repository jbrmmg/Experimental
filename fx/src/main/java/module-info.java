module jbr.javafx.expjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens jbr.javafx.expjavafx to javafx.fxml;
    exports jbr.javafx.expjavafx;
    exports jbr.javafx.expjavafx.control;
    opens jbr.javafx.expjavafx.control to javafx.fxml;
}
