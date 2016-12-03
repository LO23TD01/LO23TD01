package ihmTable.controller;

import java.io.IOException;

import data.GameTable;
import ihmTable.controller.CollapsiblePanelController.Position;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class TableController {

	private static final double  PANELS_PERCENTAGE = 0.25;

	@FXML
	private BorderPane tableView;

	@FXML
	private BorderPane tableCenterView;

	public GameTable gameTableInstance;

	public void initialize() throws IOException {

		//Initialisation des Panels et de la vue
	    setPosition(getCollapsiblePane(FXMLLoader.load(getClass().getResource("/ihmTable/resources/view/Chat.fxml")), Position.right), Position.right);
        setPosition(getCollapsiblePane(FXMLLoader.load(getClass().getResource("/ihmTable/resources/view/Rules.fxml")), Position.left), Position.left);

        setPosition(FXMLLoader.load(getClass().getResource("/ihmTable/resources/view/TableCenterView.fxml")), Position.center);

        AnchorPane bottomContainer = new AnchorPane();
        AnchorPane playerStats = FXMLLoader.load(getClass().getResource("/ihmTable/resources/view/PlayerStats.fxml"));
        AnchorPane gameStats = FXMLLoader.load(getClass().getResource("/ihmTable/resources/view/GameStats.fxml"));
        AnchorPane.setLeftAnchor(playerStats, 0.0);
        AnchorPane.setRightAnchor(gameStats, 0.0);
        bottomContainer.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                playerStats.setPrefWidth(newValue.doubleValue() / 2);
                gameStats.setPrefWidth(newValue.doubleValue() / 2);
            }
        });
        bottomContainer.getChildren().addAll(playerStats, gameStats);
        setPosition(getCollapsiblePane(bottomContainer, Position.bottom), Position.bottom);

        //todo passer gametabelInstance à chaque sous controller.

	}

	private void setPosition(AnchorPane anchorPane, Position position) {
		switch (position) {
		case left:
			tableView.setLeft(anchorPane);
			break;
		case right:
			tableView.setRight(anchorPane);
			break;
		case bottom:
			tableCenterView.setBottom(anchorPane);
			break;
		case top:
			tableCenterView.setTop(anchorPane);
			break;
		case center:
			tableCenterView.setCenter(anchorPane);
			break;
		default:
			break;
		}
	}

	private AnchorPane getCollapsiblePane(AnchorPane anchorPane, Position position) throws IOException {
		FXMLLoader collapsiblePanelLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/CollapsiblePanel.fxml"));
		AnchorPane panel = collapsiblePanelLoader.load();

		CollapsiblePanelController panelController = (CollapsiblePanelController) collapsiblePanelLoader.getController();
		if(position == Position.left || position == Position.right) {
			panelController.setContent(anchorPane, position, tableView.getPrefWidth() * PANELS_PERCENTAGE);
		} else {
			panelController.setContent(anchorPane, position, tableView.getPrefHeight() * PANELS_PERCENTAGE);
		}
		return panel;
	}
}
