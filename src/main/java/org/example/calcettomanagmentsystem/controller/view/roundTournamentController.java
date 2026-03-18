package org.example.calcettomanagmentsystem.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
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
import org.example.calcettomanagmentsystem.navigation.FxmlNavigation;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class roundTournamentController implements Initializable {

    @FXML
    private TabPane matchOfRoundPane;

    private void update() {
        List<Match> matchList = ServiceManager.getMatchService()
                .findMatchesByTournament(ServiceManager.getTournament());

        List<List<Match>> groupedByRound = matchList.stream()
                .collect(Collectors.groupingBy(Match::round, TreeMap::new, Collectors.toList()))
                .values()
                .stream()
                .toList();

        matchOfRoundPane.getTabs().clear();

        for (List<Match> roundMatches : groupedByRound) {
            FlowPane flowPane = new FlowPane();
            flowPane.getStyleClass().addAll("match-flow-pane");

            for (Match match : roundMatches) {
                Button matchButton = new Button();
                matchButton.getStyleClass().addAll("tournament-list-button", "match-button");

                HBox matchContent = new HBox();
                matchContent.getStyleClass().add("match-content-hbox");

                Label matchInfo = new Label(createMatchName(match));
                matchInfo.getStyleClass().add("match-info-label");

                matchContent.getChildren().add(matchInfo);
                matchButton.setGraphic(matchContent);
                matchButton.setOnAction(e -> displayModal((Stage) matchOfRoundPane.getScene().getWindow(), match));

                flowPane.getChildren().add(matchButton);
            }

            Tab tab = new Tab("Round " + roundMatches.getFirst().round());
            tab.setContent(flowPane);
            tab.getStyleClass().add("custom-tab-pane");

            matchOfRoundPane.getTabs().add(tab);
        }
    }

    private void setPoints(Match match, Team team, int points) {
        ServiceManager.getMatchService().setPoints(team, match, points);
        update();
    }

    private void displayModal(Stage stage, Match match) {
        Stage modalStage = new Stage();
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(stage);

        VBox root = new VBox();
        root.getStyleClass().addAll("main-container", "modal-root");

        VBox infoPane = new VBox();
        infoPane.getStyleClass().addAll("info-pane", "modal-info-pane");

        Label headerLabel = new Label("Update Match Scores");
        headerLabel.getStyleClass().add("header-title"); // Using header-title from global

        Region accentLine = new Region();
        accentLine.getStyleClass().add("accent-line"); // Using accent-line from global

        VBox headerBox = new VBox(headerLabel, accentLine);
        headerBox.getStyleClass().add("header-area");

        VBox teamsBox = new VBox();
        teamsBox.getStyleClass().add("input-group");
        Label teamsLabel = new Label("Teams");
        teamsLabel.getStyleClass().add("input-label"); // Using input-label from global

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
                textField.setText(String.valueOf(entry.getValue().intValue()));
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
        cancelBtn.setMaxWidth(Double.MAX_VALUE);
        cancelBtn.setOnAction(event -> modalStage.close());

        infoPane.getChildren().addAll(headerBox, teamsBox, cancelBtn);
        root.getChildren().add(infoPane);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        // Ensure styles are loaded
        scene.getStylesheets().add(App.class.getResource("css/global-styles.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("css/view/roundTournament-styles.css").toExternalForm());

        modalStage.setScene(scene);
        modalStage.setResizable(false);
        modalStage.showAndWait();
    }

    private String createMatchName(Match match) {
        return match.teamResults().keySet().stream().map(Team::name).collect(Collectors.joining(" vs. "));
    }

    @FXML
    private void nextRound() {
        ServiceManager.getMakerService().generateNextMatches(ServiceManager.getTournament());
        update(); // Refresh UI after generating next matches
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
