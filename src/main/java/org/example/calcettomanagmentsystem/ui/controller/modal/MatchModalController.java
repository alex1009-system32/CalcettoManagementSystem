package org.example.calcettomanagmentsystem.ui.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.ui.modal.TeamPoint;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;

import java.util.Map;

public class MatchModalController extends VBox {
    @FXML
    private FlowPane teamFlowPane;

    private Stage stage;

    public void setData(Stage stage, Match match) {
        this.stage = stage;

        for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
            teamFlowPane.getChildren().add(new TeamPoint(match, entry.getKey()));
        }
    }

    @FXML
    private void close() {
        stage.close();
    }

}
