package org.example.calcettomanagmentsystem.ui.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.example.calcettomanagmentsystem.service.ValidationException;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

/**
 * Controller for assigning scores to a specific team within a match.
 * <p>
 * This class handles score updates, including validation and error display.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 */
public class TeamPointController extends HBox {
    public Button saveBtn;
    /** Label for the team's name. */
    @FXML
    private Label teamNameLabel;

    /** Text field for entering team score/points. */
    @FXML
    private TextField pointTextField;

    /** The match context. */
    private Match match;
    /** The team whose points are being assigned. */
    private Team team;

    Runnable runnable;

    /**
     * Initializes the controller with specific team and match context.
     *
     * @param match The {@link Match} instance.
     * @param team The {@link Team} to assign points for.
     */
    public void setData(Match match, Team team, Runnable runnable) {
        this.match = match;
        this.team = team;
        this.runnable = runnable;

        teamNameLabel.setText(team.name());
        pointTextField.setText(String.valueOf(match.teamResults().get(team)));
    }

    /**
     * Persists the assigned points into the database.
     * Displays error messages in the text field if validation fails.
     */
    @FXML
    private void savePoint() {
        try {
            ServiceManager.getMatchService().setPoints(team, match, Integer.parseInt(pointTextField.getText()));
            if (runnable != null) {
                runnable.run();
            }
        } catch (ValidationException e) {
            pointTextField.setText(e.getMessage());
            pointTextField.getStyleClass().add("error-label");
        } catch (NumberFormatException _) {
            pointTextField.setText("Please enter a number");
            pointTextField.getStyleClass().add("error-label");
        }
    }
}
