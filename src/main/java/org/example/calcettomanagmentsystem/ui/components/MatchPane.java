package org.example.calcettomanagmentsystem.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.example.calcettomanagmentsystem.ui.controller.components.MatchPaneController;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

import java.io.IOException;

public class MatchPane extends HBox {
    private final MatchPaneController matchPaneController;

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
