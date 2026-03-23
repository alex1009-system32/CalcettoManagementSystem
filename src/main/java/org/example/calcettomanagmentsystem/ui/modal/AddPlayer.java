package org.example.calcettomanagmentsystem.ui.modal;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.calcettomanagmentsystem.ui.components.TournamentCart;
import org.example.calcettomanagmentsystem.ui.controller.modal.AddPlayerController;
import org.example.calcettomanagmentsystem.navigation.FXMLNavigator;

import java.io.IOException;

public class AddPlayer extends VBox {
    private final AddPlayerController addPlayerController;

    public AddPlayer(Stage stage, Runnable onSaveCallback) {
        FXMLLoader loader = new FXMLLoader(TournamentCart.class.getResource(FXMLNavigator.ADD_PLAYER.getPath()));
        loader.setRoot(this);

        try {
            loader.load();
            this.addPlayerController = loader.getController();

            setCardDetails(stage, onSaveCallback);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCardDetails(Stage stage, Runnable onEditRequest) {
        if (addPlayerController != null) {
            addPlayerController.setData(stage, onEditRequest);
        }
    }

}
