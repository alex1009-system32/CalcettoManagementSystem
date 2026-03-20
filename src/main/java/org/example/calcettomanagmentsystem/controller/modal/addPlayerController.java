package org.example.calcettomanagmentsystem.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.util.function.Consumer;

public class addPlayerController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    private Stage stage;
    private Consumer<Stage> onEditRequested;
    @FXML
    public void create() {
        try {
            ServiceManager.getPlayerService()
                    .create(nameField.getText(), emailField.getText(), ServiceManager.getTournament());
            cancel();
        } catch (ValidationException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        }
    }

    public void setData(Stage stage, Consumer<Stage> onEditRequested) {
        this.stage = stage;
        this.onEditRequested = onEditRequested;
    }

    @FXML
    void cancel() {
        if(onEditRequested != null) {
            onEditRequested.accept(this.stage);
        }
    }
}
