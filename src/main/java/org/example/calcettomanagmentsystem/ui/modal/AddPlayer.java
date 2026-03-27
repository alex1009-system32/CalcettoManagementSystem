package org.example.calcettomanagmentsystem.ui.modal;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.ui.components.TournamentCart;
import org.example.calcettomanagmentsystem.ui.controller.modal.AddPlayerController;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;

import java.io.IOException;

/**
 * Custom JavaFX component for the 'Add Player' modal view.
 * <p>
 * This component provides the layout and data binding for adding a new player.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class AddPlayer extends VBox {
    /** The controller managing the visual logic of this modal. */
    private final AddPlayerController addPlayerController;

    /**
     * Constructs a new AddPlayer modal component.
     *
     * @param stage The {@link Stage} instance for the modal.
     * @param onSaveCallback Callback to execute after a player is successfully saved.
     */
    public AddPlayer(Stage stage, Runnable onSaveCallback) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.ADD_PLAYER.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            this.addPlayerController = loader.getController();

            setCardDetails(stage, onSaveCallback);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load AddPlayer modal component", e);
        }
    }

    /**
     * Initializes the modal's controller with required data.
     *
     * @param stage The modal's stage.
     * @param onEditRequest Callback for post-save updates.
     */
    public void setCardDetails(Stage stage, Runnable onEditRequest) {
        if (addPlayerController != null) {
            addPlayerController.setData(stage, onEditRequest);
        }
    }
}
