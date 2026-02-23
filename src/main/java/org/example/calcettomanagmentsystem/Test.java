package org.example.calcettomanagmentsystem;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.SQLException;
import java.util.List;

public class Test {

	static void main() throws SQLException {

		List teams =
				new SQLiteTeamDao()
				.getAllTeamsFromTournament(
					new SQLiteTournamentDao()
							.getTournamentById(1)
				);

		for (Object team : teams) {
			System.out.println(team);
		}

	}

}
