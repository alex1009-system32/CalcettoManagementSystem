package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

/**
 * Zugriffskontrakt für Match-Daten mit Fokus auf Turnierbezug.
 * <p>
 * Der Zweck ist eine klar definierte Schnittstelle zwischen
 * Match-Logik und Persistenz.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.dao.impl.SQLiteMatchDao
 */
public interface MatchDao {
	/**
	 * Legt ein Match im Kontext eines Turniers an, damit Runden
	 * nachvollziehbar persistiert werden.
	 *
	 * @param tournament Turnierkontext, der die Runde und Zugehörigkeit vorgibt
	 * @return erzeugtes Match zur weiteren Verknüpfung
	 */
	Match addMatch(Tournament tournament);

	/**
	 * Verknüpft ein Team mit einem Match, um die Teilnahme festzuhalten.
	 *
	 * @param team Team, das am Match beteiligt ist
	 * @param match Ziel-Match für die Verknüpfung
	 * @return {@code true} bei erfolgreicher Speicherung
	 */
	boolean addTeamToMatch(Team team,  Match match);

	/**
	 * Speichert die Punkte eines Teams für ein Match, um Auswertungen zu ermöglichen.
	 *
	 * @param team Team, dessen Punkte gesetzt werden
	 * @param match Match, zu dem die Punkte gehören
	 * @param point Punktewert für das Team
	 * @return {@code true} bei erfolgreicher Aktualisierung
	 */
	boolean addAddPointToTeamInMatch(Team team,  Match match,double point);

	/**
	 * Lädt alle Matches eines Turniers für vollständige Roundtrip-Analysen.
	 *
	 * @param tournament Turnierfilter
	 * @return Liste aller Matches des Turniers
	 */
	List<Match> getAllMatchesFromTournament(Tournament tournament);

	/**
	 * Lädt alle Matches einer spezifischen Runde, um Zwischenstände zu berechnen.
	 *
	 * @param tournament Turnierfilter
	 * @param round Runde, deren Matches benötigt werden
	 * @return Matches der gewählten Runde
	 */
	List<Match> getAllMatchesFromTournamentInRound(Tournament tournament, int round);

	/**
	 * Lädt alle Matches eines Teams, um Verlauf und Historie zu liefern.
	 *
	 * @param team Teamfilter
	 * @return Matches mit Beteiligung des Teams
	 */
	List<Match> getAllMatchesFromTeam(Team team);

	/**
	 * Liefert ein Match anhand seiner ID für Detailansichten.
	 *
	 * @param mid Match-ID
	 * @return Match oder {@code null}, wenn nicht vorhanden
	 */
	Match getMatchById(int mid);

	/**
	 * Entfernt ein Match aus der Persistenz, wenn es nicht mehr gültig ist.
	 *
	 * @param match Match, das entfernt werden soll
	 * @return {@code true} bei erfolgreicher Löschung
	 */
	boolean deleteMatch(Match match);
}
