package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MatchCartController extends Button {
    @FXML
    private Label matchInfoLabel;

    private Match match;
    private Consumer<Match> onOpenRequested;

    public void setData(@NotNull Match match, Consumer<Match> onOpenRequested) {
        this.match = match;
        this.onOpenRequested = onOpenRequested;

        matchInfoLabel.setText(match.createMatchName());
    }

    @FXML
    private void openModal() {
        if (onOpenRequested != null) {
            onOpenRequested.accept(match);
        }
    }
}
