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
 * @version 0.0
 */
public class Launcher {
	/**
	 * Main method that delegates the launch to the JavaFX {@link App} class.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		javafx.application.Application.launch(App.class, args);
	}
}
