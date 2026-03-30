package org.example.calcettomanagmentsystem.ui.controller.components;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

/**
 * Controller for the {@link org.example.calcettomanagmentsystem.ui.components.MatchPane} component.
 * <p>
 * This class manages a layout pane for multiple match cards.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 */
public class MatchPaneController {
    /** The flow pane used to layout match cards. */
    @FXML
    private FlowPane flowPane;

    /**
     * Retrieves the underlying flow pane.
     *
     * @return The {@link FlowPane} instance.
     */
    public FlowPane getFlowPane() {
        return flowPane;
    }
}
