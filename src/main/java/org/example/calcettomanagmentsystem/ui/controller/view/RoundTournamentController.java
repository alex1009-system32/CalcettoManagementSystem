package org.example.calcettomanagmentsystem.ui.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.calcettomanagmentsystem.App;
import org.example.calcettomanagmentsystem.ui.components.MatchCart;
import org.example.calcettomanagmentsystem.ui.components.MatchPane;
import org.example.calcettomanagmentsystem.ui.modal.MatchModal;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.ui.FXMLNavigator;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;


/**
 * Controller for the 'Round Tournament' view.
 * <p>
 * This class manages the display of matches grouped by rounds using a {@link TabPane}.
 * It facilitates navigating through rounds and opening match detail modals.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class RoundTournamentController implements Initializable {

    /** TabPane containing match listings for each round. */
    @FXML
    private TabPane matchOfRoundPane;

    /**
     * Updates the UI by loading all matches for the active tournament 
     * and grouping them into tabs by round number.
     */
    private void update() {
        List<Match> matchList =
                ServiceManager.getMatchService().findMatchesByTournament(ServiceManager.getTournament());

        List<List<Match>> gorupedByMatchList = matchList.stream()
                                                        .collect(Collectors.groupingBy(Match::round,
                                                                                       TreeMap::new,
                                                                                       Collectors.toList()))
                                                        .values()
                                                        .stream()
                                                        .toList();

        matchOfRoundPane.getTabs().clear();

        for (List<Match> list : gorupedByMatchList) {
            MatchPane matchPane = new MatchPane();

            for (Match match : list) {

                MatchCart matchCart = new MatchCart(match, (currentMatch) -> {
                    openModal(currentMatch);
                });

                matchPane.getFlowPane().getChildren().add(matchCart);
            }

            Tab tab = new Tab();
            tab.setText("Round " + list.getFirst().round());
            tab.setContent(matchPane);

            matchOfRoundPane.getTabs().add(tab);
        }
    }

    /**
     * Helper method to initiate modal display for a specific match.
     *
     * @param match The {@link Match} instance to show.
     */
    private void openModal(Match match) {
        renderModal((Stage) matchOfRoundPane.getScene().getWindow(), match);
    }

    /**
     * Configures and displays the match details modal.
     *
     * @param stage The parent stage.
     * @param match The match data.
     */
    private void renderModal(Stage stage, Match match) {
        Stage modalStage = new Stage();

        modalStage.initOwner(stage);

        Scene scene = new Scene(new MatchModal(modalStage, match));

        modalStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setScene(scene);
        modalStage.setResizable(false);
        modalStage.showAndWait();
    }

    /**
     * Triggers the generation of the next set of matches for the tournament.
     */
    @FXML
    private void nextRound() {
        ServiceManager.getMakerService().generateNextMatches(ServiceManager.getTournament());
    }

    /**
     * Navigates back to the tournament selection view.
     */
    @FXML
    private void goBack() {
        App.setRoot(FXMLNavigator.SELECT_TOURNAMENT);
    }

    /**
     * Initializes the controller class.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
}
