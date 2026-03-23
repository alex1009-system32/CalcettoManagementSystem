package org.example.calcettomanagmentsystem.controller.components;

import javafx.fxml.FXML;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

public class AddTournamentController extends javafx.scene.control.Button {
    @FXML
    private void selectTournament() {
        App.setRoot(FXMLNavigator.CREATE_TOURNAMENT);
    }
}
