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

	// OLD DB Schema
	private static final String setup = """
			CREATE TABLE IF NOT EXISTS tournament (
			  tid INTEGER PRIMARY KEY,
			    tournament_name TEXT,
			    start_date TEXT,
			    duration INTEGER
			);
			CREATE TABLE IF NOT EXISTS team (
			    tid INTEGER PRIMARY KEY,
			    team_name TEXT,
			    trid INTEGER,
			    FOREIGN KEY(trid) REFERENCES tournament(tid)
			);
			CREATE TABLE IF NOT EXISTS player (
			    pid INTEGER PRIMARY KEY,
			    pname TEXT,
			    pemail TEXT UNIQUE,
			    tid INTEGER,
			    FOREIGN KEY(tid) REFERENCES team(tid)
			);
			CREATE TABLE IF NOT EXISTS MATCH (
			    mid INTEGER PRIMARY KEY,
			    round INTEGER,
			    tid INTEGER,
			    FOREIGN KEY(tid) REFERENCES tournament(tid)
			);
			CREATE TABLE IF NOT EXISTS team_match (
			    tid INTEGER,
			    mid INTEGER,
			    points REAL,
			    PRIMARY KEY(tid, mid),
			    FOREIGN KEY(tid) REFERENCES team(tid),
			    FOREIGN KEY(mid) REFERENCES MATCH(mid)
			);
			""";

	private static final String testSetup = """
			PRAGMA
            foreign_keys = ON;
            
            DROP TABLE IF EXISTS team_match;
            DROP TABLE IF EXISTS "match";
            DROP TABLE IF EXISTS player;
            DROP TABLE IF EXISTS team;
            DROP TABLE IF EXISTS tournament;
            
            CREATE TABLE IF NOT EXISTS tournament (
                    tid INTEGER PRIMARY KEY,
                    tournament_name TEXT NOT NULL,
                    start_date TEXT,
                    duration INTEGER NOT NULL,
                    pre_round INTEGER NOT NULL DEFAULT 3,
                    current_round INTEGER NOT NULL DEFAULT 1,
                    max_team_size INTEGER DEFAULT 2
                );
            CREATE TABLE IF NOT EXISTS team (
                    tid INTEGER PRIMARY KEY,
                    team_name TEXT
                );
            CREATE TABLE IF NOT EXISTS player (
                    pid INTEGER PRIMARY KEY,
                    pname TEXT,
                    pemail TEXT UNIQUE,
                    tid INTEGER,
                    trid INTEGER,
                    FOREIGN KEY ( tid ) REFERENCES team ( tid ),
                    FOREIGN KEY ( trid ) REFERENCES tournament ( tid )
                );
            CREATE TABLE IF NOT EXISTS "match" (
                    mid INTEGER PRIMARY KEY,
                    round INTEGER,
                    tid INTEGER,
                    FOREIGN KEY ( tid ) REFERENCES tournament ( tid )
                );
            CREATE TABLE IF NOT EXISTS team_match (
                    tid INTEGER,
                    mid INTEGER,
                    points REAL DEFAULT ( -1.0 ),
                    PRIMARY KEY ( tid, mid ),
                    FOREIGN KEY ( tid ) REFERENCES team ( tid ),
                    FOREIGN KEY ( mid ) REFERENCES "match" ( mid )
                );
            
            INSERT INTO tournament (tid, tournament_name, start_date, duration, pre_round, current_round, max_team_size) VALUES
                (1, 'Winter Open 2024', '2024-01-15', 7, 3, 3, 2),
                (2, 'Spring Invitational', '2024-03-10', 5, 2, 1, 2),
                (3, 'Summer Championship', '2024-06-20', 14, 4, 1, 2),
                (4, 'Fall Classic', '2024-09-05', 10, 3, 2, 2),
                (5, 'Midnight Scrims', '2024-10-31', 1, 1, 1, 2),
                (6, 'Pro League Season 1', '2025-01-01', 30, 5, 1, 2),
                (7, 'Amateur Trophy', '2025-02-15', 3, 2, 2, 2),
                (8, 'Global Masters', '2025-05-12', 20, 4, 1, 2),
                (9, 'Weekend Warrior Cup', '2025-07-04', 2, 2, 1, 2),
                (10, 'All-Stars Finale', '2025-12-12', 1, 1, 1, 2);
            INSERT INTO team (tid, team_name) VALUES
                (1, 'Cyber Knights'), (2, 'Data Wizards'), (3, 'SQL Stars'),
                (4, 'Byte Brawlers'), (5, 'Null Pointers'), (6, 'Query Queens'),
                (7, 'Logic Bombs'), (8, 'Bit Flippers'), (9, 'Cloud Runners'),
                (10, 'Alpha Testers'), (11, 'Binary Beasts'), (12, 'Syntax Errors'),
                (13, 'Kernel Panic'), (14, 'Default Values'), (15, 'Infinite Loops');
            INSERT INTO player (pid, pname, pemail, tid, trid) VALUES
                (1, 'Alice Smith', 'alice@example.com', 1, 1),
                (2, 'Bob Jones', 'bob@example.com', 1, 1),
                (3, 'Charlie Brown', 'charlie@example.com', 2, 1),
                (4, 'Diana Prince', 'diana@example.com', 2, 1),
                (5, 'Edward Norton', 'edward@example.com', 3, 2),
                (6, 'Fiona Gallagher', 'fiona@example.com', 3, 2),
                (7, 'George Miller', 'george@example.com', 4, 2),
                (8, 'Hannah Abbott', 'hannah@example.com', 4, 2),
                (9, 'Ian Wright', 'ian@example.com', 5, 3),
                (10, 'Jenny Slate', 'jenny@example.com', 5, 3),
                (11, 'Kevin Hart', 'kevin@example.com', 6, 3),
                (12, 'Laura Palmer', 'laura@example.com', 6, 3),
                (13, 'Mike Ross', 'mike@example.com', 7, 4),
                (14, 'Nina Simone', 'nina@example.com', 7, 4),
                (15, 'Oscar Wilde', 'oscar@example.com', 8, 4),
                (16, 'Peter Parker', 'peter@example.com', 8, 4),
                (17, 'Quinn Fabray', 'quinn@example.com', 9, 5),
                (18, 'Riley Reid', 'riley@example.com', 9, 5),
                (19, 'Steve Rogers', 'steve@example.com', 10, 5),
                (20, 'Tony Stark', 'tony@example.com', 10, 5);
            INSERT INTO "match" (mid, round, tid) VALUES
                (1, 1, 1), (2, 1, 1), (3, 2, 1), (4, 1, 2), (5, 1, 2),
                (6, 1, 3), (7, 2, 3), (8, 3, 3), (9, 1, 4), (10, 2, 4),
                (11, 1, 5), (12, 1, 6), (13, 1, 7), (14, 2, 7), (15, 1, 8);
            INSERT INTO team_match (tid, mid, points) VALUES
                (1, 1, 10.5), (2, 1, 8.0),
                (3, 2, 15.0), (4, 2, 12.5),
                (1, 3, 20.0), (3, 3, 18.5),
                (5, 4, 5.0),  (6, 4, 5.0),
                (7, 5, 0.0),  (8, 5, 25.0),
                (9, 6, 12.0), (10, 6, 14.5),
                (11, 12, 30.0), (12, 12, 28.0),
                (13, 13, 10.0), (14, 13, 10.0),
                (13, 14, 22.0), (15, 14, 21.5),
                (1, 15, 50.0),  (5, 15, 45.0);
			""";


	private SQLiteDB() {
	}

	static {
		try (InputStream inputStream = SQLiteDB.class.getResourceAsStream("/org/example/calcettomanagmentsystem/config/db.properties")) {

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
