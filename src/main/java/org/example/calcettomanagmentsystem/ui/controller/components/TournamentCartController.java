package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.shared.navigation.FXMLNavigator;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

/**
 * Controller for the {@link org.example.calcettomanagmentsystem.ui.components.TournamentCart} component.
 * <p>
 * This class handles the display of tournament details on a card and
 * facilitates navigation to the tournament management views.
 * </p>
 *
 * @author Senior Developer
 */
public class TournamentCartController extends javafx.scene.control.Button {

    /** Label for the tournament name. */
    @FXML
    private Label nameLabel;
    /** Label for the tournament start date. */
    @FXML
    private Label dateLabel;
    /** Label for the tournament duration in days. */
    @FXML
    private Label durationLabel;

    /** The tournament data being displayed. */
    private Tournament tournament;

    /**
     * Populates the controller with tournament data.
     *
     * @param tournament The {@link Tournament} instance.
     */
    public void setData(Tournament tournament) {
        this.tournament = tournament;
        nameLabel.setText(tournament.name());
        dateLabel.setText(tournament.date().toString());
        durationLabel.setText(String.valueOf(tournament.duration()));
    }

    /**
     * Handles the click event for the tournament card.
     * Sets the global active tournament and navigates to the appropriate view
     * based on whether the tournament has already started.
     */
    @FXML
    private void click() {
        ServiceManager.setTournament(tournament);

        if (tournament.currentRound() < 1) {
            App.setRoot(FXMLNavigator.START_TOURNAMENT);
        } else {
            App.setRoot(FXMLNavigator.ROUND_TOURNAMENT);
        }
    }
}
