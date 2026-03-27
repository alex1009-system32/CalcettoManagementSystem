package org.example.calcettomanagmentsystem.ui.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.shared.exceptions.ValidationException;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

/**
 * Controller for the 'Add Player' modal dialog.
 * <p>
 * This class manages the input form for adding a new player to the active tournament,
 * handles validation errors, and triggers a UI update upon successful creation.
 * </p>
 *
 * @author Senior Developer
 */
public class AddPlayerController {
    /** Label for displaying validation error messages. */
    @FXML
    private Label errorLabel;
    /** Text field for the player's name input. */
    @FXML
    private TextField nameField;
    /** Text field for the player's email input. */
    @FXML
    private TextField emailField;

    /** The modal's stage instance, used for closing the window. */
    private Stage stage;
    /** Callback to refresh the parent view after a successful save. */
    private Runnable onSaveCallback;

    /**
     * Initializes the controller with the modal stage and refresh callback.
     *
     * @param stage The {@link Stage} of the modal.
     * @param onSaveCallback The callback function to run after saving.
     */
    public void setData(Stage stage, Runnable onSaveCallback) {
        this.stage = stage;
        this.onSaveCallback = onSaveCallback;
    }

    /**
     * Handles the player creation process.
     * Validates input, persists the new player, and closes the modal on success.
     */
    @FXML
    private void create() {
        try {
            ServiceManager.getPlayerService()
                    .create(nameField.getText(), emailField.getText(), ServiceManager.getTournament());

            if (onSaveCallback != null) {
                onSaveCallback.run();
            }

            cancel();
        } catch (ValidationException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    /**
     * Closes the modal window without saving.
     */
    @FXML
    private void cancel() {
        stage.close();
    }
}
