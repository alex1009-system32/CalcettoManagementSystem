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
                  tid SERIAL PRIMARY KEY,
                  tournament_name TEXT,
                  start_date TEXT,
                  duration INTEGER
              );
              
              CREATE TABLE IF NOT EXISTS team (
                  tid SERIAL PRIMARY KEY,
                  team_name TEXT,
                  trid INTEGER,
                  FOREIGN KEY(trid) REFERENCES tournament(tid)
              );
              
              CREATE TABLE IF NOT EXISTS player (
                  pid SERIAL PRIMARY KEY,
                  pname TEXT,
                  pemail TEXT UNIQUE,
                  tid INTEGER,
                  FOREIGN KEY(tid) REFERENCES team(tid)
              );
              
              CREATE TABLE IF NOT EXISTS match (
                  mid SERIAL PRIMARY KEY,
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

    private SQLiteDB() {
    }

    static{
        try (InputStream inputStream = DatabaseOld.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("Properties file not found!");
            }

            properties.load(inputStream);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(
                    properties.getProperty("db.sqlite.url")
            );
        }
        return connection;
    }

    public static void init() throws SQLException {
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(setup);
            statement.close();
        }
    }

}
