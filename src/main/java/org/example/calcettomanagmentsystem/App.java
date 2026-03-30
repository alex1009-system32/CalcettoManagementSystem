package org.example.calcettomanagmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;
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
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class App extends Application {
    /** The shared {@link Scene} instance used for switching between different views. */
    private static Scene scene;

    /** The default initial width of the application window in pixels. */
    private final int WIDTH = 1500;
    /** The default initial height of the application window in pixels. */
    private final int HEIGHT = 900;

    /** The minimum allowed width of the application window to ensure layout stability. */
    private final int MIN_WIDTH = 1500;
    /** The minimum allowed height of the application window to ensure layout stability. */
    private final int MIN_HEIGHT = 800;

    /** The current root FXML resource path as a {@link String}. */
    private static String root = FXMLNavigator.SELECT_TOURNAMENT.getPath();

    /**
     * Changes the current root of the application scene to the specified FXML view.
     * <p>
     * This method facilitates type-safe navigation by using the {@link FXMLNavigator} enum.
     * </p>
     *
     * @param FXMLNavigator The navigator entry identifying the target FXML resource.
     * @throws RuntimeException If the FXML resource cannot be loaded or parsed.
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
     * Initializes the application's resources before the UI is launched.
     * <p>
     * This method is called by the JavaFX launcher thread after the
     * Application instance is constructed, but before the {@link #start}
     * method is invoked. It is used here to initialize the database
     * connection and schema via {@link ServiceManager}.
     * </p>
     *
     * @throws Exception if the database initialization fails or
     * resources cannot be loaded.
     */
    @Override
    public void init() throws Exception {
        System.out.println("Loading init");
        ServiceManager.initDB();
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
