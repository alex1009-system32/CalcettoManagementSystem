package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Controller for the {@link org.example.calcettomanagmentsystem.ui.components.MatchCart} component.
 * <p>
 * This class manages the display of match information on a single card
 * and handles the user request to view match details.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class MatchCartController extends Button {
    /** Label displaying basic match information (e.g., Team A vs Team B). */
    @FXML
    private Label matchInfoLabel;

    /** The match data currently being displayed. */
    private Match match;
    /** Callback for requesting a modal window to open. */
    private Consumer<Match> onOpenRequested;

    /**
     * Populates the controller with match data and the open request callback.
     *
     * @param match The {@link Match} instance.
     * @param onOpenRequested The callback for opening the match modal.
     */
    public void setData(@NotNull Match match, Consumer<Match> onOpenRequested) {
        this.match = match;
        this.onOpenRequested = onOpenRequested;

        matchInfoLabel.setText(match.createMatchName());
    }

    /**
     * Handles the user's request to open the match details modal.
     */
    @FXML
    private void openModal() {
        if (onOpenRequested != null) {
            onOpenRequested.accept(match);
        }
    }
}
