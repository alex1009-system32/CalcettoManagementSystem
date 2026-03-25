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
import org.example.calcettomanagmentsystem.shared.navigation.FXMLNavigator;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class RoundTournamentController implements Initializable {

    @FXML
    private TabPane matchOfRoundPane;

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

    private void openModal(Match match) {
        renderModal((Stage) matchOfRoundPane.getScene().getWindow(), match);
    }

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

    @FXML
    private void nextRound() {
        ServiceManager.getMakerService().generateNextMatches(ServiceManager.getTournament());
    }

    @FXML
    private void goBack() {
        App.setRoot(FXMLNavigator.SELECT_TOURNAMENT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
}
