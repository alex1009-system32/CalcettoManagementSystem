package org.example.calcettomanagmentsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.FxmlLocation;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;

public class createTournamentController {

    @FXML
	private TextField nameField;

	@FXML
	private TextField durationField;

	@FXML
	private TextField preRoundField;

	@FXML
	private TextField teamSizeField;

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
	protected void cancelTournament() {
		App.setRoot(FxmlLocation.SELECTTOURNAMENT);
	}

	@FXML
	protected void createTournament() {
		if (create()) {
			App.setRoot(FxmlLocation.SELECTTOURNAMENT);
		}
	}

}
