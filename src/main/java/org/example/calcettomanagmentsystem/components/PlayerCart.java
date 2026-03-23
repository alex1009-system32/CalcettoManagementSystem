package org.example.calcettomanagmentsystem.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.example.calcettomanagmentsystem.controller.components.PlayerCartController;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

import java.io.IOException;

public class PlayerCart extends Button {
    private final PlayerCartController playerCartController;

    public PlayerCart(Player player, Runnable onActionCallback) {
        FXMLLoader loader = new FXMLLoader(AddTournamentCart.class.getResource(FXMLNavigator.PLAYER_CART.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            playerCartController = loader.getController();

            setCardDetails(player, onActionCallback);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCardDetails(Player player, Runnable onActionCallback) {
        if (playerCartController != null) {
            playerCartController.setData(player, onActionCallback);
        }
    }

}
