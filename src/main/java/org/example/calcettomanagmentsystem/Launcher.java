package org.example.calcettomanagmentsystem;

import javafx.application.Application;
import org.example.calcettomanagmentsystem.connection.SQLiteDB;

import java.io.InputStream;

/**
 * Thin launcher class delegating to the JavaFX Application to support IDEs and packaging.
 */
public class Launcher {
	public static void main(String[] args) {
		javafx.application.Application.launch(App.class, args);
	}
}
