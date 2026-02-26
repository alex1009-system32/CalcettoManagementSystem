package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.dao.impl.SQLiteMatchDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public class MatchMaker {

	public void makePreRounds(Tournament tournament) {
		if (tournament.getCurrendRound() != 0) {
			return;
		}

		List<Team> teams = new SQLiteTeamDao().getAllTeamsFromTournament(tournament);
		SQLiteMatchDao sqliteMatchDao = new SQLiteMatchDao();

		for (int i = 0; i < tournament.getPreRound(); i++) {

			sqliteMatchDao.addMatch(tournament, i);



		}

	}

	public void makeMatchesForRound(Tournament tournament) {
	}

}
