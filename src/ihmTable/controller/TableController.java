package ihmTable.controller;

import java.io.IOException;

import ihmTable.controller.CollapsiblePanelController.Position;
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

	public void initialize() throws IOException {
		 setPosition(getCollapsiblePane("/ihmTable/resources/view/Chat.fxml", Position.right), Position.right);
		 setPosition(getCollapsiblePane("/ihmTable/resources/view/Rules.fxml", Position.left), Position.left);
		 setPosition(getCollapsiblePane("/ihmTable/resources/view/PlayerStats.fxml", Position.bottom), Position.bottom);
		 FXMLLoader centerPanelLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/TableCenterView.fxml"));
		 setPosition(centerPanelLoader.load(), Position.center);
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

	private AnchorPane getCollapsiblePane(String content, Position position) throws IOException {
		FXMLLoader collapsiblePanelLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/CollapsiblePanel.fxml"));
		AnchorPane panel = collapsiblePanelLoader.load();

		CollapsiblePanelController panelController = (CollapsiblePanelController) collapsiblePanelLoader.getController();
		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(content));
		if(position == Position.left || position == Position.right) {
			panelController.setContent(anchorPane, position, tableView.getPrefWidth() * PANELS_PERCENTAGE);
		} else {
			panelController.setContent(anchorPane, position, tableView.getPrefHeight() * PANELS_PERCENTAGE);
		}
		return panel;
	}
}
