package org.example.calcettomanagmentsystem;

public enum FxmlLocation {

	MAINVIEW("/org/example/calcettomanagmentsystem/fxml/main-view.fxml"),
	HELLOVIEW("/org/example/calcettomanagmentsystem/fxml/hello-view.fxml");

	private final String fxmlPath;

	private FxmlLocation(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}

	@Override
	public String toString() {
		return fxmlPath;
	}
}
