package org.example.calcettomanagmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class App extends Application {
	private static Scene scene;

	private static Integer tournamentID = null;

	private final int WIDTH = 500;
	private final int HEIGHT = 680;

	private static String root = FxmlLocation.CREATETOURNAMENT.toString();

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

	public static Integer getTournamentID() {
		return tournamentID;
	}

	public static void setTournamentID(Integer tournamentID) {
		App.tournamentID = tournamentID;
	}

	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(loadFXML(root), WIDTH, HEIGHT);

		stage.setMaximized(true);
		stage.setScene(scene);
		stage.show();

		System.out.println(scene.getWidth());
		System.out.println(scene.getHeight());
	}
}
