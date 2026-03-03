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
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.navigation.FxmlNavigation;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller für den Turnierstart und die Spielererfassung.
 * <p>
 * Die Ansicht stellt die aktuellen Turnierdaten bereit und erlaubt
 * die Pflege der Teilnehmer, bevor das Turnier fortgesetzt wird.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.App
 * @see FxmlNavigation
 */
public class startTournamentController implements Initializable {
    /**
     * Label für den Turniernamen.
     */
    @FXML
    private Label tournamentNameLabel;
    /**
     * Label für die Anzahl der Vorrunden.
     */
    @FXML
    private Label preRoundLabel;
    /**
     * Label für die aktuell gespeicherte Runde.
     */
    @FXML
    private Label currentRoundLabel;
    /**
     * Label für die maximale Teamgröße.
     */
    @FXML
    private Label maxTeamSizeLabel;
    /**
     * Container für die dynamisch erzeugte Spielerliste.
     */
    @FXML
    private FlowPane playerFlowPane;

    /**
     * Synchronisiert Turnierdaten und Spielerliste mit dem UI.
     *
     * @implNote Die Spielerliste wird neu aufgebaut, um inkonsistente
     * UI-Zustände nach Datenänderungen zu vermeiden.
     */
    private void updateList() {
        tournamentNameLabel.setText(ServiceManager.getTournament().getTournamentName());
        preRoundLabel.setText(String.valueOf(ServiceManager.getTournament().getPreRound()));
        currentRoundLabel.setText(String.valueOf(ServiceManager.getTournament().getCurrendRound()));
        maxTeamSizeLabel.setText(String.valueOf(ServiceManager.getTournament().getMaxTeamSize()));

        playerFlowPane.getChildren().clear();

        for (Player player : ServiceManager.getPlayerService()
                                           .getAllPlayerFromTournament(ServiceManager.getTournament())) {

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

            Button deleteBtn = new Button("delete");
            deleteBtn.setAlignment(Pos.CENTER);
            deleteBtn.setMaxSize(50.0, 50.0);
            deleteBtn.getStyleClass().add("cancel-button");
            HBox.setHgrow(pemailLabel, Priority.ALWAYS);

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setPrefHeight(50.0);
            hBox.setPrefWidth(700.0);
            hBox.getChildren().addAll(pnameLabel, pemailLabel, deleteBtn);

            Button playerButton = new Button();
            playerButton.getStyleClass().add("player-list-row");
            playerButton.setMnemonicParsing(false);
            playerButton.setGraphic(hBox);

            playerButton.setOnAction(e -> {
                IO.println(player);
            });

            deleteBtn.setOnAction(e -> {
                delete(player);
            });

            playerFlowPane.getChildren().add(playerButton);
        }

    }

    /**
     * Persistiert einen neuen Spieler und aktualisiert die Darstellung.
     *
     * @param stage      Modalfenster, das nach erfolgreicher Erstellung geschlossen wird
     * @param nameField  Eingabefeld für den Spielernamen
     * @param emailField Eingabefeld für die E-Mail-Adresse
     * @return {@code true} bei erfolgreicher Anlage
     */
    private void create(Stage stage, @NotNull TextField nameField, TextField emailField) {
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
            return;
        }

        ServiceManager.getPlayerService()
                      .createPlayer(nameField.getText(), emailField.getText(), ServiceManager.getTournament());

        updateList();
        stage.close();
    }

    private void delete(Player player) {
        ServiceManager.getPlayerService().deletePlayer(player);
        updateList();
    }

    /**
     * Öffnet ein modales Formular zur Spieleranlage.
     *
     * @param stage Elternfenster für den Modaldialog
     * @implNote Der Dialog nutzt eine transparente Stage, um die
     * visuelle Einbettung in das UI zu optimieren.
     */
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
            closeModal(modalStage);
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

    /**
     * Schließt die Spieleranlage ohne Persistenz.
     *
     * @param stage Modalfenster, das geschlossen wird
     */
    private void closeModal(@NotNull Stage stage) {
        stage.close();
    }

    @FXML
    private void startTournament() {
        App.setRoot(FxmlNavigation.ROUND_TOURNAMENT);
    }

    /**
     * Öffnet den Dialog zur Spieleranlage.
     */
    @FXML
    private void openModal() {
        displayModal((Stage) tournamentNameLabel.getScene().getWindow());
    }

    /**
     * Navigiert zurück zur Turnierauswahl.
     */
    @FXML
    private void goBack() {
        App.setRoot(FxmlNavigation.SELECT_TOURNAMENT);
    }

    /**
     * Initialisiert den Controller und erzwingt gültigen Turnierkontext.
     *
     * @param location  Ressourcenbasis der FXML
     * @param resources Lokalisierungsbundle, sofern vorhanden
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (ServiceManager.getTournament() == null) App.setRoot(FxmlNavigation.SELECT_TOURNAMENT);
        updateList();
    }
}
