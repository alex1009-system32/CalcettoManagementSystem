package org.example.calcettomanagmentsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    static int i = 0;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        IO.println(i);
        i++;
    }
}
