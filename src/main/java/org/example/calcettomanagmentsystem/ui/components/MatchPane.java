package org.example.calcettomanagmentsystem.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.example.calcettomanagmentsystem.ui.controller.components.MatchPaneController;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;

import java.io.IOException;

/**
 * Custom JavaFX component representing a container for matches.
 * <p>
 * This component provides a {@link FlowPane} to layout multiple {@link MatchCart} instances.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 */
public class MatchPane extends HBox {
    /** The controller managing the layout and content of this match pane. */
    private final MatchPaneController matchPaneController;

    /**
     * Constructs a new MatchPane by loading its FXML definition.
     */
    public MatchPane() {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.MATCH_TAP.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            matchPaneController = (MatchPaneController) loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load MatchPane component", e);
        }
    }

    /**
     * Retrieves the underlying flow pane for adding match cards.
     *
     * @return The {@link FlowPane} instance used for layout.
     */
    public FlowPane getFlowPane() {
        return matchPaneController.getFlowPane();
    }
}
