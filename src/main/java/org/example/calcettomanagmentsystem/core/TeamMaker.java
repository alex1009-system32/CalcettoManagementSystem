package org.example.calcettomanagmentsystem.core;

import com.github.javafaker.Faker;
import org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;
import java.util.stream.Gatherers;

public class TeamMaker {
	public void makeTeams(Tournament tournament) {
		List<Player> players = new SQLitePlayerDao().getAllPlayersFromTournament(tournament);

		List<List<Player>> teams = players.stream()
				.gather(Gatherers.windowFixed(tournament.getMaxTeamSize()))
				.toList();

		String teamName;
		Team team;
		SQLiteTeamDao teamDao = new SQLiteTeamDao();
		Faker faker = new Faker();

		for (List<Player> teamList : teams) {
			teamName = faker.funnyName().name();
			teamDao.addTeam(teamName);
			team = teamDao.getTeamByName(teamName);

			for (Player player : teamList) {
				teamDao.addPlayerToTeam(player, team);
			}
		}
	}
}
