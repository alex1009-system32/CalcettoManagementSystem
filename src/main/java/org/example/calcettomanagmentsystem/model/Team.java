package org.example.calcettomanagmentsystem.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Team {

	private int tid;
	private String teamName;

	private List<Player> players;

	public Team(int tid, String teamName) {
		this.players = new ArrayList<Player>();

		setTid(tid);
		setTeamName(teamName);
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

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

	public void removePlayer(Player player) {
		players.remove(player);
	}

	public int getTid() {
		return tid;
	}

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
