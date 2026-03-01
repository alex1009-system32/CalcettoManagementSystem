package org.example.calcettomanagmentsystem.model;

import java.util.*;

/**
 * Domänenobjekt für ein Match innerhalb eines Turniers.
 * <p>
 * Hält Teams und Punktstände zusammen, um Ergebnisse nachvollziehbar zu machen.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.model.Team
 */
public class Match {
	/**
	 * Punkte pro Team zur Ergebnisberechnung.
	 */
	private final Map<Team, Double> points;
	/**
	 * Teilnehmende Teams für die Rundenlogik.
	 */
	private final List<Team> teams;

	/**
	 * Primärschlüssel zur eindeutigen Identifikation.
	 */
	private int mid;
	/**
	 * Runde, zu der das Match gehört.
	 */
	private int round;

	/**
	 * Erstellt ein Match mit Identität und Rundenbezug.
	 *
	 * @param mid eindeutige Match-ID
	 * @param round Runde, zu der das Match gehört
	 */
	public Match(int mid, int round) {
		this.points = new HashMap<>();
		this.teams = new ArrayList<>();

		setMid(mid);
		setRound(round);
	}

	/**
	 * Fügt ein Team zum Match hinzu, um die Paarung zu definieren.
	 *
	 * @param team teilnehmendes Team
	 */
	public void addTeam(Team team) {
		this.teams.add(team);
	}

	/**
	 * Setzt den Punktestand eines Teams für dieses Match.
	 *
	 * @param team Team mit Ergebnis
	 * @param points Punktestand für das Team
	 */
	public void addPoints(Team team, double points) {
		this.points.put(team, points);
	}

	/**
	 * Liefert die Runde, zu der das Match gehört.
	 *
	 * @return Rundennummer
	 */
	public int getRound() {
		return round;
	}

	/**
	/**
	 * Liefert die Match-ID für Referenzen.
	 *
	 * @return Match-ID
	 */
	public int getMid() {
		return mid;
	}

	/**
	 * Liefert die Teamliste für Darstellung und Auswertung.
	 *
	 * @return Teams im Match
	 */
	public List<Team> getTeams() {
		return teams;
	}

	/**
	 * Liefert die Punktverteilung für Auswertungen.
	 *
	 * @return Map der Team-Punkte
	 */
	public Map<Team, Double> getPoints() {
		return points;
	}

	/**
	 * Setzt die Runde intern, um Konsistenz zu wahren.
	 *
	 * @param round Rundennummer
	 */
	private void setRound(int round) {
		this.round = round;
	}

	/**
	 * Setzt die Match-ID intern, um Konsistenz zu wahren.
	 *
	 * @param mid Match-ID
	 */
	private void setMid(int mid) {
		this.mid = mid;
	}

	/**
	 * Vergleicht Matches anhand Identität, Runde und Ergebnisdaten.
	 *
	 * @param o Vergleichsobjekt
	 * @return {@code true}, wenn die relevanten Felder übereinstimmen
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Match match = (Match) o;
		return getMid() == match.getMid() && getRound() == match.getRound() && Objects.equals(points, match.points) && Objects.equals(teams, match.teams);
	}

	/**
	 * Erzeugt einen Hash für Collections und Caches.
	 *
	 * @return Hashcode basierend auf Matchdaten
	 */
	@Override
	public int hashCode() {
		return Objects.hash(points, teams, getMid(), getRound());
	}

	/**
	 * Liefert eine lesbare Darstellung für Logs und Debugging.
	 *
	 * @return textuelle Repräsentation des Matches
	 */
	@Override
	public String toString() {
		return "Match{" +
				"points=" + points +
				", teams=" + teams +
				", mid=" + mid +
				", round=" + round +
				'}';
	}
}
