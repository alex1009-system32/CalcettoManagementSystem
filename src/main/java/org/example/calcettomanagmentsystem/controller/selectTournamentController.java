package org.example.calcettomanagmentsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.FxmlLocation;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.DepthTest;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller that renders the list of tournaments and handles selection/creation actions.
 * Initializes the dashboard view with cards for each available tournament.
 */
public class selectTournamentController implements Initializable {
	List<Tournament> tournamentList;
	@FXML
	private FlowPane tournamentFlowPane;

	/**
	 * Refreshes the tournament cards shown in the view.
	 * Fetches all tournaments from the database and dynamically creates UI elements for them.
	 */
	private void updateList() {
		tournamentList = new SQLiteTournamentDao().getAllTournaments();

		for (Tournament tournament : tournamentList) {
			Label tournamentLabel = new Label(tournament.getTournamentName());
			tournamentLabel.setPadding(new Insets(0, 100, 0, 100));
			HBox.setHgrow(tournamentLabel, Priority.ALWAYS);

			Label startDateLabel = new Label(tournament.getDate().toString());
			startDateLabel.setPadding(new Insets(0, 100, 0, 100));
			HBox.setHgrow(startDateLabel, Priority.ALWAYS);

			Label durationLabel = new Label(String.valueOf(tournament.getDuration()));
			durationLabel.setPadding(new Insets(0, 100, 0, 100));
			HBox.setHgrow(durationLabel, Priority.ALWAYS);

			HBox hbox = new HBox();
			hbox.setAlignment(Pos.CENTER);
			hbox.setMaxHeight(Double.MAX_VALUE);
			hbox.setMaxWidth(Double.MAX_VALUE);
			hbox.setStyle("-fx-padding: 10;");
			hbox.getChildren().addAll(tournamentLabel, startDateLabel, durationLabel);

			Button tournamentButton = new Button();
			tournamentButton.setDepthTest(DepthTest.DISABLE);
			tournamentButton.setGraphicTextGap(0.0);
			tournamentButton.setPrefWidth(980.0);
			tournamentButton.setGraphic(hbox);

			tournamentButton.setPadding(new Insets(10, 10, 10, 10));

			tournamentButton.getStyleClass().add("tournament-card");

			tournamentButton.setOnAction(e -> {
				selectTournament(tournament);
			});

			tournamentFlowPane.getChildren().add(tournamentButton);
		}

		Label tournamentLabel = new Label("Add Tournament");
		tournamentLabel.setPadding(new Insets(0, 100, 0, 100));
		HBox.setHgrow(tournamentLabel, Priority.ALWAYS);

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setMaxHeight(Double.MAX_VALUE);
		hbox.setMaxWidth(Double.MAX_VALUE);
		hbox.setStyle("-fx-padding: 10;");
		hbox.getChildren().addAll(tournamentLabel);

		Button tournamentButton = new Button();
		tournamentButton.setDepthTest(DepthTest.DISABLE);
		tournamentButton.setGraphicTextGap(0.0);
		tournamentButton.setPrefWidth(980.0);
		tournamentButton.setGraphic(hbox);

		tournamentButton.setPadding(new Insets(10, 10, 10, 10));

		tournamentButton.getStyleClass().add("add-tournament-card");

		tournamentButton.setOnAction(e -> {
			addTournament();
		});

		tournamentFlowPane.getChildren().add(tournamentButton);

	}

	/**
	 * Opens the selected tournament dashboard.
	 */
	@FXML
	protected void selectTournament(Tournament tournament) {

		App.setTournament(tournament);

		if (tournament.getCurrendRound() <= 0.0) {
			App.setRoot(FxmlLocation.START_TOURNAMENT);
		} else {
			App.setRoot(FxmlLocation.ROUND_TOURNAMENT);
		}
	}

	/**
	 * Navigates to the Create Tournament view.
	 */
	@FXML
	protected void addTournament() {
		App.setRoot(FxmlLocation.CREATE_TOURNAMENT);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateList();
	}
}
