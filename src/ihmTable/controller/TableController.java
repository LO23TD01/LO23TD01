package ihmTable.controller;

import java.io.IOException;

import data.State;
import data.User;
import data.client.InterImplDataTable;
import ihmTable.controller.CollapsiblePanelController.Position;
import ihmTable.util.PlayerWaitingAlert;
import ihmTable.util.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TableController {

	public static final double PANELS_PERCENTAGE = 0.20;

	@FXML
	private BorderPane tableView;

	@FXML
	private BorderPane tableCenterView;

	private InterImplDataTable interImplDataTable;
	private User user;

	public void setData(InterImplDataTable interImplDataTable, User user) throws IOException {
		this.interImplDataTable = interImplDataTable;
		this.user = user;

		Utility.bindPrefProperties(tableCenterView, tableView.widthProperty().multiply(100 - 2 * PANELS_PERCENTAGE), tableView.heightProperty());
		initRules();
		Pane bottomContainer = initBottom();
		PlayerStatsController playerStatsController = initPlayerStats(bottomContainer);
		initGameStats(bottomContainer);
		initTableCenter(playerStatsController);
		initChat(playerStatsController);
		initMenu();

		//Waiting for other players in prestart state
		if(this.interImplDataTable.getActualTable().getGameState().getState() == State.PRESTART) {
			new PlayerWaitingAlert(interImplDataTable, user, (Stage) tableView.getScene().getWindow());
		}
	}

	// Chat view's initialization
	private void initChat(PlayerStatsController playerStatsController) throws IOException {
		FXMLLoader chatLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Chat.fxml"));
		setPosition(getCollapsiblePane(chatLoader.load(), Position.right), Position.right);
		ChatController chatController = (ChatController) chatLoader.getController();
		chatController.setData(interImplDataTable, user, playerStatsController);
	}

	// Rules view's initialization
	private void initRules() throws IOException {
		setPosition(getCollapsiblePane(FXMLLoader.load(getClass().getResource("/ihmTable/resources/view/Rules.fxml")), Position.left), Position.left);
	}

	// TableCenterView view's initialization
	private void initTableCenter(PlayerStatsController playerStatsController) throws IOException {
		FXMLLoader tableCenterLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/TableCenter.fxml"));
		Pane tableCenter = tableCenterLoader.load();
		setPosition(tableCenter, Position.center);
		TableCenterController tableCenterController = (TableCenterController) tableCenterLoader.getController();
		tableCenterController.setData(interImplDataTable, user, playerStatsController);
	}

	// PlayerStats view's initialization
	private PlayerStatsController initPlayerStats(Pane parent) throws IOException {
		FXMLLoader playerStatsLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/PlayerStats.fxml"));
		Pane playerStats = playerStatsLoader.load();
		AnchorPane.setLeftAnchor(playerStats, 0.0);
		parent.getChildren().add(playerStats);
		Utility.bindPrefProperties(playerStats, parent.widthProperty().multiply(0.5), parent.heightProperty());
		PlayerStatsController playerStatsController = playerStatsLoader.getController();
		playerStatsController.setData(interImplDataTable, user);
		return playerStatsController;
	}

	// GameStats view's initialization
	private void initGameStats(Pane parent) throws IOException {
		FXMLLoader gameStatsLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/GameStats.fxml"));
		Pane gameStats = gameStatsLoader.load();
		AnchorPane.setRightAnchor(gameStats, 0.0);
		parent.getChildren().add(gameStats);
		Utility.bindPrefProperties(gameStats, parent.widthProperty().multiply(0.5), parent.heightProperty());
		GameStatsController gameStatsController = gameStatsLoader.getController();
		gameStatsController.setData(interImplDataTable);
	}

	// Menu view's initialization
	private void initMenu() throws IOException {
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Menu.fxml"));
		Pane menu = menuLoader.load();
		setPosition(menu, Position.top);
		MenuController menuController = (MenuController) menuLoader.getController();
		menuController.setData(interImplDataTable);
	}

	// Bottom view's initialization
	private Pane initBottom() throws IOException {
		AnchorPane bottomContainer = new AnchorPane();
		setPosition(getCollapsiblePane(bottomContainer, Position.bottom), Position.bottom);
		return bottomContainer;
	}

	private void setPosition(Pane pane, Position position) {
		switch (position) {
		case left:
			tableView.setLeft(pane);
			break;
		case right:
			tableView.setRight(pane);
			break;
		case bottom:
			tableCenterView.setBottom(pane);
			break;
		case top:
			tableCenterView.setTop(pane);
			break;
		case center:
			tableCenterView.setCenter(pane);
			break;
		default:
			break;
		}
	}

	private Pane getCollapsiblePane(Pane pane, Position position) throws IOException {
		FXMLLoader collapsiblePanelLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/CollapsiblePanel.fxml"));
		Pane panel = collapsiblePanelLoader.load();
		CollapsiblePanelController panelController = (CollapsiblePanelController) collapsiblePanelLoader.getController();
		if (position == Position.left || position == Position.right) {
			panelController.setContent(pane, position, tableView);
		} else {
			panelController.setContent(pane, position, tableCenterView);
		}
		return panel;
	}
}
