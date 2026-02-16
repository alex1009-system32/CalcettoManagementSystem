package org.example.calcettomanagmentsystem.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQLiteDB {
	private static final Properties properties = new Properties();
	private static java.sql.Connection connection;

	private static String setup = """
			create table IF not exists tournament (
			  tid integer primary key,
			    tournament_name TEXT,
			    start_date TEXT,
			    duration integer
			);
			create table IF not exists team (
			    tid integer primary key,
			    team_name TEXT,
			    trid integer,
			    foreign key(trid) references tournament(tid)
			);
			create table IF not exists player (
			    pid integer primary key,
			    pname TEXT,
			    pemail TEXT unique,
			    tid integer,
			    foreign key(tid) references team(tid)
			);
			create table IF not exists match (
			    mid integer primary key,
			    round integer,
			    tid integer,
			    foreign key(tid) references tournament(tid)
			);
			create table IF not exists team_match (
			    tid integer,
			    mid integer,
			    points real,
			    primary key(tid, mid),
			    foreign key(tid) references team(tid),
			    foreign key(mid) references match(mid)
			);
			""";

	private static String testSetup = """
			drop table IF exists team_match;
			drop table IF exists match;
			drop table IF exists player;
			drop table IF exists team;
			drop table IF exists tournament;
			
			create table IF not exists tournament
			(
					tid
					integer
					primary
					key,
					tournament_name
					TEXT,
					start_date
					TEXT,
					duration
					integer
			);
			create table IF not exists team
			(
					tid
					integer
					primary
					key,
					team_name
					TEXT,
					trid
					integer,
					foreign
					key
			(
					trid
			) references tournament
			(
					tid
			) );
			create table IF not exists player
			(
					pid
					integer
					primary
					key,
					pname
					TEXT,
					pemail
					TEXT
					unique,
					tid
					integer,
					foreign
					key
			(
					tid
			) references team
			(
					tid
			) );
			create table IF not exists match
			(
					mid
					integer
					primary
					key,
					round
					integer,
					tid
					integer,
					foreign
					key
			(
					tid
			) references tournament
			(
					tid
			) );
			create table IF not exists team_match
			(
					tid
					integer,
					mid
					integer,
					points
					real,
					primary
					key
			(
					tid,
					mid
			), foreign key
			(
					tid
			) references team
			(
					tid
			), foreign key
			(
					mid
			) references match
			(
					mid
			) );
			
			insert into tournament (tournament_name, start_date, duration)
			values ('Winter Cup 2024', '2024-01-15', 14),
			       ('Sommer Liga Pro', '2024-06-01', 30),
			       ('Charity Event', '2024-09-10', 2),
			       ('eSports Major', '2024-11-20', 7),
			       ('Regionale Meisterschaft', '2024-03-05', 5);
			insert into team (team_name, trid)
			values ('Die wilden Kerle', 1),
			       ('FC Datenbank', 1),
			       ('SQL Strikers', 2),
			       ('Python Panthers', 2),
			       ('Java Giants', 3);
			insert into player (pname, pemail, tid)
			values ('Max Mustermann', 'max@test.de', 1),
			       ('Erika Musterfrau', 'erika@test.de', 1),
			       ('John Doe', 'john.doe@example.com', 2),
			       ('Jane Smith', 'jane.s@web.de', 3),
			       ('Lukas Podolski', 'poldi@fussball.de', 4);
			insert into match (round, tid)
			values (1, 1),
			       (2, 1),
			       (1, 2),
			       (1, 3),
			       (1, 4);
			insert into team_match (tid, mid, points)
			values (1, 1, 3.0),
			       (2, 1, 0.0),
			       (3, 3, 1.5),
			       (4, 3, 1.5),
			       (5, 4, 3.0);
			""";

	private SQLiteDB() {
	}

	static {
		try (InputStream inputStream = DatabaseOld.class.getClassLoader().getResourceAsStream("org/example/calcettomanagmentsystem/config/db.properties")) {
			if (inputStream == null) {
				throw new RuntimeException("Properties file not found!");
			}

			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(properties.getProperty("db.sqlite.url"));
		}
		return connection;
	}

	public static void init() throws SQLException {
		Statement statement = getConnection().createStatement();
		statement.executeUpdate(setup);
		statement.close();

	}

	public static void initTest() throws SQLException {
		Statement statement = getConnection().createStatement();

		statement.executeUpdate(testSetup);
		statement.close();
	}
}
