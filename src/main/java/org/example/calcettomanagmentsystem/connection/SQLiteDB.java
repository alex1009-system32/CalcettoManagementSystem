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
            CREATE TABLE IF NOT EXISTS match (
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
                FOREIGN KEY(mid) REFERENCES match(mid)
            );
            """;

     private static String testSetup = """
            DROP TABLE IF EXISTS team_match;
            DROP TABLE IF EXISTS match;
            DROP TABLE IF EXISTS player;
            DROP TABLE IF EXISTS team;
            DROP TABLE IF EXISTS tournament;
            
            CREATE TABLE IF NOT EXISTS tournament
            (
            		tid
            		INTEGER
            		PRIMARY
            		KEY,
            		tournament_name
            		TEXT,
            		start_date
            		TEXT,
            		duration
            		INTEGER
            );
            CREATE TABLE IF NOT EXISTS team
            (
            		tid
            		INTEGER
            		PRIMARY
            		KEY,
            		team_name
            		TEXT,
            		trid
            		INTEGER,
            		FOREIGN
            		KEY
            (
            		trid
            ) REFERENCES tournament
            (
            		tid
            ) );
            CREATE TABLE IF NOT EXISTS player
            (
            		pid
            		INTEGER
            		PRIMARY
            		KEY,
            		pname
            		TEXT,
            		pemail
            		TEXT
            		UNIQUE,
            		tid
            		INTEGER,
            		FOREIGN
            		KEY
            (
            		tid
            ) REFERENCES team
            (
            		tid
            ) );
            CREATE TABLE IF NOT EXISTS match
            (
            		mid
            		INTEGER
            		PRIMARY
            		KEY,
            		round
            		INTEGER,
            		tid
            		INTEGER,
            		FOREIGN
            		KEY
            (
            		tid
            ) REFERENCES tournament
            (
            		tid
            ) );
            CREATE TABLE IF NOT EXISTS team_match
            (
            		tid
            		INTEGER,
            		mid
            		INTEGER,
            		points
            		REAL,
            		PRIMARY
            		KEY
            (
            		tid,
            		mid
            ), FOREIGN KEY
            (
            		tid
            ) REFERENCES team
            (
            		tid
            ), FOREIGN KEY
            (
            		mid
            ) REFERENCES match
            (
            		mid
            ) );

            INSERT INTO tournament (tournament_name, start_date, duration)
            VALUES ('Winter Cup 2024', '2024-01-15', 14),
                   ('Sommer Liga Pro', '2024-06-01', 30),
                   ('Charity Event', '2024-09-10', 2),
                   ('eSports Major', '2024-11-20', 7),
                   ('Regionale Meisterschaft', '2024-03-05', 5);
            INSERT INTO team (team_name, trid)
            VALUES ('Die wilden Kerle', 1),
                   ('FC Datenbank', 1),
                   ('SQL Strikers', 2),
                   ('Python Panthers', 2),
                   ('Java Giants', 3);
            INSERT INTO player (pname, pemail, tid)
            VALUES ('Max Mustermann', 'max@test.de', 1),
                   ('Erika Musterfrau', 'erika@test.de', 1),
                   ('John Doe', 'john.doe@example.com', 2),
                   ('Jane Smith', 'jane.s@web.de', 3),
                   ('Lukas Podolski', 'poldi@fussball.de', 4);
            INSERT INTO match (round, tid)
            VALUES (1, 1),
                   (2, 1),
                   (1, 2),
                   (1, 3),
                   (1, 4);
            INSERT INTO team_match (tid, mid, points)
            VALUES (1, 1, 3.0),
                   (2, 1, 0.0),
                   (3, 3, 1.5),
                   (4, 3, 1.5),
                   (5, 4, 3.0);
            """;

    private SQLiteDB() {
    }

    static {
        try (InputStream inputStream = DatabaseOld.class.getClassLoader().getResourceAsStream("db.properties")) {
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
            connection = DriverManager.getConnection(
                    properties.getProperty("db.sqlite.url")
            );
        }
        return connection;
    }

    public static void init() throws SQLException {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(setup);
            statement.close();
        }
    }

    public static void initTest() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        for (String sql : testSetup.split(";")) {
            statement.execute(sql);
        }
    }

    public static String getTestSetup() {
        return testSetup;
    }
}
