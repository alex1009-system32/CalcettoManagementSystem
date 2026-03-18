/**
 * Moduldefinition für die Desktop-Anwendung.
 * <p>
 * Die Abhängigkeiten sind bewusst so gewählt, dass UI, Datenzugriff und
 * Hilfsbibliotheken klar getrennt bleiben und die JPMS-Sichtbarkeit
 * kontrolliert ist.
 * </p>
 */
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
    exports org.example.calcettomanagmentsystem.controller;
    exports org.example.calcettomanagmentsystem.controller.view;
    exports org.example.calcettomanagmentsystem.navigation;

    opens org.example.calcettomanagmentsystem to javafx.fxml;
    opens org.example.calcettomanagmentsystem.components to javafx.fxml;
    opens org.example.calcettomanagmentsystem.controller.components to javafx.fxml;
    opens org.example.calcettomanagmentsystem.controller.view to javafx.fxml;
    opens org.example.calcettomanagmentsystem.controller to javafx.fxml;
    opens org.example.calcettomanagmentsystem.navigation to javafx.fxml;
}
