package org.example.calcettomanagmentsystem.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchCartController {
    @FXML
    private Label matchInfoLabel;

    private Match match;
    private Runnable onActionCallback;

    public void setData(@NotNull Match match, Runnable onActionCallback) {

    }

    private String createMatchName(Match match) {
        List<String> names = new ArrayList<>();
        for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
            names.add(entry.getKey().name());
        }
        return String.join(" vs. ", names);
    }

    @FXML
    private void openModal() {
        if (onActionCallback != null) {
            onActionCallback.run();
        }
    }
}
