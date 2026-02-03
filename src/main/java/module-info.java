module org.example.calcettomanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires annotations;

    opens org.example.calcettomanagmentsystem to javafx.fxml;
    exports org.example.calcettomanagmentsystem;
    exports org.example.calcettomanagmentsystem.controller;
    opens org.example.calcettomanagmentsystem.controller to javafx.fxml;
}