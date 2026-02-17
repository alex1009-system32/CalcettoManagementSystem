package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface TeamDao {
	void addTeam(String teamname);

	void addPlayerToTeam(Player player, Team team);

	List<Team> getAllTeamsFromTournament(Tournament tournament);

	Team getTeamById(int tid);
}
