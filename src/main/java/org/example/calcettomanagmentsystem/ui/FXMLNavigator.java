package org.example.calcettomanagmentsystem.ui;

/**
 * Central registry for FXML resource paths to ensure type-safe navigation.
 * <p>
 * This enumeration maps logical view identifiers to their corresponding 
 * FXML file locations in the application resources.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public enum FXMLNavigator {
    /** Component for displaying a single tournament card. */
    TOURNAMENT_CART("/org/example/calcettomanagmentsystem/fxml/components/tournament-cart-component.fxml"),
    /** Component for the 'add new tournament' action card. */
    ADD_TOURNAMENT_CART("/org/example/calcettomanagmentsystem/fxml/components/add-tournament-cart-component.fxml"),
    /** Component for displaying a single player card. */
    PLAYER_CART("/org/example/calcettomanagmentsystem/fxml/components/player-cart-component.fxml"),
    /** Main container pane for match listings. */
    MATCH_TAP("/org/example/calcettomanagmentsystem/fxml/components/match-pane-component.fxml"),
    /** Component for displaying a single match card. */
    MATCH_CART("/org/example/calcettomanagmentsystem/fxml/components/match-cart-component.fxml"),
    /** Modal window for adding a new player. */
    ADD_PLAYER("/org/example/calcettomanagmentsystem/fxml/modal/addPlayer.fxml"),
    /** Modal window for viewing match details. */
    MATCH_MODAL("/org/example/calcettomanagmentsystem/fxml/modal/matchModal.fxml"),
    /** Modal window for assigning points to teams. */
    TEAM_POINT("/org/example/calcettomanagmentsystem/fxml/modal/teamPoint.fxml"),
    /** Main view for tournament round management. */
    ROUND_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/roundTournament-view.fxml"),
    /** Initial setup view for starting a tournament. */
    START_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/startTournament-view.fxml"),
    /** View for creating a new tournament. */
    CREATE_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/createTournament-view.fxml"),
    /** View for selecting an existing tournament. */
    SELECT_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/selectTournament-view.fxml");

    /** The internal resource path to the FXML file. */
	private final String fxmlPath;

    /**
     * Constructs a navigator entry with the specified path.
     *
     * @param fxmlPath The resource path.
     */
	FXMLNavigator(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}

    /**
     * Retrieves the resource path for this navigator entry.
     *
     * @return The FXML file path.
     */
	public String getPath() {
		return fxmlPath;
	}

    /**
     * Provides a string representation of the navigation location.
     *
     * @return A string containing the FXML path.
     */
	@Override
	public String toString() {
		return "FxmlLocation{" +
				"fxmlPath='" + fxmlPath + '\'' +
				'}';
	}
}
