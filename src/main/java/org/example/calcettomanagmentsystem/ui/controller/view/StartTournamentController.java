package org.example.calcettomanagmentsystem.ui.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.ui.components.PlayerCart;
import org.example.calcettomanagmentsystem.ui.modal.AddPlayer;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.shared.navigation.FXMLNavigator;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.net.URL;
import java.util.ResourceBundle;


public class StartTournamentController implements Initializable {
    @FXML
    private Label tournamentNameLabel;

    @FXML
    private Label preRoundLabel;

    @FXML
    private Label currentRoundLabel;

    @FXML
    private Label maxTeamSizeLabel;

    @FXML
    private FlowPane playerFlowPane;

    public void update() {
        tournamentNameLabel.setText(ServiceManager.getTournament().name());
        preRoundLabel.setText(String.valueOf(ServiceManager.getTournament().preRound()));
        currentRoundLabel.setText(String.valueOf(ServiceManager.getTournament().currentRound()));
        maxTeamSizeLabel.setText(String.valueOf(ServiceManager.getTournament().maxTeamSize()));

        playerFlowPane.getChildren().clear();

        for (Player player : ServiceManager.getPlayerService().findAllByTournament(ServiceManager.getTournament())) {
            PlayerCart playerCart = new PlayerCart(player, this::update);
            playerFlowPane.getChildren().add(playerCart);
        }

    }

    private void renderModal(Stage stage) {
        Stage modalStage = new Stage();

        modalStage.initOwner(stage);

        AddPlayer root = new AddPlayer(modalStage, this::update);
        Scene scene = new Scene(root);

        modalStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setScene(scene);
        modalStage.setResizable(false);
        modalStage.showAndWait();
    }

    @FXML
    private void openModal() {
        renderModal((Stage) tournamentNameLabel.getScene().getWindow());
    }

    @FXML
    private void startTournament() {
        ServiceManager.getMakerService().generateTeams(ServiceManager.getTournament());
        ServiceManager.getMakerService().generateNextMatches(ServiceManager.getTournament());
        App.setRoot(FXMLNavigator.ROUND_TOURNAMENT);
    }

    @FXML
    private void goBack() {
        App.setRoot(FXMLNavigator.SELECT_TOURNAMENT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (ServiceManager.getTournament() == null) App.setRoot(FXMLNavigator.SELECT_TOURNAMENT);
        update();
    }
}
