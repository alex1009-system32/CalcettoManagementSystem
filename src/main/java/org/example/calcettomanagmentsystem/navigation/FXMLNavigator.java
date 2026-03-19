package org.example.calcettomanagmentsystem.navigation;

import org.example.calcettomanagmentsystem.App;

/**
 * Central registry for FXML resources to avoid scattered literal paths.
 * <p>
 * The intent is to make navigation resilient to path changes by keeping
 * all view identifiers in one place.
 * </p>
 */
public enum FXMLNavigator {

    TOURNAMENT_CART("/org/example/calcettomanagmentsystem/fxml/components/tournament-cart-component.fxml"),
    ADD_TOURNAMENT_CART("/org/example/calcettomanagmentsystem/fxml/components/add-tournament-cart-component.fxml"),

    PLAYER_CART("/org/example/calcettomanagmentsystem/fxml/components/player-cart-component.fxml"),

    MATCH_TAP("/org/example/calcettomanagmentsystem/fxml/components/match-pane-component.fxml"),
    MATCH_CART("/org/example/calcettomanagmentsystem/fxml/components/match-cart-component.fxml"),

    ROUND_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/roundTournament-view.fxml"),
	START_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/startTournament-view.fxml"),

	CREATE_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/createTournament-view.fxml"),
	SELECT_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/selectTournament-view.fxml");

	/**
	 * Classpath location of the FXML resource.
	 */
	private final String fxmlPath;

	/**
	 * Binds the enum constant to a concrete FXML path.
	 *
	 * @param fxmlPath classpath-relative resource path
	 */
	FXMLNavigator(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}

	/**
	 * Exposes the FXML path for loader usage.
	 *
	 * @return classpath-relative resource path used by {@link App}
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
