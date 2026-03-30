package org.example.calcettomanagmentsystem.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.example.calcettomanagmentsystem.ui.controller.components.MatchCartController;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Custom JavaFX component representing a match in the UI.
 * <p>
 * This component displays basic match information and provides a callback
 * for opening the match details modal.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 */
public class MatchCart extends Button {
    /** The controller managing the visual elements of this match card. */
    private final MatchCartController matchCartController;

    /**
     * Constructs a new MatchCart for the given match.
     *
     * @param match The {@link Match} data to display.
     * @param onOpenRequested Callback function invoked when the user requests to open match details.
     */
    public MatchCart(Match match, Consumer<Match> onOpenRequested) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.MATCH_CART.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            matchCartController = loader.getController();

            setCardDetails(match, onOpenRequested);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load MatchCart component", e);
        }
    }

    /**
     * Updates the card's visual data.
     *
     * @param match The {@link Match} data to display.
     * @param onOpenRequested Callback function for opening match details.
     */
    public void setCardDetails(Match match, Consumer<Match> onOpenRequested) {
        if (matchCartController != null) {
            matchCartController.setData(match, onOpenRequested);
        }
    }
}
