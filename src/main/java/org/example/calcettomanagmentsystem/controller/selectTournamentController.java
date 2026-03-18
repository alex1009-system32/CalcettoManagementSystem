package org.example.calcettomanagmentsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.components.TournamentCart;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;
import org.example.calcettomanagmentsystem.model.Tournament;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
 * @see FXMLNavigator
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
            TournamentCart tournamentCart = new TournamentCart(tournament);
            tournamentFlowPane.getChildren().add(tournamentCart);
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
			App.setRoot(FXMLNavigator.START_TOURNAMENT);
		} else {
			App.setRoot(FXMLNavigator.ROUND_TOURNAMENT);
		}
	}

	/**
	 * Navigiert zur Maske für das Erstellen eines neuen Turniers.
	 */
	@FXML
	protected void addTournament() {
		App.setRoot(FXMLNavigator.CREATE_TOURNAMENT);
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
