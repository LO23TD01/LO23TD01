package ihmTable.controller;

import java.io.IOException;
import java.util.Optional;

import data.State;
import data.User;
import data.client.InterImplDataTable;
import ihmTable.controller.CollapsiblePanelController.Position;
import ihmTable.util.PlayerWaitingAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class TableController {

	private static final String EXIT_GAME_ALERT_HEADER = "Partie en cours";
	private static final String EXIT_GAME_ALERT_CONTENT = "Vous allez quitter une partie en cours.\nVoulez-vous continuer ?";
	private static final double PANELS_PERCENTAGE = 0.25;

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

		initChat();
		initRules();
		initTableCenter();
		initBottom();
		initMenu();

		if(this.interImplDataTable.getActualTable().getGameState().getState() == State.PRESTART) {
			//Waiting for other players
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
	private void initTableCenter() throws IOException {
		FXMLLoader tableCenterLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/TableCenter.fxml"));
		setPosition(tableCenterLoader.load(), Position.center);
		TableCenterController tableCenterController = (TableCenterController) tableCenterLoader.getController();
		tableCenterController.setData(interImplDataTable, user);
	}

	// PlayerStats view's initialization
	private AnchorPane initPlayerStats() throws IOException {
		FXMLLoader playerStatsLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/PlayerStats.fxml"));
		AnchorPane playerStats = playerStatsLoader.load();
		PlayerStatsController playerStatsController = playerStatsLoader.getController();
		playerStatsController.setData(interImplDataTable, user);
		return playerStats;
	}

	// GameStats view's initialization
	private AnchorPane initGameStats() throws IOException {
		FXMLLoader gameStatsLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/GameStats.fxml"));
		AnchorPane gameStats = gameStatsLoader.load();
		GameStatsController gameStatsController = gameStatsLoader.getController();
		gameStatsController.setData(interImplDataTable, user);
		return gameStats;
	}

	// Menu view's initialization
	private void initMenu() throws IOException {
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Menu.fxml"));
		setPosition(menuLoader.load(), Position.top);
		MenuController menuController = (MenuController) menuLoader.getController();
	}

	// Bottom view's initialization
	private void initBottom() throws IOException {
		AnchorPane bottomContainer = new AnchorPane();
		AnchorPane playerStats = initPlayerStats();
		AnchorPane gameStats = initGameStats();

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
