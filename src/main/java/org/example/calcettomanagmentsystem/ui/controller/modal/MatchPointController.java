package org.example.calcettomanagmentsystem.ui.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

public class MatchPointController {
    @FXML
    private Label teamNameLabel;

    @FXML
    private TextField pointTextField;

    private Match match;
    private Team team;

    public void setData(Match match, Team team) {
        this.match = match;
        this.team = team;

        teamNameLabel.setText(team.name());
        pointTextField.setText(String.valueOf(match.teamResults().get(team)));
    }

    @FXML
    private void savePoint() {
        try {
            ServiceManager.getMatchService().setPoints(team, match, Integer.parseInt(pointTextField.getText()));
        } catch (ValidationException e) {
            pointTextField.setText(e.getMessage());
            pointTextField.getStyleClass().add("error-label");
        }  catch (NumberFormatException _) {
            pointTextField.setText("Please enter a number");
            pointTextField.getStyleClass().add("error-label");
        }
    }
}
