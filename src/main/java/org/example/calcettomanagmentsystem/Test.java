package org.example.calcettomanagmentsystem;

import com.github.javafaker.Faker;
import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.SQLException;
import java.util.List;

public class Test {

	static void main() throws InterruptedException{

		while (true) {
			IO.println(new Faker().funnyName().name());
			Thread.sleep(100);
		}

	}

}
