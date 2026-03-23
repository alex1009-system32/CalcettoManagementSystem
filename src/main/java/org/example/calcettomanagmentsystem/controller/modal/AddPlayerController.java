package org.example.calcettomanagmentsystem.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.util.function.Consumer;

public class AddPlayerController {
    @FXML
    private Label errorLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    private Stage stage;
    private Runnable onSaveCallback;

    public void setData(Stage stage, Runnable onSaveCallback) {
        this.stage = stage;
        this.onSaveCallback = onSaveCallback;
    }

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

    @FXML
    private void cancel() {
        stage.close();
    }
}
