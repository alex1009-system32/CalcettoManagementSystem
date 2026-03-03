package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

/**
 * Zugriffskontrakt für Turnierverwaltung mit Fokus auf Persistenzgrenzen.
 * <p>
 * Stellt eine stabile API für CRUD-Operationen bereit.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao
 */
public interface TournamentDao {
	/**
	 * Persistiert ein Turnier, damit es über die UI wiederauffindbar ist.
	 *
	 * @param tournament_name Anzeigename des Turniers
	 * @param duration Dauer in Tagen als Planungsgrundlage
	 * @param preRound Anzahl der Vorrunden für die Turnierlogik
	 * @param maxTeamSize Maximale Teamgröße zur Team-Bildung
	 * @return erstelltes Turnier mit ID
	 */
	Tournament addTournament(Tournament tournament);

	/**
	 * Erhöht die aktuelle Runde, um den Turnierfortschritt zu persistieren.
	 *
	 * @param tournament Turnier, dessen Fortschritt aktualisiert wird
	 * @return {@code true} bei erfolgreicher Aktualisierung
	 */
	boolean increaseRound(Tournament tournament);

	/**
	 * Lädt alle Turniere für Übersichten und Auswahl.
	 *
	 * @return Liste der Turniere
	 */
	List<Tournament> getAllTournaments();

	/**
	 * Sucht ein Turnier für Detailansichten und Folgeoperationen.
	 *
	 * @param tid Turnier-ID
	 * @return Turnier oder {@code null}, wenn nicht vorhanden
	 */
	Tournament getTournamentById(int tid);

	/**
	 * Entfernt ein Turnier, wenn es nicht mehr benötigt wird.
	 *
	 * @param tournament Turnier, das gelöscht werden soll
	 * @return {@code true} bei erfolgreicher Löschung
	 */
	boolean deleteTournament(Tournament tournament);
}
