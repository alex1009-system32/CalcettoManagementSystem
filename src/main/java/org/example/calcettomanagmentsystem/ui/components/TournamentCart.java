package org.example.calcettomanagmentsystem.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.example.calcettomanagmentsystem.ui.controller.components.TournamentCartController;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.shared.navigation.FXMLNavigator;

import java.io.IOException;

/**
 * Custom JavaFX component representing a tournament card.
 * <p>
 * This component displays key tournament metrics (duration, rounds, team size)
 * and allows the user to select the tournament for management.
 * </p>
 *
 * @author Senior Developer
 */
public class TournamentCart extends Button {
    /** The controller managing the visual elements of this tournament card. */
    private final TournamentCartController tournamentCartController;

    /**
     * Constructs a new TournamentCart for the specified tournament.
     *
     * @param tournament The {@link Tournament} data to display.
     */
    public TournamentCart(Tournament tournament) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.TOURNAMENT_CART.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            this.tournamentCartController = loader.getController();

            setCardDetails(tournament);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load TournamentCart component", e);
        }
    }

    /**
     * Updates the card's visual data.
     *
     * @param tournament The {@link Tournament} data to display.
     */
    public void setCardDetails(Tournament tournament) {
        if (tournamentCartController != null) {
            tournamentCartController.setData(tournament);
        }
    }
}
