package org.example.calcettomanagmentsystem.modal;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.example.calcettomanagmentsystem.components.TournamentCart;
import org.example.calcettomanagmentsystem.controller.components.TournamentCartController;
import org.example.calcettomanagmentsystem.controller.modal.addPlayerController;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

import java.io.IOException;

public class AddPlayer extends VBox {
    private addPlayerController addPlayerController;

    public AddPlayer(Tournament tournament) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.TOURNAMENT_CART.getPath()));
        System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
        loader.setRoot(this);

        try {
            loader.load();
            this.addPlayerController = loader.getController();

            setCardDetails(tournament);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCardDetails() {
        if (addPlayerController != null) {
            addPlayerController.setData(tournament);
        }
    }

}
