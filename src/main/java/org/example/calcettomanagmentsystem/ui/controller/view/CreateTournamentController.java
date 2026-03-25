package org.example.calcettomanagmentsystem.ui.controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.shared.exceptions.ValidationException;
import org.example.calcettomanagmentsystem.shared.navigation.FXMLNavigator;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

/**
 * FXML Controller class for the tournament creation view.
 * <p>
 * This controller handles user input for defining new tournament parameters
 * and interacts with {@link org.example.calcettomanagmentsystem.service.TournamentService}
 * to persist the new tournament.
 * </p>
 *
 * @author Senior Developer
 */
public class CreateTournamentController {
    /** Input field for the tournament name. */
    @FXML
    private TextField nameField;
    /** Input field for the tournament duration in days. */
    @FXML
    private TextField durationField;
    /** Input field for the number of preliminary rounds. */
    @FXML
    private TextField preRoundField;
    /** Input field for the maximum team size. */
    @FXML
    private TextField teamSizeField;

    /** Label for displaying validation or processing errors. */
    @FXML
    private Label errorLabel;

    /**
     * Navigates back to the tournament selection view without saving.
     */
    @FXML
    private void cancelTournament() {
        App.setRoot(FXMLNavigator.SELECT_TOURNAMENT);
    }

    /**
     * Validates input and creates a new tournament record.
     * <p>
     * On success, redirects to the tournament selection view.
     * On failure, displays an error message to the user.
     * </p>
     */
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
