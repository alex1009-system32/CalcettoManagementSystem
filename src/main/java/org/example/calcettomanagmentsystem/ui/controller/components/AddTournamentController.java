package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;

/**
 * Controller for the {@link org.example.calcettomanagmentsystem.ui.components.AddTournamentCart} component.
 * <p>
 * This class handles the user interaction for initiating a new tournament creation.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class AddTournamentController extends javafx.scene.control.Button {
    /**
     * Handles the selection of the tournament creation action.
     * Redirects the user to the tournament creation view.
     */
    @FXML
    private void selectTournament() {
        App.setRoot(FXMLNavigator.CREATE_TOURNAMENT);
    }
}
