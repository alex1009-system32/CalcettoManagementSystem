package org.example.calcettomanagmentsystem;

/**
 * Bootstrap entry point for the application.
 * <p>
 * This class serves as the main entry point to launch the JavaFX application
 * without requiring the {@link App} class to be the direct target, which
 * avoids issues with modules and classpath in certain environments.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class Launcher {
	/**
	 * Main entry point for the application.
	 * <p>
	 * This method delegates the startup process to the JavaFX {@link App} class.
	 * </p>
	 *
	 * @param args Command-line arguments passed during application startup.
	 */
	public static void main(String[] args) {
		javafx.application.Application.launch(App.class, args);
	}
}
