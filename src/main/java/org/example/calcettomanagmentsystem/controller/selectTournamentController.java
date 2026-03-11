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
            nameLabel.setContentDisplay(ContentDisplay.RIGHT);
            nameLabel.setMaxHeight(Double.MAX_VALUE);
            nameLabel.setMaxWidth(Double.MAX_VALUE);
            nameLabel.setOpaqueInsets(new Insets(10.0, 10.0, 10.0, 10.0));
            nameLabel.setFont(new Font(15.0));
            HBox.setHgrow(nameLabel, Priority.ALWAYS);

            Label dateLabel = new Label(tournament.date().toString());
            dateLabel.setContentDisplay(ContentDisplay.RIGHT);
            dateLabel.setMaxHeight(Double.MAX_VALUE);
            dateLabel.setMaxWidth(Double.MAX_VALUE);
            dateLabel.setOpaqueInsets(new Insets(10.0, 10.0, 10.0, 10.0));
            dateLabel.setFont(new Font(15.0));
            HBox.setHgrow(dateLabel, Priority.ALWAYS);

            Label durationLabel = new Label(String.valueOf(tournament.duration()));
            durationLabel.setContentDisplay(ContentDisplay.RIGHT);
            durationLabel.setMaxHeight(Double.MAX_VALUE);
            durationLabel.setMaxWidth(Double.MAX_VALUE);
            durationLabel.setOpaqueInsets(new Insets(10.0, 10.0, 10.0, 10.0));
            durationLabel.setFont(new Font(15.0));
            HBox.setHgrow(durationLabel, Priority.ALWAYS);

            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setFillHeight(false);
            hbox.setMaxHeight(Double.MAX_VALUE);
            hbox.setMaxWidth(Double.MAX_VALUE);
            hbox.setStyle("-fx-padding: 10;");
            hbox.getChildren().addAll(nameLabel, dateLabel, durationLabel);

            Button tournamentButton = new Button();
            tournamentButton.setPrefHeight(75.0);
            tournamentButton.setPrefWidth(800.0);
            tournamentButton.getStyleClass().add("tournament-card");
            tournamentButton.setGraphic(hbox);

            tournamentFlowPane.getChildren().add(tournamentButton);
		}

        Label label = new Label("Add Tournament");
        label.setAlignment(Pos.CENTER);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setFont(new Font(20.0));
        HBox.setHgrow(label, Priority.ALWAYS);

        HBox hBox = new HBox(label);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMaxHeight(Double.MAX_VALUE);
        hBox.setMaxWidth(Double.MAX_VALUE);
        hBox.setStyle("-fx-padding: 10;");

        Button tournamentButton = new Button();
        tournamentButton.setId("tournamentButton");
        tournamentButton.setDepthTest(javafx.scene.DepthTest.DISABLE);
        tournamentButton.setMaxWidth(Double.MAX_VALUE);
        tournamentButton.setPrefHeight(75.0);
        tournamentButton.setPrefWidth(700.0);
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
