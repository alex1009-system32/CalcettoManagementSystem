package org.example.calcettomanagmentsystem.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQLiteDB {
    private static Properties properties = new Properties();
    private static java.sql.Connection connection;

    private static String setup = """
            CREATE IF NOT EXISTS tournament(
                tid SERIAL PRIMARY KEY,
                tournament name VARCHAR(192),
                
                startdate TEXT,
                duration INTEGER
            );
            
            CREATE IF NOT EXISTS team(
                tid SERIAL PRIMARY KEY,
                teamname VARCHAR(20),
                
                trid NUMERIC,
                
                FOREIGN KEY(trid) REFERENCES tournament(tid)
            );
            
            CREATE IF NOT EXISTS player(
                pid SERIAL PRIMARY KEY,
                pname VARCHAR(255),
                pemail VARCHAR(255) UNIQUE,
                
                tid NUMERIC,
                
                FOREIGN KEY(tid) REFERENCES team(tid)
            );
            
            CREATE IF NOT EXISTS player(
                mid  SERIAL PRIMARY KEY,
                round NUMERIC,
                
                tid NUMERIC,
                
                FOREIGN KEY(tid) REFERENCES tournament(tid)
            );
            
            CREATE IF NOT EXISTS team_match(
                tid NUMERIC,
                mid NUMERIC,
                
                points NUMERIC,
                
                FOREIGN KEY(tid) REFERENCES team(tid),
                FOREIGN KEY(mid) REFERENCES match(mid),
                
                PRIMARY KEY(tid, mid)
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
            Statement statement = connection.createStatement();
            statement.execute(setup);
        }
        return connection;
    }

}
