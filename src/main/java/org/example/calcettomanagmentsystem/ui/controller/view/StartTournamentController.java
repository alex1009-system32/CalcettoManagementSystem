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
import org.example.calcettomanagmentsystem.service.ValidationException;
import org.example.calcettomanagmentsystem.ui.components.PlayerCart;
import org.example.calcettomanagmentsystem.ui.modal.AddPlayer;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for the 'Start Tournament' view.
 * <p>
 * This class displays tournament overview and allows for player registration
 * before teams and initial matches are generated.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 */
public class StartTournamentController implements Initializable {
    /** Label for the tournament name. */
    @FXML
    private Label tournamentNameLabel;

    /** Label for the number of preliminary rounds. */
    @FXML
    private Label preRoundLabel;

    /** Label for the current round number. */
    @FXML
    private Label currentRoundLabel;

    /** Label for the maximum players per team. */
    @FXML
    private Label maxTeamSizeLabel;

    /** Flow pane for displaying registered player cards. */
    @FXML
    private FlowPane playerFlowPane;

    /**
     * Updates the UI elements with current tournament and player data.
     */
    public void update() {
        try {

            preRoundLabel.setText(String.valueOf(ServiceManager.getTournament().preRound()));
            tournamentNameLabel.setText(ServiceManager.getTournament().name());
            currentRoundLabel.setText(String.valueOf(ServiceManager.getTournament().currentRound()));
            maxTeamSizeLabel.setText(String.valueOf(ServiceManager.getTournament().maxTeamSize()));

            playerFlowPane.getChildren().clear();

            for (Player player : ServiceManager.getPlayerService().findAllByTournament(ServiceManager.getTournament())) {
                PlayerCart playerCart = new PlayerCart(player, this::update);
                playerFlowPane.getChildren().add(playerCart);
            }
        } catch (ValidationException e) {
            Label errorLabel = new Label(e.getMessage());
            playerFlowPane.getChildren().add(errorLabel);
        }

    }

    /**
     * Configures and displays the modal window for adding new players.
     *
     * @param stage The parent stage.
     */
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

    /**
     * Opens the modal window for adding players.
     */
    @FXML
    private void openModal() {
        renderModal((Stage) tournamentNameLabel.getScene().getWindow());
    }

    /**
     * Finalizes the tournament setup by generating teams and matches,
     * then navigates to the round management view.
     */
    @FXML
    private void startTournament() {
        try {
            ServiceManager.getMakerService().generateTeams(ServiceManager.getTournament());
            ServiceManager.getMakerService().generateNextMatches(ServiceManager.getTournament());
            App.setRoot(FXMLNavigator.ROUND_TOURNAMENT);
        } catch (ValidationException e) {
            // todo
        }
    }

    /**
     * Navigates back to the tournament selection view.
     */
    @FXML
    private void goBack() {
        App.setRoot(FXMLNavigator.SELECT_TOURNAMENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (ServiceManager.getTournament() == null) App.setRoot(FXMLNavigator.SELECT_TOURNAMENT);
        update();
    }
}
