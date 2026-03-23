package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MatchCartController extends Button {
    @FXML
    private Label matchInfoLabel;

    private Match match;
    private Consumer<Match> onOpenRequested;

    public void setData(@NotNull Match match, Consumer<Match> onOpenRequested) {
        this.match = match;
        this.onOpenRequested = onOpenRequested;

        matchInfoLabel.setText(createMatchName(match));
    }

    // Dosen't belong here
    private String createMatchName(Match match) {
        List<String> names = new ArrayList<>();
        for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
            names.add(entry.getKey().name());
        }
        return String.join(" vs. ", names);
    }

    @FXML
    private void openModal() {
        if (onOpenRequested != null) {
            onOpenRequested.accept(match);
        }
    }
}
