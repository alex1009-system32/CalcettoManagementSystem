package org.example.calcettomanagmentsystem.ui.modal;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.example.calcettomanagmentsystem.ui.components.TournamentCart;
import org.example.calcettomanagmentsystem.ui.controller.modal.MatchPointController;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;

import java.io.IOException;

/**
 * Custom JavaFX component for editing points of a single team in a match.
 * <p>
 * This component is used within the match details modal to provide a per-team score input.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class TeamPoint extends HBox {
    /** The controller managing the score input logic. */
    MatchPointController matchPointController;

    /**
     * Constructs a new TeamPoint component.
     *
     * @param match The {@link Match} context.
     * @param team The {@link Team} to manage points for.
     */
    public TeamPoint(Match match, Team team) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.TEAM_POINT.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            this.matchPointController = loader.getController();

            setCardDetails(match, team);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load TeamPoint component", e);
        }
    }

    /**
     * Initializes the component's controller with required data.
     *
     * @param match The match context.
     * @param team The team data.
     */
    private void setCardDetails(Match match, Team team) {
        if (matchPointController != null) {
            matchPointController.setData(match, team);
        }
    }

}
