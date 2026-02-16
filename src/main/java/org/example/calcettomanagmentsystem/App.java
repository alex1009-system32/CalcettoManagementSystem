package org.example.calcettomanagmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
	private static Scene scene;

	private final int WIDTH = 500;
	private final int HEIGHT = 680;

	//Just for test Purposes
	private static String currendRoot = FxmlLocation.MAINVIEW.toString();

	public static void setRoot(FxmlLocation fxmlLocation) throws IOException {
		scene.setRoot(loadFXML(fxmlLocation.toString()));
		currendRoot = fxmlLocation.toString();
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
		return fxmlLoader.load();
	}

	@Override
	public void start(Stage stage) throws IOException {

		Scene scene = new Scene(loadFXML(currendRoot), WIDTH, HEIGHT);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();
	}
}
