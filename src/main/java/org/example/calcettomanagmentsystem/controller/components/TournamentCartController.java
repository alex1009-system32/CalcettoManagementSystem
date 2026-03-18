package org.example.calcettomanagmentsystem.controller.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.time.LocalDate;

public class TournamentCartController extends javafx.scene.control.Button {

    @FXML
    private Label nameLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label durationLabel;

    private Tournament tournament;

    @FXML
    private void click() {
        ServiceManager.setTournament(tournament);
        App.setRoot(FXMLNavigator.START_TOURNAMENT);
    }

    public void setData(Tournament tournament) {
        this.tournament = tournament;
        nameLabel.setText(tournament.name());
        dateLabel.setText(tournament.date().toString());
        durationLabel.setText(String.valueOf(tournament.duration()));
    }
}
