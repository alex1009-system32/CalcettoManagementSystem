package org.example.calcettomanagmentsystem.ui.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import org.example.calcettomanagmentsystem.ui.components.AddTournamentCart;
import org.example.calcettomanagmentsystem.ui.components.TournamentCart;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectTournamentController implements Initializable {

	@FXML
	private FlowPane tournamentFlowPane;

	private void update() {
		for (Tournament tournament : ServiceManager.getTournamentService().findAll()) {
            TournamentCart tournamentCart = new TournamentCart(tournament);
            tournamentFlowPane.getChildren().add(tournamentCart);
		}

        AddTournamentCart addTournamentCart = new AddTournamentCart();
        tournamentFlowPane.getChildren().add(addTournamentCart);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		update();
	}
}
