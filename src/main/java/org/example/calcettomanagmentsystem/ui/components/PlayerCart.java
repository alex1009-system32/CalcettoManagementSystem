package org.example.calcettomanagmentsystem.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.example.calcettomanagmentsystem.ui.controller.components.PlayerCartController;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.shared.navigation.FXMLNavigator;

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
