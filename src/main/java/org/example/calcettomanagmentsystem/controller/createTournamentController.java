package org.example.calcettomanagmentsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.FxmlLocation;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;

/**
 * Controller responsible for creating new tournaments through the form UI.
 * Handles input validation and database persistence for new tournament entities.
 */
public class createTournamentController {

	@FXML
	private TextField nameField;

	@FXML
	private TextField durationField;

	@FXML
	private TextField preRoundField;

	@FXML
	private TextField teamSizeField;

	/**
	 * Validates form fields and persists a new tournament if valid.
	 * Checks for empty fields and highlights them with error styling if necessary.
	 *
	 * @return true if creation succeeded and data was valid, false otherwise.
	 */
	private boolean create() {
		boolean result = true;

		if (nameField.getText().isEmpty()) {
			nameField.setStyle(
					"-fx-background-color: #fffafb; " +
					"-fx-border-color: #d63031;"
			);
			result = false;
		}

		if (durationField.getText().isEmpty()) {
			durationField.setStyle(
					"-fx-background-color: #fffafb; " +
					"-fx-border-color: #d63031;"
			);
			result = false;
		}

		if (preRoundField.getText().isEmpty()) {
			preRoundField.setStyle(
					"-fx-background-color: #fffafb; " +
					"-fx-border-color: #d63031;"
			);
			result = false;
		}

		/* Is Disabled
		if (teamSizeField.getText().isEmpty()) {
			teamSizeField.setStyle("-fx-background-color: #fffafb; " + "-fx-border-color: #d63031;");
			result = false;
		} */

		if (!result) {
			return false;
		}

		//ToDo: Need To make the Integer MUST BE A NUMBER!!!
		new SQLiteTournamentDao().addTournament(
				nameField.getText(),
				Integer.valueOf(durationField.getText()),
				Integer.valueOf(preRoundField.getText()),
				2
		);

		return true;

	}

	@FXML
	/**
	 * Navigates back to the tournament selection screen.
	 */
	protected void cancelTournament() {
		App.setRoot(FxmlLocation.SELECT_TOURNAMENT);
	}

	@FXML
	/**
	 * Attempts to create a tournament and navigates on success.
	 */
	protected void createTournament() {
		if (create()) {
			App.setRoot(FxmlLocation.SELECT_TOURNAMENT);
		}
	}

}
