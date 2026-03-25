package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.shared.navigation.FXMLNavigator;

public class AddTournamentController extends javafx.scene.control.Button {
    @FXML
    private void selectTournament() {
        App.setRoot(FXMLNavigator.CREATE_TOURNAMENT);
    }
}
