package org.example.calcettomanagmentsystem.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.navigation.FxmlNavigation;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Controller für die Rundenansicht eines Turniers.
 * <p>
 * Die Klasse dient als Platzhalter, um später Rundensteuerung und
 * Ergebnisdarstellung zentral zu bündeln.
 * </p>
 */
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
            flowPane.setOrientation(Orientation.VERTICAL);
            flowPane.setAlignment(Pos.TOP_CENTER);
            flowPane.setColumnHalignment(HPos.CENTER);

            for (Match match : list) {
                Button button = new Button(match.toString());
                button.setAlignment(Pos.CENTER);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setFont(new Font(13.0));
                HBox.setHgrow(button, Priority.ALWAYS);

                button.setOnAction(e -> {
                    openModal(match);
                });

                HBox hbox = new HBox();
                hbox.setPrefSize(300.0, 50.0);
                hbox.getChildren().add(button);

                Pane pane = new Pane();
                pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                pane.setMinSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
                pane.setPrefSize(300.0, 50.0);
                pane.getChildren().add(hbox);

                flowPane.getChildren().add(pane);
            }

            Tab tab = new Tab();
            tab.setText("Round: " + list.getFirst().round());
            tab.setContent(flowPane);

            matchOfRoundPane.getTabs().add(tab);
        }
    }

    private void setPoints(Match match, Team team, int points) {
        ServiceManager.getMatchService().setPoints(team, match, points);
        update();
    }

    private void openModal(Match match) {
        displayModal((Stage) matchOfRoundPane.getScene().getWindow(),  match);
    }

    private void displayModal(Stage stage, Match match) {
        Stage modalStage = new Stage();

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(500.0, 700.0);
        root.setPadding(new Insets(40.0));
        root.getStyleClass().add("main-container");

        VBox infoPane = new VBox(25.0);
        infoPane.setAlignment(Pos.TOP_LEFT);
        infoPane.setMaxWidth(400.0);
        infoPane.setPadding(new Insets(40.0));
        infoPane.getStyleClass().add("info-pane");

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

            TextField textField = new TextField();
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
        System.out.println("Leck eier");
    }

    @FXML
    private void goBack() {
        App.setRoot(FxmlNavigation.SELECT_TOURNAMENT);
    }

    /**
     * Initialisiert den Controller mit Ressourcenbezug.
     *
     * @param location  Ressourcenbasis der FXML
     * @param resources Lokalisierungsbundle, sofern vorhanden
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
}
