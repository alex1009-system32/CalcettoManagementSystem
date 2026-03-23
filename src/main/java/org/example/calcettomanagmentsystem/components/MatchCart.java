package org.example.calcettomanagmentsystem.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.example.calcettomanagmentsystem.controller.components.MatchCartController;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

import java.io.IOException;
import java.util.function.Consumer;

public class MatchCart extends Button {
    private final MatchCartController matchCartController;

    public MatchCart(Match match, Consumer<Match> onOpenRequested) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.MATCH_CART.getPath()));
        System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
        loader.setRoot(this);

        try {
            loader.load();
            matchCartController = loader.getController();

            setCardDetails(match, onOpenRequested);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCardDetails(Match match, Consumer<Match> onOpenRequested) {
        if (matchCartController != null) {
            matchCartController.setData(match, onOpenRequested);
        }
    }


}
