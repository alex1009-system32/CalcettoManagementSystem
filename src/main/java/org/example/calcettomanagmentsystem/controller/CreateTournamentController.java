package org.example.calcettomanagmentsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

public class CreateTournamentController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField durationField;
    @FXML
    private TextField preRoundField;
    @FXML
    private TextField teamSizeField;

    @FXML
    private Label errorLabel;

    @FXML
    private void cancelTournament() {
        App.setRoot(FXMLNavigator.SELECT_TOURNAMENT);
    }
    @FXML
    private void createTournament() {
        try {
            ServiceManager.getTournamentService().create(nameField.getText(), Integer.parseInt(durationField.getText()), Integer.parseInt(preRoundField.getText()), 2);
            App.setRoot(FXMLNavigator.SELECT_TOURNAMENT);
        } catch (ValidationException e) {
            errorLabel.setText(e.getMessage());
        } catch (NumberFormatException _) {
            errorLabel.setText("Please enter valid numbers for duration and rounds.");
            errorLabel.setVisible(true);
        }
    }

}
