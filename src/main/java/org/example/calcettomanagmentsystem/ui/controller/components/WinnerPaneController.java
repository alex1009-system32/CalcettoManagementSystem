package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.calcettomanagmentsystem.core.model.Team;


public class WinnerPaneController {
    @FXML
    private Label winnerLabel;

    public void setData(Team team) {
        winnerLabel.setText(team.name());
    }
}
