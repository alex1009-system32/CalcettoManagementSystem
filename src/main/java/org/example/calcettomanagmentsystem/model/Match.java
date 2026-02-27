package org.example.calcettomanagmentsystem.model;

import java.util.*;

/**
 * Represents a match within a tournament.
 * A match belongs to a specific round and tracks participating teams and their points.
 */
public class Match {
	private final Map<Team, Double> points;
	private final List<Team> teams;

	private int mid;
	private int round;

	/**
	 * Creates a new match.
	 *
	 * @param mid unique match id
	 * @param round tournament round this match belongs to
	 */
	public Match(int mid, int round) {
		this.points = new HashMap<>();
		this.teams = new ArrayList<>();

		setMid(mid);
		setRound(round);
	}

	/**
	 * Adds a team to this match.
	 *
	 * @param team team participating in the match
	 */
	public void addTeam(Team team) {
		this.teams.add(team);
	}

	/**
	 * Records or updates the points for a team in this match.
	 *
	 * @param team team to score
	 * @param points points earned by the team
	 */
	public void addPoints(Team team, double points) {
		this.points.put(team, points);
	}

	/**
	 * Returns the round number.
	 *
	 * @return round
	 */
	public int getRound() {
		return round;
	}

	/**
	 * Returns the unique match id.
	 *
	 * @return match id
	 */
	public int getMid() {
		return mid;
	}

	private void setRound(int round) {
		this.round = round;
	}

	private void setMid(int mid) {
		this.mid = mid;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Match match = (Match) o;
		return getMid() == match.getMid() && getRound() == match.getRound() && Objects.equals(points, match.points) && Objects.equals(teams, match.teams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(points, teams, getMid(), getRound());
	}

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
