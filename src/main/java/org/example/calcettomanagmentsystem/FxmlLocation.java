package org.example.calcettomanagmentsystem;

/**
 * Centralized enum of FXML resource locations used by the application.
 */
public enum FxmlLocation {

	ROUND_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/roundTournament-view.fxml"),
	START_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/startTournament-view.fxml"),

	CREATE_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/createTournament-view.fxml"),
	SELECT_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/selectTournament-view.fxml");

	private final String fxmlPath;

	FxmlLocation(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}

	/**
	 * @return classpath-relative FXML resource path
	 */
	public String getPath() {
		return fxmlPath;
	}

	@Override
	public String toString() {
		return "FxmlLocation{" +
				"fxmlPath='" + fxmlPath + '\'' +
				'}';
	}
}
