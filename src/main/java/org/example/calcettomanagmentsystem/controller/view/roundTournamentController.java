package org.example.calcettomanagmentsystem.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.navigation.FxmlNavigation;
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
            flowPane.setOrientation(Orientation.HORIZONTAL);
            flowPane.setAlignment(Pos.TOP_CENTER);
            flowPane.setHgap(20);
            flowPane.setVgap(20);
            flowPane.setPadding(new Insets(30));
            flowPane.getStyleClass().add("main-container");

            for (Match match : list) {
                Button matchButton = new Button();
                matchButton.setPrefSize(350, 80);
                matchButton.getStyleClass().add("tournament-list-button");

                HBox matchContent = new HBox();
                matchContent.setAlignment(Pos.CENTER);
                matchContent.setSpacing(15);
                matchContent.setPrefWidth(330);

                Label matchInfo = new Label(createMatchName(match));
                matchInfo.getStyleClass().add("label-major");
                matchInfo.setFont(new Font("Segoe UI Semibold", 14));

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
        root.setPrefSize(500.0, 700.0);
        root.getStyleClass().add("main-container");
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40.0));

        VBox infoPane = new VBox(25.0);
        infoPane.setMaxWidth(400.0);
        infoPane.getStyleClass().add("info-pane");
        infoPane.setAlignment(Pos.TOP_LEFT);
        infoPane.setPadding(new Insets(40.0));

        VBox headerBox = new VBox(5.0);
        Label headerLabel = new Label("Update Match Scores");

        headerLabel.getStyleClass().add("header-text");
        Region emeraldLine = new Region();
        emeraldLine.setPrefSize(60.0, 4.0);
        emeraldLine.getStyleClass().add("emerald-line");

        headerBox.getChildren().addAll(headerLabel, emeraldLine);

        VBox teamsBox = new VBox(8.0);
        Label teamsLabel = new Label("Teams");
        teamsLabel.getStyleClass().add("label-minor");

        VBox rowsContainer = new VBox(10.0);

        for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
            HBox row = new HBox(10.0);
            row.setAlignment(Pos.CENTER);
            row.setPrefHeight(50.0);

            Label label = new Label(entry.getKey().name());
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);
            label.getStyleClass().add("label-major");

            TextField textField = new TextField();
            textField.getStyleClass().add("custom-text-field");
            textField.setPromptText("points");
            textField.setPrefWidth(80.0);
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
        cancelBtn.setMaxWidth(Double.MAX_VALUE);
        cancelBtn.getStyleClass().add("btn-cancel");
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

    private String createMatchName(Match match) {
        List<String> names = new ArrayList<>();

        for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
            names.add(entry.getKey().name());
        }

        return String.join(" vs. ", names);
    }

    @FXML
    private void nextRound() {
        ServiceManager.getMakerService().generateNextMatches(ServiceManager.getTournament());
    }

    @FXML
    private void goBack() {
        App.setRoot(FxmlNavigation.SELECT_TOURNAMENT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
}
