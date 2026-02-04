package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.connection.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDaoClient {

    public void addUser(String name) {
        String sql = "INSERT INTO client (cname) VALUES ( ? )";

        try (Connection connection = Database.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TestClient> findAll() {
        List<TestClient> list = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try(Connection connection = Database.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                list.add(new TestClient(
                        resultSet.getInt("cid"),
                        resultSet.getString("cname")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
