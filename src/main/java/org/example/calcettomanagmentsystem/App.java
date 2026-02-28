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
 * JavaFX entry point that centralizes UI navigation and application-wide state.
 * <p>
 * The intent is to keep view transitions and the currently selected
 * {@link org.example.calcettomanagmentsystem.model.Tournament} in one place,
 * so controllers can switch screens without duplicating bootstrapping logic.
 * </p>
 */
public class App extends Application {
	/**
	 * Shared scene instance to allow root swaps without recreating the window.
	 */
	private static Scene scene;
	/**
	 * Currently selected tournament to be reused across views.
	 */
	private static Tournament tournament = null;

	/**
	 * Default initial width chosen to match the target layout baseline.
	 */
	private final int WIDTH = 1300;
	/**
	 * Default initial height chosen to match the target layout baseline.
	 */
	private final int HEIGHT = 800;

	/**
	 * Minimum width to avoid layout breakage in core views.
	 */
	private final int MIN_WIDTH = 1300;
	/**
	 * Minimum height to avoid layout breakage in core views.
	 */
	private final int MIN_HEIGHT = 800;

	/**
	 * Initial root view so the app can start without controller-driven navigation.
	 */
	private static String root = FxmlLocation.SELECT_TOURNAMENT.getPath();

	/**
	 * Switches the active scene root to the specified view.
	 * <p>
	 * This keeps navigation consistent and allows controllers to request view
	 * changes without owning scene construction details.
	 * </p>
	 *
	 * @param fxmlLocation logical view identifier for the target screen
	 * @throws RuntimeException if the FXML cannot be loaded
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
	 * Exposes the currently selected tournament for downstream screens.
	 *
	 * @return current tournament or {@code null} if no selection exists
	 */
	public static Tournament getTournament() {
		return tournament;
	}

	/**
	 * Captures the tournament choice so other views can access it consistently.
	 *
	 * @param tournament tournament chosen in the selection flow
	 */
	public static void setTournament(Tournament tournament) {
		App.tournament = tournament;
	}

	/**
	 * Bootstraps the primary stage with the initial view and window constraints.
	 *
	 * @param stage primary stage created by JavaFX
	 * @throws IOException if the initial FXML cannot be loaded
	 */
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
