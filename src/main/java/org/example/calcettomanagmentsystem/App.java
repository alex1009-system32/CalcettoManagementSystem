package org.example.calcettomanagmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.shared.navigation.FXMLNavigator;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Entry point for the JavaFX application.
 * <p>
 * This class manages the primary stage, scene transitions, and global application state.
 * It provides a centralized mechanism for navigating between different views
 * identified by {@link FXMLNavigator}.
 * </p>
 *
 * @author Senior Developer
 */
public class App extends Application {
    /**
     * Shared scene instance to allow root swaps without recreating the window.
     */
    private static Scene scene;

    /**
     * Default initial width chosen to match the target layout baseline.
     */
    private final int WIDTH = 1500;
    /**
     * Default initial height chosen to match the target layout baseline.
     */
    private final int HEIGHT = 900;

    /**
     * Minimum width to avoid layout breakage in core views.
     */
    private final int MIN_WIDTH = 1500;
    /**
     * Minimum height to avoid layout breakage in core views.
     */
    private final int MIN_HEIGHT = 800;

    /**
     * Initial root view so the app can start without controller-driven navigation.
     */
    private static String root = FXMLNavigator.SELECT_TOURNAMENT.getPath();

    /**
     * Changes the current root of the application scene.
     *
     * @param FXMLNavigator The navigator entry for the target view.
     * @throws RuntimeException If the FXML resource cannot be loaded.
     */
    public static void setRoot(@NotNull FXMLNavigator FXMLNavigator) {
        try {
            scene.setRoot(loadFXML(FXMLNavigator.getPath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to change scene root to " + FXMLNavigator.getPath(), e);
        }

        root = FXMLNavigator.toString();
    }

    /**
     * Loads an FXML file and returns its parent node.
     *
     * @param fxml The resource path to the FXML file.
     * @return The root {@link Parent} node of the loaded FXML.
     * @throws IOException If the file cannot be read.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    /**
     * Initializes and displays the primary application stage.
     *
     * @param stage The primary stage provided by the JavaFX runtime.
     * @throws IOException If the initial view cannot be loaded.
     */
    @Override
    public void start(@NotNull Stage stage) throws IOException {

        scene = new Scene(loadFXML(root), WIDTH, HEIGHT);

        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);

        stage.setMaximized(false);
        stage.setScene(scene);
        stage.show();
    }
}
