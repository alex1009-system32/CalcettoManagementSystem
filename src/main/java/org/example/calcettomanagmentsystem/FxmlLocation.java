package org.example.calcettomanagmentsystem;

public enum FxmlLocation {

	CREATETOURNAMENT("/org/example/calcettomanagmentsystem/fxml/createTournament-view.fxml"),
	SELECTTOURNAMENT("/org/example/calcettomanagmentsystem/fxml/selectTournament-view.fxml"),
	VIEWTOURNAMENT("/org/example/calcettomanagmentsystem/fxml/viewTournament-view.fxml");

	private final String fxmlPath;

	FxmlLocation(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}

	@Override
	public String toString() {
		return fxmlPath;
	}
}
