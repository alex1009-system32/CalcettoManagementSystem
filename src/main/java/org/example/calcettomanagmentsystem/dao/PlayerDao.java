package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface PlayerDao {
	void addPlayer(String pname, String pemail, Tournament tournament);

	List<Player> getAllPlayers();

	List<Player> getAllPlayersFromTournament(Tournament tournament);

	Player getPlayerById(int pid);
}
