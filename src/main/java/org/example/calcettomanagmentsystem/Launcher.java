package org.example.calcettomanagmentsystem;

/**
 * Bootstrap entry point to support JavaFX launch in packaging and IDE contexts.
 * <p>
 * Keeps {@link App} free from static {@code main} concerns while preserving
 * standard Java entry semantics.
 * </p>
 */
public class Launcher {
	/**
	 * Delegates to the JavaFX launcher.
	 *
	 * @param args raw process arguments passed to the JavaFX runtime
	 */
	public static void main(String[] args) {
		javafx.application.Application.launch(App.class, args);
	}
}
