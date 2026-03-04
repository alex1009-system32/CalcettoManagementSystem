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
    private final int mid;
    private final int round;
    private final Tournament tournament;

    private final Map<Team, Double> teamResults;

    public Match(int mid, int round, Tournament tournament) {
        this.mid = mid;
        this.round = round;
        this.tournament = Objects.requireNonNull(tournament, "Tournament cannot be null");
        this.teamResults = new LinkedHashMap<>(); // LinkedHashMap bewahrt die Reihenfolge (Team 1 vs Team 2)
    }

    public Match(int round, Tournament tournament) {
        this.mid = -1;
        this.round = round;
        this.tournament = Objects.requireNonNull(tournament, "Tournament cannot be null");
        this.teamResults = new LinkedHashMap<>(); // LinkedHashMap bewahrt die Reihenfolge (Team 1 vs Team 2)
    }

    public void addTeamResult(Team team, double points) {
        this.teamResults.put(team, points);
    }



	/**
	 * Setzt den Punktestand eines Teams für dieses Match.
	 *
	 * @param team Team mit Ergebnis
	 * @param points Punktestand für das Team
	 */
	public void addPoints(Team team, double points) {
		this.teamResults.put(team, points);
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

    @Override
    public String toString() {
        return "Match{" +
                "mid=" + mid +
                ", round=" + round +
                ", tournament=" + tournament +
                ", teamResults=" + teamResults +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return mid == match.mid && round == match.round && Objects.equals(tournament,
                                                                          match.tournament) && Objects.equals(
                teamResults,
                match.teamResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid, round, tournament, teamResults);
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Map<Team, Double> getTeamResults() {
        return teamResults;
    }
}
