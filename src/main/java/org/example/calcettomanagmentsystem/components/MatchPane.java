package org.example.calcettomanagmentsystem.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import org.example.calcettomanagmentsystem.controller.components.MatchPaneController;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

import java.io.IOException;

public class MatchPane extends ScrollPane {
    MatchPaneController matchPaneController;

    public MatchPane() {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.MATCH_TAP.getPath()));
        System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
        loader.setRoot(this);

        try {
            loader.load();
            matchPaneController = (MatchPaneController) loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FlowPane getFlowPane() {
        return matchPaneController.getFlowPane();
    }
}
