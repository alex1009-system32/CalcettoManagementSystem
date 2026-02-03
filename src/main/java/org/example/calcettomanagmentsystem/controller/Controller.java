package org.example.calcettomanagmentsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.calcettomanagmentsystem.dao.TestDaoClient;
import org.example.calcettomanagmentsystem.model.TestClient;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        TestDaoClient testDaoClient = new TestDaoClient();
        List<TestClient> list = testDaoClient.findAll();

        for (TestClient testClient : list) {
            System.out.println(testClient);
        }
    }
}
