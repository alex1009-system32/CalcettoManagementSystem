package org.example.calcettomanagmentsystem.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

import java.io.IOException;

public class AddTournamentCart extends Button {
    public AddTournamentCart() {
        FXMLLoader loader = new FXMLLoader(
                AddTournamentCart.class.getResource(FXMLNavigator.ADD_TOURNAMENT_CART.getPath()));
        System.out.println(AddTournamentCart.class.getResource(FXMLNavigator.ADD_TOURNAMENT_CART.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
