package org.example.calcettomanagmentsystem;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;

import java.sql.SQLException;

public class Test {

    static void main() {

        try {

            SQLiteDB.initTest();

            // ToDo: Its a bug, it don't createst the DB
            IO.println(SQLiteDB.getTestSetup());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
