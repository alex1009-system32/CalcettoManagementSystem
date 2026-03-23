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
	requires org.xerial.sqlitejdbc;
	requires java.desktop;
    requires jdk.unsupported;
	requires javafaker;
	requires org.jetbrains.annotations;

    exports org.example.calcettomanagmentsystem;
    exports org.example.calcettomanagmentsystem.ui.controller.components;
    exports org.example.calcettomanagmentsystem.ui.controller.modal;
    exports org.example.calcettomanagmentsystem.ui.controller.view;
    exports org.example.calcettomanagmentsystem.navigation;

    opens org.example.calcettomanagmentsystem to javafx.fxml;
    opens org.example.calcettomanagmentsystem.ui.components to javafx.fxml;
    opens org.example.calcettomanagmentsystem.ui.controller.components to javafx.fxml;
    opens org.example.calcettomanagmentsystem.ui.controller.modal to javafx.fxml;
    opens org.example.calcettomanagmentsystem.ui.controller.view to javafx.fxml;
    opens org.example.calcettomanagmentsystem.navigation to javafx.fxml;
}
