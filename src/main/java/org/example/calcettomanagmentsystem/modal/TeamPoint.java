package org.example.calcettomanagmentsystem.modal;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.components.TournamentCart;
import org.example.calcettomanagmentsystem.controller.modal.MatchPointController;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

import java.io.IOException;

public class TeamPoint extends HBox {
    MatchPointController matchPointController;

    public TeamPoint(Match match, Team team) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.TEAM_POINT.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            this.matchPointController = loader.getController();

            setCardDetails(match, team);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCardDetails(Match match, Team team) {
        if (matchPointController != null) {
            matchPointController.setData(match, team);
        }
    }

}
