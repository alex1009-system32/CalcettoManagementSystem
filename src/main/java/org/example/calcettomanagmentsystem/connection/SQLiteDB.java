package org.example.calcettomanagmentsystem.connection;

import org.example.calcettomanagmentsystem.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.navigation.SQLScheamNavigation;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Zentrale Verbindungsschicht für SQLite.
 * <p>
 * Die Klasse bündelt Verbindungsaufbau und Initialisierung, um einen
 * konsistenten Zugriffspfad auf die Datenbank zu gewährleisten.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0
 */
public class SQLiteDB implements DataBaseSource {
    /**
     * Konfiguration aus {@code db.properties}, damit die URL austauschbar bleibt.
     */
    private static final Properties properties = new Properties();
    /**
     * Singleton-Verbindung, um eine einheitliche Datenbank-Session zu nutzen.
     */
    private static java.sql.Connection connection;

    static {
        try (InputStream inputStream = SQLiteDB.class.getResourceAsStream("/org/example/calcettomanagmentsystem/config/db.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("Properties file not found!");
            }

            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Liefert eine wiederverwendbare Verbindung zur konfigurierten SQLite-Instanz.
     *
     * @return aktive Verbindung zur Datenbank
     * @throws SQLException wenn der Verbindungsaufbau fehlschlägt
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            return DriverManager.getConnection(properties.getProperty("db.sqlite.url"));
        }
        return connection;
    }

    /**
     * Initialisiert das Schema für produktive Laufzeiten.
     * <p>
     * Das Ziel ist eine definierte Datenbankstruktur beim ersten Start der App.
     * </p>
     *
     * @throws SQLException wenn das Schema nicht ausgeführt werden kann
     */
    public void init() {
        try (Statement statement = this.getConnection().createStatement()) {
            statement.executeUpdate(SQLReader.readFile(SQLScheamNavigation.SETUP));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialisiert das Schema inklusive Beispieldaten für Tests und Demos.
     *
     * @throws SQLException wenn das Test-Schema nicht ausgeführt werden kann
     */
    public void initTest(){
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(SQLReader.readFile(SQLScheamNavigation.TEST_DB));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
