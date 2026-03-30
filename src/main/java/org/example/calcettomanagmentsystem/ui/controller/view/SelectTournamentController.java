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

/**
 * Controller for the 'Select Tournament' view.
 * <p>
 * This class displays a list of available tournaments as cards
 * and includes an option to create a new tournament.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class SelectTournamentController implements Initializable {

    /** Flow pane container used to layout and display individual tournament cards. */
	@FXML
	private FlowPane tournamentFlowPane;

    /**
     * Updates the user interface by fetching all existing tournaments from the service 
     * and rendering a {@link TournamentCart} for each, followed by an {@link AddTournamentCart}.
     */
	private void update() {
		for (Tournament tournament : ServiceManager.getTournamentService().findAll()) {
            TournamentCart tournamentCart = new TournamentCart(tournament);
            tournamentFlowPane.getChildren().add(tournamentCart);
		}

        AddTournamentCart addTournamentCart = new AddTournamentCart();
        tournamentFlowPane.getChildren().add(addTournamentCart);
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
