package org.example.calcettomanagmentsystem.shared.navigation;

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

    ADD_PLAYER("/org/example/calcettomanagmentsystem/fxml/modal/addPlayer.fxml"),
    MATCH_MODAL("/org/example/calcettomanagmentsystem/fxml/modal/matchModal.fxml"),
    TEAM_POINT("/org/example/calcettomanagmentsystem/fxml/modal/teamPoint.fxml"),

    ROUND_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/roundTournament-view.fxml"),
    START_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/startTournament-view.fxml"),

    CREATE_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/createTournament-view.fxml"),
    SELECT_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/selectTournament-view.fxml");

	private final String fxmlPath;

	FXMLNavigator(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}

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
