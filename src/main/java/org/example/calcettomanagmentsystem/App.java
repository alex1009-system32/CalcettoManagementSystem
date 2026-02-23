package org.example.calcettomanagmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class App extends Application {
	private static Scene scene;
	private static Tournament tournament = null;

	private final int WIDTH = 500;
	private final int HEIGHT = 680;

	private static String root = FxmlLocation.SELECTTOURNAMENT.toString();

	public static void setRoot(@NotNull FxmlLocation fxmlLocation) {
		try {
			scene.setRoot(loadFXML(fxmlLocation.toString()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		root = fxmlLocation.toString();
	}

	private static Parent loadFXML(String fxml) throws IOException {

		System.out.println(App.class.getResource(fxml).toString());

		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
		return fxmlLoader.load();
	}

	public static Tournament getTournament() {
		return tournament;
	}

	public static void setTournament(Tournament tournament) {
		App.tournament = tournament;
	}

	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(loadFXML(root), WIDTH, HEIGHT);

		stage.setMinHeight(800);
		stage.setMinWidth(1300);

		stage.setMaximized(true);
		stage.setScene(scene);
		stage.show();
	}
}
