package org.example.calcettomanagmentsystem.ui;

/**
 * Central registry for FXML resource paths to ensure type-safe navigation.
 * <p>
 * This enumeration maps logical view identifiers to their corresponding 
 * FXML file locations in the application resources.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public enum FXMLNavigator {
    /** Component for displaying a single tournament card in the selection view. */
    TOURNAMENT_CART("/org/example/calcettomanagmentsystem/fxml/components/tournament-cart-component.fxml"),
    /** Special card used as an entry point for creating a new tournament. */
    ADD_TOURNAMENT_CART("/org/example/calcettomanagmentsystem/fxml/components/add-tournament-cart-component.fxml"),
    /** Pane displaying the winners and ranking of a completed tournament round. */
    WINNER_PANE("/org/example/calcettomanagmentsystem/fxml/components/winner-pane-component.fxml"),
    /** Card component representing an individual player in the tournament roster. */
    PLAYER_CART("/org/example/calcettomanagmentsystem/fxml/components/player-cart-component.fxml"),
    /** Main container for the match listing within a tournament view. */
    MATCH_TAP("/org/example/calcettomanagmentsystem/fxml/components/match-pane-component.fxml"),
    /** Component representing a single match with its participating teams and status. */
    MATCH_CART("/org/example/calcettomanagmentsystem/fxml/components/match-cart-component.fxml"),
    /** Modal dialog for entering new player information. */
    ADD_PLAYER("/org/example/calcettomanagmentsystem/fxml/modal/addPlayer.fxml"),
    /** Modal window for viewing or editing detailed match information. */
    MATCH_MODAL("/org/example/calcettomanagmentsystem/fxml/modal/matchModal.fxml"),
    /** Modal dialog for recording points earned by teams in a match. */
    TEAM_POINT("/org/example/calcettomanagmentsystem/fxml/modal/teamPoint.fxml"),
    /** Comprehensive view for managing rounds, matches, and team progression. */
    ROUND_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/roundTournament-view.fxml"),
    /** Initial view for configuring and launching a new tournament. */
    START_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/startTournament-view.fxml"),
    /** View containing the form to define new tournament parameters. */
    CREATE_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/createTournament-view.fxml"),
    /** Landing view for selecting from a list of existing tournaments. */
    SELECT_TOURNAMENT("/org/example/calcettomanagmentsystem/fxml/view/selectTournament-view.fxml");

    /** The internal resource path to the FXML file. */
	private final String fxmlPath;

    /**
     * Constructs a navigator entry with the specified FXML resource path.
     *
     * @param fxmlPath The path to the FXML resource relative to the classpath.
     */
	FXMLNavigator(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}

    /**
     * Retrieves the resource path for this navigator entry.
     *
     * @return The FXML file path as a {@link String}.
     */
	public String getPath() {
		return fxmlPath;
	}

    /**
     * Provides a string representation of the navigation location for debugging.
     *
     * @return A descriptive string containing the FXML path.
     */
	@Override
	public String toString() {
		return "FxmlLocation{" +
				"fxmlPath='" + fxmlPath + '\'' +
				'}';
	}
}
