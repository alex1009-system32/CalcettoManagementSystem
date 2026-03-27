package org.example.calcettomanagmentsystem.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.example.calcettomanagmentsystem.ui.controller.components.PlayerCartController;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.shared.navigation.FXMLNavigator;

import java.io.IOException;

/**
 * Custom JavaFX component representing a player in the UI.
 * <p>
 * This card-like component displays player details and provides 
 * actions like deletion.
 * </p>
 *
 * @author Senior Developer
 */
public class PlayerCart extends Button {
    /** The controller managing the visual elements of this player card. */
    private final PlayerCartController playerCartController;

    /**
     * Constructs a new PlayerCart for the specified player.
     *
     * @param player The {@link Player} data to display.
     * @param onActionCallback Callback function invoked after an action (like deletion) is performed.
     */
    public PlayerCart(Player player, Runnable onActionCallback) {
        FXMLLoader loader = new FXMLLoader(AddTournamentCart.class.getResource(FXMLNavigator.PLAYER_CART.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            playerCartController = loader.getController();

            setCardDetails(player, onActionCallback);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load PlayerCart component", e);
        }
    }

    /**
     * Updates the card's visual data.
     *
     * @param player The {@link Player} data to display.
     * @param onActionCallback Callback function for post-action updates.
     */
    public void setCardDetails(Player player, Runnable onActionCallback) {
        if (playerCartController != null) {
            playerCartController.setData(player, onActionCallback);
        }
    }
}
