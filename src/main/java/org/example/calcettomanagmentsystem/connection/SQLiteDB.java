package org.example.calcettomanagmentsystem.connection;

import org.example.calcettomanagmentsystem.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.navigation.SQLSchemaNavigator;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class SQLiteDB implements DataBaseSource {
    private static final Properties properties = new Properties();
    private static java.sql.Connection connection;

    static {
        try (InputStream inputStream = SQLiteDB.class.getResourceAsStream(
                "/org/example/calcettomanagmentsystem/config/db.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("Properties file not found!");
            }

            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(properties.getProperty("db.sqlite.url"));
        }
        return connection;
    }

    public void init() {
        try (PreparedStatement preparedStatement = this.getConnection()
                .prepareStatement(SQLReader.readFile(SQLSchemaNavigator.SETUP))) {
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initTest() {
        try (PreparedStatement preparedStatement = this.getConnection()
                .prepareStatement(SQLReader.readFile(SQLSchemaNavigator.SETUP))) {
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
