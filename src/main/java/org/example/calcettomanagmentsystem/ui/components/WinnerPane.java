package org.example.calcettomanagmentsystem.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;
import org.example.calcettomanagmentsystem.ui.controller.components.TournamentCartController;
import org.example.calcettomanagmentsystem.ui.controller.components.WinnerPaneController;

import java.io.IOException;

public class WinnerPane extends VBox {

    /** The controller managing the visual elements of this tournament card. */
    private WinnerPaneController winnerPaneController;

    public WinnerPane(Team team) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.WINNER_PANE.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            this.winnerPaneController = loader.getController();

            setCardDetails(team);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load TournamentCart component", e);
        }
    }

    public void setCardDetails(Team team) {
        if (winnerPaneController != null) {
            winnerPaneController.setData(team);
        }
    }
}
