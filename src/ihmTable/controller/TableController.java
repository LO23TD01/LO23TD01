package ihmTable.controller;

import java.io.IOException;

import data.User;
import data.client.InterImplDataTable;
import ihmTable.controller.CollapsiblePanelController.Position;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class TableController {

	private static final double PANELS_PERCENTAGE = 0.25;

	@FXML
	private BorderPane tableView;

	@FXML
	private BorderPane tableCenterView;

	private InterImplDataTable interImplDataTable;
	private User user;

	public void setData(InterImplDataTable interImplDataTable, User user) throws IOException {
		this.interImplDataTable = interImplDataTable;
		this.user = user;
		initChat();
		initRules();
		initTableCenter();
		initBottom();
	}

	// Chat view's initialization
	private void initChat() throws IOException {
		FXMLLoader chatLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Chat.fxml"));
		setPosition(getCollapsiblePane(chatLoader.load(), Position.right), Position.right);
		ChatController chatController = (ChatController) chatLoader.getController();
		chatController.setData(interImplDataTable, user);
	}

	// Rules view's initialization
	private void initRules() throws IOException {
		setPosition(getCollapsiblePane(FXMLLoader.load(getClass().getResource("/ihmTable/resources/view/Rules.fxml")), Position.left), Position.left);
	}

	// TableCenterView view's initialization
	private void initTableCenter() throws IOException {
		FXMLLoader tableCenterLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/TableCenter.fxml"));
		setPosition(tableCenterLoader.load(), Position.center);
		TableCenterController tableCenterController = (TableCenterController) tableCenterLoader.getController();
	}

	// PlayerStats view's initialization
	private AnchorPane initPlayerStats() throws IOException {
		FXMLLoader playerStatsLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/PlayerStats.fxml"));
		AnchorPane playerStats = playerStatsLoader.load();
		PlayerStatsController playerStatsController = playerStatsLoader.getController();
		playerStatsController.gameTableInstance = this.interImplDataTable.getActualTable();
		return playerStats;
	}

	// GameStats view's initialization
	private AnchorPane intGameStats() throws IOException {
		FXMLLoader gameStatsLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/GameStats.fxml"));
		AnchorPane gameStats = gameStatsLoader.load();
		GameStatsController gameStatsController = gameStatsLoader.getController();
		return gameStats;
	}

	// Bottom view's initialization
	private void initBottom() throws IOException {
		AnchorPane bottomContainer = new AnchorPane();
		AnchorPane playerStats = initPlayerStats();
		AnchorPane gameStats = intGameStats();

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

		CollapsiblePanelController panelController = (CollapsiblePanelController) collapsiblePanelLoader
				.getController();
		if (position == Position.left || position == Position.right) {
			panelController.setContent(anchorPane, position, tableView.getPrefWidth() * PANELS_PERCENTAGE);
		} else {
			panelController.setContent(anchorPane, position, tableView.getPrefHeight() * PANELS_PERCENTAGE);
		}
		return panel;
	}
}
