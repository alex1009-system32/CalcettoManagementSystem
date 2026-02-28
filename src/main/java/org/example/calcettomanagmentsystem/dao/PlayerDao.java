package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

/**
 * Zugriffskontrakt für Spieler mit Turnierbezug.
 * <p>
 * Dient der Entkopplung zwischen UI/Logik und Persistenzzugriff.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao
 */
public interface PlayerDao {
	/**
	 * Persistiert einen Spieler im Kontext eines Turniers, um Zuordnung zu ermöglichen.
	 *
	 * @param pname Anzeigename des Spielers
	 * @param pemail Kontaktadresse zur eindeutigen Identifikation
	 * @param tournament Turnierkontext für die Zugehörigkeit
	 * @return erstellter Spieler mit persistierter ID
	 */
	Player addPlayer(String pname, String pemail, Tournament tournament);

	/**
	 * Lädt alle Spieler für Übersichten und Verwaltung.
	 *
	 * @return Liste aller Spieler
	 */
	List<Player> getAllPlayers();

	/**
	 * Lädt alle Spieler eines Turniers für Turnierverwaltung.
	 *
	 * @param tournament Turnierfilter
	 * @return Spieler des Turniers
	 */
	List<Player> getAllPlayersFromTournament(Tournament tournament);

	/**
	 * Sucht einen Spieler für Detailansicht oder Verknüpfung.
	 *
	 * @param pid Spieler-ID
	 * @return Spieler oder {@code null}, wenn nicht vorhanden
	 */
	Player getPlayerById(int pid);

	/**
	 * Entfernt einen Spieler, z. B. bei Abmeldung oder Datenkorrektur.
	 *
	 * @param player Spieler, der entfernt werden soll
	 * @return {@code true} bei erfolgreicher Löschung
	 */
	boolean deletePlayer(Player player);
}
