package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

/**
 * Zugriffskontrakt für Teams und deren Spielerzuordnung.
 * <p>
 * Ziel ist eine klare Persistenzgrenze für Teamverwaltung.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao
 */
public interface TeamDao {
	/**
	 * Persistiert ein neues Team, damit es referenzierbar bleibt.
	 *
	 * @param teamname Anzeigename des Teams
	 * @return erstelltes Team mit ID
	 */
	Team addTeam(String teamname);

	/**
	 * Verknüpft einen Spieler mit einem Team, um die Zuordnung festzuhalten.
	 *
	 * @param player Spieler, der dem Team beitreten soll
	 * @param team Zielteam für die Zuordnung
	 * @return {@code true} bei erfolgreicher Aktualisierung
	 */
	boolean addPlayerToTeam(Player player, Team team);

	/**
	 * Lädt Teams eines Turniers für Bracket- oder Listenansichten.
	 *
	 * @param tournament Turnierfilter
	 * @return Teams des Turniers
	 */
	List<Team> getAllTeamsFromTournament(Tournament tournament);

	/**
	 * Sucht ein Team für Detailansichten oder Beziehungen.
	 *
	 * @param tid Team-ID
	 * @return Team oder {@code null}, wenn nicht vorhanden
	 */
	Team getTeamById(int tid);

	/**
	 * Sucht ein Team anhand des eindeutigen Namens.
	 *
	 * @param teamName eindeutiger Teamname
	 * @return Team oder {@code null}, wenn nicht vorhanden
	 */
	Team getTeamByName(String teamName);

	/**
	 * Entfernt ein Team, wenn es nicht mehr gültig ist.
	 *
	 * @param team Team, das gelöscht werden soll
	 * @return {@code true} bei erfolgreicher Löschung
	 */
	boolean deleteTeam(Team team);
}
