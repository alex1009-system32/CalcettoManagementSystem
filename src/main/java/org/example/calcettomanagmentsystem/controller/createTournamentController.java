package org.example.calcettomanagmentsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.navigation.FxmlNavigation;
import org.example.calcettomanagmentsystem.service.TournamentService;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

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
            nameField.getStyleClass().add("text-field-error");
            result = false;
        }

        if (durationField.getText().isEmpty()) {
            durationField.getStyleClass().add("text-field-error");
            result = false;
        }

        if (preRoundField.getText().isEmpty()) {
            preRoundField.getStyleClass().add("text-field-error");
            result = false;
        }

        if (!result) {
            return false;
        }

        ServiceManager.getTournamentService().create(nameField.getText(), Integer.parseInt(durationField.getText()), Integer.parseInt(preRoundField.getText()), 2);
        return true;
    }

    /**
     * Kehrt zur Turnierauswahl zurück, ohne Änderungen zu persistieren.
     */
    @FXML
    protected void cancelTournament() {
        App.setRoot(FxmlNavigation.SELECT_TOURNAMENT);
    }

    /**
     * Erstellt das Turnier und navigiert anschließend zur Auswahlansicht.
     */
    @FXML
    protected void createTournament() {
        if (create()) {
            App.setRoot(FxmlNavigation.SELECT_TOURNAMENT);
        }
    }

}
