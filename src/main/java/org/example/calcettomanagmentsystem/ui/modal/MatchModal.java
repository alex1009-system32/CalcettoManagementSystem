package org.example.calcettomanagmentsystem.ui.modal;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.ui.components.TournamentCart;
import org.example.calcettomanagmentsystem.ui.controller.modal.MatchModalController;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;

import java.io.IOException;

/**
 * Custom JavaFX component for the match details modal view.
 * <p>
 * This component provides the layout for viewing and editing scores of a match.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class MatchModal extends VBox {
    /** The controller managing the visual logic of this modal. */
    MatchModalController matchModalController;

    /**
     * Constructs a new MatchModal component.
     *
     * @param stage The {@link Stage} instance for the modal.
     * @param match The {@link Match} to display details for.
     */
    public MatchModal(Stage stage, Match match, Runnable runnable) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.MATCH_MODAL.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            this.matchModalController = loader.getController();

            setCardDetails(stage, match, runnable);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load MatchModal component", e);
        }

    }

    /**
     * Initializes the modal's controller with required data.
     *
     * @param stage The modal's stage.
     * @param match The match data.
     */
    private void setCardDetails(Stage stage, Match match, Runnable runnable) {
        if (matchModalController != null) {
            matchModalController.setData(stage, match,  runnable);
        }
    }

}
