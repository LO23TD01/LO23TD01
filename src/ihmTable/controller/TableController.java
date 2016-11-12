package ihmTable.controller;

import java.io.IOException;

import ihmTable.controller.CollapsiblePanelController.Position;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class TableController {

	private static double leftRightPanelWidth = 300;
	private static double topBottomPanelHeight = 100;

	@FXML
	private BorderPane tableView;

	public void initialize() throws IOException {
		tableView.setRight(getCollapsiblePane("/ihmTable/resources/view/Chat.fxml", Position.right));
		tableView.setLeft(getCollapsiblePane("/ihmTable/resources/view/Rules.fxml", Position.left));
		Pane p = new Pane();
		tableView.setCenter(p);
	}

	private AnchorPane getCollapsiblePane(String content, Position position) throws IOException {
		FXMLLoader panelLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/CollapsiblePanel.fxml"));
		AnchorPane panel = panelLoader.load();

		CollapsiblePanelController panelController = (CollapsiblePanelController) panelLoader.getController();

		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(content));
		if(position == Position.left || position == Position.right) {
			panelController.setCollapsiblePanel(anchorPane, position, leftRightPanelWidth);
		} else {
			panelController.setCollapsiblePanel(anchorPane, position, topBottomPanelHeight);
		}
		return panel;
	}
}
