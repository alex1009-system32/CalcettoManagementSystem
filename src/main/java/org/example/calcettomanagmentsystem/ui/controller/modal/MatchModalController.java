package org.example.calcettomanagmentsystem.ui.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.ui.modal.TeamPoint;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;

import java.util.Map;

/**
 * Controller for the match details modal dialog.
 * <p>
 * This class displays scoring components for each team in the match.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 */
public class MatchModalController extends VBox {
    /** Flow pane for displaying team scoring elements. */
    @FXML
    private FlowPane teamFlowPane;

    /** The modal's stage instance. */
    private Stage stage;

    /**
     * Initializes the controller with match data.
     *
     * @param stage The {@link Stage} instance of the modal.
     * @param match The {@link Match} whose details are to be shown.
     */
    public void setData(Stage stage, Match match, Runnable runnable) {
        this.stage = stage;

        for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
            teamFlowPane.getChildren().add(new TeamPoint(match, entry.getKey(), runnable));
        }
    }

    /**
     * Closes the match details modal window.
     */
    @FXML
    private void close() {
        stage.close();
    }

}
