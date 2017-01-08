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

/**
 * Controller of the table view
 */
public class TableController {

	/**
	 * Percentage of the panel
	 */
	public static final double PANELS_PERCENTAGE = 0.20;

	/**
	 * The main container of the table view
	 */
	@FXML
	private BorderPane tableView;

	/**
	 * The center container of the table view
	 */
	@FXML
	private BorderPane tableCenterView;

	/**
	 * The interface with data
	 *
	 * @see InterImplDataTable
	 */
	private InterImplDataTable interImplDataTable;
	/**
	 * The local user
	 *
	 * @see User
	 */
	private User user;

	/**
	 * Set the data of the controller
	 * @param interImplDataTable the interface with data
	 * @param user the local user
	 * @throws IOException
	 *
	 * @see TableController#interImplDataTable
	 * @see TableController#user
	 */
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

	/**
	 * Initialize the chat view
	 * @param playerStatsController the playerStatsController
	 * @throws IOException
	 *
	 * @see PlayerStatsController
	 */
	private void initChat(PlayerStatsController playerStatsController) throws IOException {
		FXMLLoader chatLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Chat.fxml"));
		setPosition(getCollapsiblePane(chatLoader.load(), Position.right), Position.right);
		ChatController chatController = (ChatController) chatLoader.getController();
		chatController.setData(interImplDataTable, user, playerStatsController);
	}

	/**
	 * Initialize the rules view
	 * @throws IOException
	 */
	private void initRules() throws IOException {
		setPosition(getCollapsiblePane(FXMLLoader.load(getClass().getResource("/ihmTable/resources/view/Rules.fxml")), Position.left), Position.left);
	}

	/**
	 * Initialize the table center view
	 * @param playerStatsController the playerStatsController
	 * @throws IOException
	 *
	 * @see PlayerStatsController
	 */
	private void initTableCenter(PlayerStatsController playerStatsController) throws IOException {
		FXMLLoader tableCenterLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/TableCenter.fxml"));
		Pane tableCenter = tableCenterLoader.load();
		setPosition(tableCenter, Position.center);
		TableCenterController tableCenterController = (TableCenterController) tableCenterLoader.getController();
		tableCenterController.setData(interImplDataTable, user, playerStatsController);
	}

	/**
	 * Initialize player stats view
	 * @param parent the parent of the payer stats view
	 * @return the PlayerStatsController
	 * @throws IOException
	 *
	 * @see PlayerStatsController
	 */
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

	/**
	 * Initialize game stats view
	 * @param parent the parent of the payer stats view
	 * @throws IOException
	 */
	private void initGameStats(Pane parent) throws IOException {
		FXMLLoader gameStatsLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/GameStats.fxml"));
		Pane gameStats = gameStatsLoader.load();
		AnchorPane.setRightAnchor(gameStats, 0.0);
		parent.getChildren().add(gameStats);
		Utility.bindPrefProperties(gameStats, parent.widthProperty().multiply(0.5), parent.heightProperty());
		GameStatsController gameStatsController = gameStatsLoader.getController();
		gameStatsController.setData(interImplDataTable);
	}

	/**
	 * Initialize the menu view
	 * @throws IOException
	 */
	private void initMenu() throws IOException {
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Menu.fxml"));
		Pane menu = menuLoader.load();
		setPosition(menu, Position.top);
		MenuController menuController = (MenuController) menuLoader.getController();
		menuController.setData(interImplDataTable);
	}

	/**
	 * Initialize the bottom panel
	 * @return the view of the bottom panel
	 * @throws IOException
	 */
	private Pane initBottom() throws IOException {
		AnchorPane bottomContainer = new AnchorPane();
		setPosition(getCollapsiblePane(bottomContainer, Position.bottom), Position.bottom);
		return bottomContainer;
	}

	/**
	 * Add the pane to tableView or tableCenterView according to its position
	 * @param pane the pane corresponding to the view
	 * @param position the position of the view
	 *
	 * @see TableController#tableView
	 * @see TableController#tableCenterView
	 */
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

	/**
	 * Return the view of a collapsible panel which include the given pane
	 * @param pane the pane corresponding to the view
	 * @param position the position of the panel
	 * @return The pane corresponding to the view
	 * @throws IOException
	 */
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
