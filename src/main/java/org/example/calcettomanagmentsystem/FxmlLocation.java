package org.example.calcettomanagmentsystem;

public enum FxmlLocation {

	ROUNDTOURNAMET("/org/example/calcettomanagmentsystem/fxml/view/roundTournament-view.fxml"),
	STARTTOURNAMET("/org/example/calcettomanagmentsystem/fxml/view/startTournament-view.fxml"),

	CREATETOURNAMENT("/org/example/calcettomanagmentsystem/fxml/createTournament-view.fxml"),
	SELECTTOURNAMENT("/org/example/calcettomanagmentsystem/fxml/selectTournament-view.fxml");

	private final String fxmlPath;

	FxmlLocation(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}

	@Override
	public String toString() {
		return fxmlPath;
	}
}
