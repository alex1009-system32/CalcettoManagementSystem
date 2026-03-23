package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

public class TournamentCartController extends javafx.scene.control.Button {

    @FXML
    private Label nameLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label durationLabel;

    private Tournament tournament;

    public void setData(Tournament tournament) {
        this.tournament = tournament;
        nameLabel.setText(tournament.name());
        dateLabel.setText(tournament.date().toString());
        durationLabel.setText(String.valueOf(tournament.duration()));
    }

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
