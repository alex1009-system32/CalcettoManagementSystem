package org.example.calcettomanagmentsystem.connection.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataBaseSource {
    Connection getConnection() throws SQLException;
    void init();
}
