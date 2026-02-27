package org.example.calcettomanagmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * JavaFX entry point for the Calcetto Management System.
 * Manages scene navigation and holds the selected tournament state.
 */
public class App extends Application {
	private static Scene scene;
	private static Tournament tournament = null;

	private final int WIDTH = 1300;
	private final int HEIGHT = 800;

	private final int MIN_WIDTH = 1300;
	private final int MIN_HEIGHT = 800;

	private static String root = FxmlLocation.SELECT_TOURNAMENT.getPath();

	/**
	 * Switches the active scene root to the specified FXML.
	 *
	 * @param fxmlLocation target FXML location
	 */
	public static void setRoot(@NotNull FxmlLocation fxmlLocation) {
		try {
			scene.setRoot(loadFXML(fxmlLocation.getPath()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		root = fxmlLocation.toString();
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
		return fxmlLoader.load();
	}

	/**
	 * @return currently selected tournament, or null if none selected
	 */
	public static Tournament getTournament() {
		return tournament;
	}

	/**
	 * Sets the active tournament selection.
	 *
	 * @param tournament selected tournament
	 */
	public static void setTournament(Tournament tournament) {
		App.tournament = tournament;
	}

	@Override
	public void start(Stage stage) throws IOException {

		scene = new Scene(
				loadFXML(root),
				HEIGHT,
				WIDTH);

		stage.setMinHeight(MIN_HEIGHT);
		stage.setMinWidth(MIN_WIDTH);

		stage.setMaximized(true);
		stage.setScene(scene);
		stage.show();
	}
}
