package org.example.calcettomanagmentsystem.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class roundTournamentController implements Initializable {

    @FXML
    private TabPane matchOfRoundPane;

    private void update() {
        List<Match> matchList =
                ServiceManager.getMatchService().findMatchesByTournament(ServiceManager.getTournament());

        List<List<Match>> gorupedByMatchList = matchList.stream()
                                                        .collect(Collectors.groupingBy(Match::round,
                                                                                       TreeMap::new,
                                                                                       Collectors.toList()))
                                                        .values()
                                                        .stream()
                                                        .toList();

        matchOfRoundPane.getTabs().clear();

        for (List<Match> list : gorupedByMatchList) {
            FlowPane flowPane = new FlowPane();
            flowPane.getStyleClass().addAll("main-container", "match-flow-pane");

            for (Match match : list) {
                Button matchButton = new Button();
                matchButton.getStyleClass().addAll("tournament-list-button", "match-button");

                HBox matchContent = new HBox();
                matchContent.getStyleClass().add("match-content-hbox");

                Label matchInfo = new Label(createMatchName(match));
                matchInfo.getStyleClass().addAll("label-major", "match-info-label");

                matchContent.getChildren().add(matchInfo);
                matchButton.setGraphic(matchContent);

                matchButton.setOnAction(e -> openModal(match));

                flowPane.getChildren().add(matchButton);
            }

            Tab tab = new Tab();
            tab.setText("Round " + list.getFirst().round());
            tab.setContent(flowPane);

            matchOfRoundPane.getTabs().add(tab);
        }
    }

    private void setPoints(Match match, Team team, int points) {
        ServiceManager.getMatchService().setPoints(team, match, points);
        update();
    }

    private void openModal(Match match) {
        displayModal((Stage) matchOfRoundPane.getScene().getWindow(), match);
    }

    private void displayModal(Stage stage, Match match) {
        Stage modalStage = new Stage();

        VBox root = new VBox();
        root.getStyleClass().addAll("main-container", "modal-root");

        VBox infoPane = new VBox();
        infoPane.getStyleClass().addAll("info-pane", "modal-info-pane");

        Label headerLabel = new Label("Update Match Scores");
        headerLabel.getStyleClass().add("header-text");

        Region emeraldLine = new Region();
        emeraldLine.getStyleClass().add("emerald-line");

        VBox headerBox = new VBox(headerLabel, emeraldLine);
        headerBox.getStyleClass().add("header-area");

        VBox teamsBox = new VBox();
        teamsBox.getStyleClass().add("input-group");
        Label teamsLabel = new Label("Teams");
        teamsLabel.getStyleClass().add("label-minor");

        VBox rowsContainer = new VBox(10.0);

        for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
            HBox row = new HBox();
            row.getStyleClass().add("match-update-row");

            Label label = new Label(entry.getKey().name());
            label.getStyleClass().add("label-major");
            HBox.setHgrow(label, Priority.ALWAYS);

            TextField textField = new TextField();
            textField.getStyleClass().addAll("text-field-custom", "match-update-field");
            textField.setPromptText("pts");
            if (entry.getValue() != 0) {
                textField.setText(String.valueOf(entry.getValue()));
            }

            Button saveBtn = new Button("Save");
            saveBtn.getStyleClass().add("btn-save");
            saveBtn.setOnAction(event -> {
                try {
                    int pts = Integer.parseInt(textField.getText());
                    setPoints(match, entry.getKey(), pts);
                } catch (NumberFormatException e) {
                    textField.getStyleClass().add("text-field-error");
                }
            });

            row.getChildren().addAll(label, textField, saveBtn);
            rowsContainer.getChildren().add(row);
        }

        teamsBox.getChildren().addAll(teamsLabel, rowsContainer);

        Button cancelBtn = new Button("Close");
        cancelBtn.getStyleClass().addAll("btn-base", "btn-outline");
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);
        cancelBtn.setOnAction(event -> modalStage.close());

        infoPane.getChildren().addAll(headerBox, teamsBox, cancelBtn);
        root.getChildren().add(infoPane);

        Scene scene = new Scene(root);

        scene.getStylesheets().add(App.class.getResource("css/modal/openMatch.css").toExternalForm());

        modalStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setScene(scene);
        modalStage.setResizable(false);
        modalStage.showAndWait();
    }

    private void closeModal(Stage stage) {
        stage.close();
    }

    @FXML
    private void nextRound() {
        ServiceManager.getMakerService().generateNextMatches(ServiceManager.getTournament());
    }

    @FXML
    private void goBack() {
        App.setRoot(FXMLNavigator.SELECT_TOURNAMENT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
}
