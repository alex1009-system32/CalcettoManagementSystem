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


public class startTournamentController implements Initializable {
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
        tournamentNameLabel.setText(ServiceManager.getTournament().name());
        preRoundLabel.setText(String.valueOf(ServiceManager.getTournament().preRound()));
        currentRoundLabel.setText(String.valueOf(ServiceManager.getTournament().currentRound()));
        maxTeamSizeLabel.setText(String.valueOf(ServiceManager.getTournament().maxTeamSize()));

        playerFlowPane.getChildren().clear();

        for (Player player : ServiceManager.getPlayerService().findAllOfTournament(ServiceManager.getTournament())) {

            Label pnameLabel = new Label(player.name());
            pnameLabel.getStyleClass().addAll("label-major", "player-list-label");
            HBox.setHgrow(pnameLabel, Priority.ALWAYS);

            Label pemailLabel = new Label(player.email());
            pemailLabel.getStyleClass().addAll("label-major", "player-list-label");
            HBox.setHgrow(pemailLabel, Priority.ALWAYS);

            Button deleteBtn = new Button("delete");
            deleteBtn.getStyleClass().addAll("btn-base", "btn-danger-outline", "player-list-delete-btn");

            HBox hBox = new HBox(pnameLabel, pemailLabel, deleteBtn);
            hBox.getStyleClass().add("player-list-hbox");

            Button playerButton = new Button();
            playerButton.getStyleClass().add("player-list-row");
            playerButton.setMnemonicParsing(false);
            playerButton.setGraphic(hBox);

            playerButton.setOnAction(e -> {
                IO.println(player);
                playerButton.getStyleClass().forEach(System.out::println);
            });

            deleteBtn.setOnAction(e -> {
                delete(player);
            });

            playerFlowPane.getChildren().add(playerButton);
        }

    }

    private void create(Stage stage, @NotNull TextField nameField, TextField emailField) {
        boolean result = true;

        if (nameField.getText().isEmpty()) {
            nameField.getStyleClass().add("text-field-error");
            result = false;
        }

        if (!result) {
            return;
        }

        System.out.println(ServiceManager.getPlayerService()
                                   .create(nameField.getText(), emailField.getText(), ServiceManager.getTournament()));

        updateList();
        stage.close();
    }

    private void delete(Player player) {
        ServiceManager.getPlayerService().delete(player);
        updateList();
    }

    private void displayModal(Stage stage) {
        Stage modalStage = new Stage();

        VBox root = new VBox();
        root.getStyleClass().addAll("main-container", "modal-root");

        VBox infoPane = new VBox();
        infoPane.getStyleClass().addAll("info-pane", "modal-info-pane");

        Label title = new Label("Add New Player");
        title.getStyleClass().add("header-text");

        Region accentLine = new Region();
        accentLine.getStyleClass().add("emerald-line");

        VBox header = new VBox(title, accentLine);
        header.getStyleClass().add("header-area");

        VBox nameGroup = new VBox();
        nameGroup.getStyleClass().add("input-group");
        Label nameLabel = new Label("PLAYER NAME");
        nameLabel.getStyleClass().add("label-minor");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter full name");
        nameField.getStyleClass().add("text-field-custom");
        nameGroup.getChildren().addAll(nameLabel, nameField);

        VBox emailGroup = new VBox();
        emailGroup.getStyleClass().add("input-group");
        Label emailLabel = new Label("EMAIL ADDRESS");
        emailLabel.getStyleClass().add("label-minor");
        TextField emailField = new TextField("player@example.com");
        emailField.setDisable(true);
        emailField.getStyleClass().add("text-field-custom");
        emailGroup.getChildren().addAll(emailLabel, emailField);

        Button addPlayerBtn = new Button("Add Player");
        addPlayerBtn.getStyleClass().addAll("btn-base", "btn-primary");
        HBox.setHgrow(addPlayerBtn, Priority.ALWAYS);

        addPlayerBtn.setOnAction(e -> {
            create(modalStage, nameField, emailField);
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().addAll("btn-base", "btn-outline");
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);

        cancelBtn.setOnAction(e -> {
            closeModal(modalStage);
        });

        VBox buttonBox = new VBox(15, addPlayerBtn, cancelBtn);

        infoPane.getChildren().addAll(header, nameGroup, emailGroup, buttonBox);
        root.getChildren().add(infoPane);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(App.class.getResource("css/modal/addPlayer.css").toExternalForm());

        modalStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setScene(scene);
        modalStage.setResizable(false);
        modalStage.showAndWait();
    }

    private void closeModal(@NotNull Stage stage) {
        stage.close();
    }

    @FXML
    private void startTournament() {
        ServiceManager.getMakerService().generateTeams(ServiceManager.getTournament());
        ServiceManager.getMakerService().generateNextMatches(ServiceManager.getTournament());
        App.setRoot(FxmlNavigation.ROUND_TOURNAMENT);
    }

    @FXML
    private void openModal() {
        displayModal((Stage) tournamentNameLabel.getScene().getWindow());
    }

    @FXML
    private void goBack() {
        App.setRoot(FxmlNavigation.SELECT_TOURNAMENT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (ServiceManager.getTournament() == null) App.setRoot(FxmlNavigation.SELECT_TOURNAMENT);
        updateList();
    }
}
