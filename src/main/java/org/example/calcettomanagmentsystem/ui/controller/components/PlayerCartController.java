package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.jetbrains.annotations.NotNull;

import javafx.scene.control.Label;

public class PlayerCartController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;

    private Player player;
    private Runnable onActionCallback;

    public void setData(@NotNull Player player, Runnable onActionCallback) {
        this.onActionCallback = onActionCallback;
        this.player = player;

        nameLabel.setText(player.name());
        emailLabel.setText(player.email());
    }

    @FXML
    private void handleDelete() {
        ServiceManager.getPlayerService().delete(player);
        if (onActionCallback != null) {
            onActionCallback.run();
        }
    }
}
