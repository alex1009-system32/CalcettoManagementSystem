package org.example.calcettomanagmentsystem.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.FxmlLocation;
import org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the "Start Tournament" view.
 * Displays tournament metadata and allows adding players.
 */
public class startTournametController implements Initializable {
	Tournament tournament = null;
	@FXML
	private Label tournamentNameLabel;
	@FXML
	private Label preRoundLabel;
	@FXML
	private Label currentRoundLabel;
	@FXML
	private Label maxTeamSizeLabel;
	@FXML
	private FlowPane playerFlowPane;

	private void updateList() {
		tournamentNameLabel.setText(tournament.getTournamentName());
		preRoundLabel.setText(String.valueOf(tournament.getPreRound()));
		currentRoundLabel.setText(String.valueOf(tournament.getCurrendRound()));
		maxTeamSizeLabel.setText(String.valueOf(tournament.getMaxTeamSize()));

		playerFlowPane.getChildren().clear();
		List<Player> players = new SQLitePlayerDao().getAllPlayersFromTournament(tournament);

		for (Player player : players) {

			Label pnameLabel = new Label(player.pname());
			pnameLabel.setAlignment(Pos.CENTER);
			pnameLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			pnameLabel.getStyleClass().add("label-major");
			HBox.setHgrow(pnameLabel, Priority.ALWAYS);

			Label pemailLabel = new Label(player.pemail());
			pemailLabel.setAlignment(Pos.CENTER);
			pemailLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			pemailLabel.getStyleClass().add("label-major");
			HBox.setHgrow(pemailLabel, Priority.ALWAYS);

			HBox hBox = new HBox();
			hBox.setAlignment(Pos.CENTER);
			hBox.setPrefHeight(50.0);
			hBox.setPrefWidth(500.0);
			hBox.getChildren().addAll(pnameLabel, pemailLabel);

			Button playerButton = new Button();
			playerButton.getStyleClass().add("player-list-row");
			playerButton.setMnemonicParsing(false);
			playerButton.setGraphic(hBox);

			playerFlowPane.getChildren().add(playerButton);
		}

	}

	private boolean create(Stage stage, @NotNull TextField nameField, TextField emailField) {
		boolean result = true;

		if (nameField.getText().isEmpty()) {
			nameField.setStyle("-fx-background-color: #fffafb; " + "-fx-border-color: #d63031;");
			result = false;
		}

		/* Is Disabled
		if (emailField.getText().isEmpty()) {
			nameField.setStyle("-fx-background-color: #fffafb; " + "-fx-border-color: #d63031;");
			result = false;
		} */

		if (!result) {
			return false;
		}

		new SQLitePlayerDao().addPlayer(nameField.getText(), emailField.getText(), tournament);

		updateList();
		stage.close();
		return true;
	}

	private void cancel(@NotNull Stage stage) {
		stage.close();
	}

	private void displayModal(Stage stage) {
		Stage modalStage = new Stage();

		VBox root = new VBox();
		root.setPrefSize(500, 700);
		root.getStyleClass().add("main-container");
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(40));

		VBox formCard = new VBox(25);
		formCard.setMaxWidth(400);
		formCard.getStyleClass().add("info-pane");
		formCard.setPadding(new Insets(40));
		formCard.setAlignment(Pos.TOP_LEFT);

		Label title = new Label("Add New Player");
		title.getStyleClass().add("header-text");

		Region accentLine = new Region();
		accentLine.setMinWidth(60);
		accentLine.setMaxWidth(60);
		accentLine.setMinHeight(4);
		accentLine.getStyleClass().add("emerald-line");

		VBox header = new VBox(5, title, accentLine);

		VBox nameGroup = new VBox(8);
		Label nameLabel = new Label("PLAYER NAME");
		nameLabel.getStyleClass().add("label-minor");
		TextField nameField = new TextField();
		nameField.setPromptText("Enter full name");
		nameField.getStyleClass().add("custom-text-field");
		nameGroup.getChildren().addAll(nameLabel, nameField);

		VBox emailGroup = new VBox(8);
		Label emailLabel = new Label("EMAIL ADDRESS");
		emailLabel.getStyleClass().add("label-minor");
		TextField emailField = new TextField("player@example.com");
		emailField.setDisable(true);
		emailField.getStyleClass().add("custom-text-field");
		emailGroup.getChildren().addAll(emailLabel, emailField);

		Button addPlayerBtn = new Button("Add Player");
		addPlayerBtn.setMaxWidth(Double.MAX_VALUE);
		addPlayerBtn.getStyleClass().add("btn-emerald");

		addPlayerBtn.setOnAction(e -> {
			create(modalStage, nameField, emailField);
		});

		Button cancelBtn = new Button("Cancel");
		cancelBtn.setMaxWidth(Double.MAX_VALUE);
		cancelBtn.getStyleClass().add("btn-cancel");

		cancelBtn.setOnAction(e -> {
			cancel(modalStage);
		});

		formCard.getChildren().addAll(header, nameGroup, emailGroup, addPlayerBtn, cancelBtn);
		root.getChildren().add(formCard);

		Scene scene = new Scene(root, 400, 500);
		scene.getStylesheets().add(App.class.getResource("css/view/modal.css").toExternalForm());

		modalStage.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);

		modalStage.initModality(Modality.APPLICATION_MODAL);
		modalStage.setScene(scene);
		modalStage.setResizable(false);
		modalStage.showAndWait();
	}
	@FXML
	protected void openModal() {
		displayModal((Stage) tournamentNameLabel.getScene().getWindow());
	}
	@FXML
	/**
	 * Navigates back to the selection screen.
	 */
	protected void goBack() {
		App.setRoot(FxmlLocation.SELECT_TOURNAMENT);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (App.getTournament() == null) {
			App.setRoot(FxmlLocation.SELECT_TOURNAMENT);
		}

		if (App.getTournament().getCurrendRound() != -1) {
			App.setRoot(FxmlLocation.ROUND_TOURNAMENT);
		}

		tournament = App.getTournament();

		updateList();

	}
}
