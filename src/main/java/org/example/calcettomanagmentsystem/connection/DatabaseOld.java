package org.example.calcettomanagmentsystem.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseOld {

	private static Properties properties = new Properties();
	private static java.sql.Connection connection;

	private DatabaseOld() {
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
			connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
		}
		return connection;
	}

}
