package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.jetbrains.annotations.NotNull;

import javafx.scene.control.Label;

/**
 * Controller for the {@link org.example.calcettomanagmentsystem.ui.components.PlayerCart} component.
 * <p>
 * This class handles the display of player information on a card and
 * provides a deletion action.
 * </p>
 *
 * @author Senior Developer
 */
public class PlayerCartController {
    /** Label for the player's name. */
    @FXML
    private Label nameLabel;
    /** Label for the player's contact email. */
    @FXML
    private Label emailLabel;

    /** The player data being managed. */
    private Player player;
    /** Callback function to invoke after an action (like deletion). */
    private Runnable onActionCallback;

    /**
     * Populates the controller with player data and an action callback.
     *
     * @param player The {@link Player} instance.
     * @param onActionCallback The callback to execute after data changes.
     */
    public void setData(@NotNull Player player, Runnable onActionCallback) {
        this.onActionCallback = onActionCallback;
        this.player = player;

        nameLabel.setText(player.name());
        emailLabel.setText(player.email());
    }

    /**
     * Handles the deletion of the player from the database.
     * After deletion, it triggers the provided action callback.
     */
    @FXML
    private void handleDelete() {
        ServiceManager.getPlayerService().delete(player);
        if (onActionCallback != null) {
            onActionCallback.run();
        }
    }
}
