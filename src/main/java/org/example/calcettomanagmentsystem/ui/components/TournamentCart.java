package org.example.calcettomanagmentsystem.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.example.calcettomanagmentsystem.ui.controller.components.TournamentCartController;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

import java.io.IOException;

public class TournamentCart extends Button {
    private final TournamentCartController tournamentCartController;

    public TournamentCart(Tournament tournament) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.TOURNAMENT_CART.getPath()));
        System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
        loader.setRoot(this);

        try {
            loader.load();
            this.tournamentCartController = loader.getController();

            setCardDetails(tournament);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCardDetails(Tournament tournament) {
        if (tournamentCartController != null) {
            tournamentCartController.setData(tournament);
        }
    }

}
