package org.example.calcettomanagmentsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.FxmlLocation;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;

/**
 * Verantwortet die Turniererstellung über die Eingabemaske.
 * <p>
 * Ziel ist eine zentrale Validierung und Persistenz, damit die UI
 * keine Datenbankkenntnisse besitzen muss.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao
 */
public class createTournamentController {

	/**
	 * Feld für den Anzeigenamen des Turniers.
	 */
	@FXML
	private TextField nameField;

	/**
	 * Feld für die geplante Turnierdauer in Tagen.
	 */
	@FXML
	private TextField durationField;

	/**
	 * Feld für die Anzahl der Vorrunden.
	 */
	@FXML
	private TextField preRoundField;

	/**
	 * Feld für die Teamgröße; aktuell im UI deaktiviert.
	 */
	@FXML
	private TextField teamSizeField;

	/**
	 * Validiert Eingaben und persistiert das Turnier, falls alle Pflichtwerte vorhanden sind.
	 * <p>
	 * Die Intention ist eine frühe Rückmeldung im UI, bevor Persistenz ausgelöst wird.
	 * </p>
	 *
	 * @return {@code true} bei erfolgreicher Erstellung
	 * @throws NumberFormatException wenn numerische Felder nicht parsebar sind
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

	/**
	 * Kehrt zur Turnierauswahl zurück, ohne Änderungen zu persistieren.
	 */
	@FXML
	protected void cancelTournament() {
		App.setRoot(FxmlLocation.SELECT_TOURNAMENT);
	}

	/**
	 * Erstellt das Turnier und navigiert anschließend zur Auswahlansicht.
	 */
	@FXML
	protected void createTournament() {
		if (create()) {
			App.setRoot(FxmlLocation.SELECT_TOURNAMENT);
		}
	}

}
