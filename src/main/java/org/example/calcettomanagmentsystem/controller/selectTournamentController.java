package org.example.calcettomanagmentsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.navigation.FxmlNavigation;
import org.example.calcettomanagmentsystem.model.Tournament;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.DepthTest;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller für die Turnierübersicht und Navigation.
 * <p>
 * Die Ansicht dient als zentraler Einstiegspunkt, um bestehende Turniere
 * auszuwählen oder neue anzulegen.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.App
 * @see FxmlNavigation
 */
public class selectTournamentController implements Initializable {
	/**
	 * Container für die dynamisch erzeugten Turnierkarten.
	 */
	@FXML
	private FlowPane tournamentFlowPane;

	/**
	 * Aktualisiert die Turnierkarten basierend auf der aktuellen Datenlage.
	 *
	 * @implNote Die UI-Elemente werden vollständig neu erstellt, um eine
	 *           konsistente Darstellung nach Datenänderungen sicherzustellen.
	 */
	private void updateList() {


		for (Tournament tournament : ServiceManager.getTournamentService().findAll()) {
            Label nameLabel = new Label(tournament.name());
            nameLabel.getStyleClass().add("tournament-card-label");
            HBox.setHgrow(nameLabel, Priority.ALWAYS);

            Label dateLabel = new Label(tournament.date().toString());
            dateLabel.getStyleClass().add("tournament-card-label");
            HBox.setHgrow(dateLabel, Priority.ALWAYS);

            Label durationLabel = new Label(String.valueOf(tournament.duration()));
            durationLabel.getStyleClass().add("tournament-card-label");
            HBox.setHgrow(durationLabel, Priority.ALWAYS);

            HBox hbox = new HBox(nameLabel, dateLabel, durationLabel);
            hbox.getStyleClass().add("tournament-card-hbox");

            Button tournamentButton = new Button();
            tournamentButton.getStyleClass().add("tournament-card");
            tournamentButton.setGraphic(hbox);

            tournamentButton.setOnAction(e -> {
                selectTournament(tournament);
            });

            tournamentFlowPane.getChildren().add(tournamentButton);
		}

        Label label = new Label("Add Tournament");
        HBox.setHgrow(label, Priority.ALWAYS);

        HBox hBox = new HBox(label);
        hBox.getStyleClass().add("tournament-card-hbox");

        Button tournamentButton = new Button();
        tournamentButton.getStyleClass().add("add-tournament-card");
        tournamentButton.setGraphic(hBox);
        tournamentButton.setOnAction(event -> addTournament());

        tournamentFlowPane.getChildren().add(tournamentButton);
	}

	/**
	 * Öffnet die passende Turnieransicht basierend auf dem Fortschritt.
	 */
	@FXML
	protected void selectTournament(Tournament tournament) {
		ServiceManager.setTournament(tournament);

		if (tournament.currentRound() < 1) {
			App.setRoot(FxmlNavigation.START_TOURNAMENT);
		} else {
			App.setRoot(FxmlNavigation.ROUND_TOURNAMENT);
		}
	}

	/**
	 * Navigiert zur Maske für das Erstellen eines neuen Turniers.
	 */
	@FXML
	protected void addTournament() {
		App.setRoot(FxmlNavigation.CREATE_TOURNAMENT);
	}
	/**
	 * Initialisiert die Ansicht mit den aktuell verfügbaren Turnieren.
	 *
	 * @param location  Ressourcenbasis der FXML
	 * @param resources Lokalisierungsbundle, sofern vorhanden
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateList();
	}
}
