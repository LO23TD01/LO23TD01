package ihmTable.controller;

import java.io.IOException;
import java.util.Optional;

import data.State;
import data.User;
import data.client.InterImplDataTable;
import ihmTable.controller.CollapsiblePanelController.Position;
import ihmTable.util.PlayerWaitingAlert;
import ihmTable.util.Utility;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class TableController {

	public static final double PANELS_PERCENTAGE = 0.20;
	public static final double MENU_HEIGHT_PERCENTAGE = 0.05;

	private static final String EXIT_GAME_ALERT_HEADER = "Partie en cours";
	private static final String EXIT_GAME_ALERT_CONTENT = "Vous allez quitter une partie en cours.\nVoulez-vous continuer ?";

	@FXML
	private BorderPane tableView;

	@FXML
	private BorderPane tableCenterView;

	private InterImplDataTable interImplDataTable;
	private User user;
	private Stage stage;

	public void setData(InterImplDataTable interImplDataTable, User user) throws IOException {
		this.interImplDataTable = interImplDataTable;
		this.user = user;
		this.stage = (Stage) tableView.getScene().getWindow();

		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		    	event.consume();
		    	setExitModal(stage);
		    }
		});

		Utility.bindPrefProperties(tableCenterView, tableView.widthProperty().multiply(100 - 2 * PANELS_PERCENTAGE), tableView.heightProperty());
		initChat();
		initRules();
		Pane bottomContainer = initBottom();
		PlayerStatsController playerStatsController = initPlayerStats(bottomContainer);
		initGameStats(bottomContainer);
		initTableCenter(playerStatsController);
		initMenu();

		//Waiting for other players in prestart state
		if(this.interImplDataTable.getActualTable().getGameState().getState() == State.PRESTART) {
			new PlayerWaitingAlert(interImplDataTable, user, stage);
		}
	}

	private void setExitModal(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setHeaderText(EXIT_GAME_ALERT_HEADER);
    	alert.setContentText(EXIT_GAME_ALERT_CONTENT);
    	alert.initStyle(StageStyle.UNDECORATED);

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		interImplDataTable.quitGame();
    		stage.close();
    	} else {
    		alert.close();
    	}
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
	private void initTableCenter(PlayerStatsController playerStatsController) throws IOException {
		FXMLLoader tableCenterLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/TableCenter.fxml"));
		Pane tableCenter = tableCenterLoader.load();
		Utility.bindPrefProperties(tableCenter, tableCenterView.widthProperty(), tableCenterView.heightProperty().multiply(100 - MENU_HEIGHT_PERCENTAGE - PANELS_PERCENTAGE));
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
		Utility.bindPrefProperties(menu, tableCenterView.widthProperty(), tableCenterView.heightProperty().multiply(MENU_HEIGHT_PERCENTAGE));
		setPosition(menu, Position.top);
		MenuController menuController = (MenuController) menuLoader.getController();
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
