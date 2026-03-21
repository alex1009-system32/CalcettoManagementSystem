package org.example.calcettomanagmentsystem.modal;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.components.TournamentCart;
import org.example.calcettomanagmentsystem.controller.modal.MatchModalController;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

import java.io.IOException;

public class MatchModal extends VBox {
    MatchModalController matchModalController;

    public MatchModal(Stage stage, Match match) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.MATCH_MODAL.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            this.matchModalController = loader.getController();

            setCardDetails(stage, match);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCardDetails(Stage stage, Match match) {
        if (matchModalController != null) {
            matchModalController.setData(stage, match);
        }
    }

}
