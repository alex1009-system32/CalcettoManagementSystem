package org.example.calcettomanagmentsystem.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a team participating in a tournament.
 * A team has a unique id, a display name, and a roster of players.
 */
public class Team {

	private int tid;
	private String teamName;

	private final List<Player> players;

	/**
	 * Creates a new team with the given id and name.
	 *
	 * @param tid unique team identifier
	 * @param teamName team display name
	 */
	public Team(int tid, String teamName) {
		this.players = new ArrayList<Player>();

		setTid(tid);
		setTeamName(teamName);
	}

	/**
	 * Adds a player to the team roster.
	 *
	 * @param player player to add
	 */
	public void addPlayer(Player player) {
		players.add(player);
	}

	/**
	 * Checks whether the team currently contains the given player.
	 *
	 * @param player player to check
	 * @return true if the player is on the roster, false otherwise
	 */
	public boolean containsPlayer(Player player) {
		return players.contains(player);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Team team = (Team) o;
		return getTid() == team.getTid() && Objects.equals(getTeamName(), team.getTeamName()) && Objects.equals(players, team.players);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTid(), getTeamName(), players);
	}

	/**
	 * Removes a player from the team roster.
	 *
	 * @param player player to remove
	 */
	public void removePlayer(Player player) {
		players.remove(player);
	}

	/**
	 * Returns the team identifier.
	 *
	 * @return team id
	 */
	public int getTid() {
		return tid;
	}

	/**
	 * Returns the team display name.
	 *
	 * @return team name
	 */
	public String getTeamName() {
		return teamName;
	}

	private void setTid(int tid) {
		this.tid = tid;
	}

	private void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public String toString() {
		return "Team{" + "tid=" + tid + ", teamName='" + teamName + '\'' + ", players=" + Arrays.toString(players.toArray()) + '}';
	}
}
