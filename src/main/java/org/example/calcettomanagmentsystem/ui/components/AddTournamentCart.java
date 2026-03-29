package org.example.calcettomanagmentsystem.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;

import java.io.IOException;

/**
 * Custom JavaFX component representing the 'Add Tournament' button.
 * <p>
 * This component acts as a trigger for creating a new tournament, 
 * typically displayed as a card in a gallery view.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class AddTournamentCart extends Button {
    /**
     * Constructs a new AddTournamentCart by loading its FXML definition.
     */
    public AddTournamentCart() {
        FXMLLoader loader = new FXMLLoader(
                AddTournamentCart.class.getResource(FXMLNavigator.ADD_TOURNAMENT_CART.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load AddTournamentCart component", e);
        }
    }
}
