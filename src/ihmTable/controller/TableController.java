package ihmTable.controller;

import java.io.IOException;

import ihmTable.controller.CollapsiblePanelController.Position;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class TableController {

	private static double leftRightPanelWidth = 300;
	private static double BottomPanelHeight = 200;
	private static double TopPanelHeight = 100;

	@FXML
	private BorderPane tableView;

	public void initialize() throws IOException {
		tableView.setRight(getCollapsiblePane("/ihmTable/resources/view/Chat.fxml", Position.right));
		tableView.setLeft(getCollapsiblePane("/ihmTable/resources/view/Rules.fxml", Position.left));
		//tableView.setBottom(getCollapsiblePane("/ihmTable/resources/view/PlayerStats.fxml", Position.bottom));
	}

	private AnchorPane getCollapsiblePane(String content, Position position) throws IOException {
		FXMLLoader panelLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/CollapsiblePanel.fxml"));
		AnchorPane panel = panelLoader.load();

		CollapsiblePanelController panelController = (CollapsiblePanelController) panelLoader.getController();

		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(content));
		if(position == Position.left || position == Position.right) {
			panelController.setCollapsiblePanel(anchorPane, position, leftRightPanelWidth);
		} else if (position == Position.bottom) {
			panelController.setCollapsiblePanel(anchorPane, position, BottomPanelHeight);
		}
		else
		{
			panelController.setCollapsiblePanel(anchorPane, position, BottomPanelHeight);
		}
		return panel;
	}
}
