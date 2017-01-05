package ihmTable.util;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import data.GameTable;
import data.Parameters;
import data.User;
import data.client.InterImplDataTable;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PlayerWaitingAlert extends Alert {

	private static final String PLAYERS_TEXT = "Joueurs : ";
	private static final String SPECTATORS_TEXT = "Spectateurs";
	private static final String CHAT_TEXT = "Chat";
	private static final String HEADER = "En attente";
	private static final String BUTTON_START_TEXT = "Démarrer";
	private static final String BUTTON_QUIT_TEXT = "Quitter";
	private static final String RULES_TEXT = "Règles : ";
	private static final String CHIPS_TEXT = "Jetons : ";

	private InterImplDataTable interImplDataTable;
	private GameTable gameTable;
	private User user;
	private Parameters parameters;
	private HashMap<UUID, Rectangle> playerViews;
	private ObservableList<User> players;
	private Stage stage;
	private VBox vBox;
	private HBox playersHBox;
	private ButtonType buttonStart;
	private ButtonType buttonQuit;
	private InvalidationListener listener;

	public PlayerWaitingAlert(InterImplDataTable interImplDataTable, User user, Stage stage) {
		super(AlertType.INFORMATION);
		this.initStyle(StageStyle.UNDECORATED);
		this.setHeaderText(HEADER);

		this.interImplDataTable = interImplDataTable;
		this.gameTable = interImplDataTable.getActualTable();
		this.user = user;
		this.parameters = gameTable.getParameters();
		this.players = gameTable.getPlayerList();
		this.stage = stage;
		this.playerViews = new HashMap<UUID, Rectangle>();

		//Create a lambda listener to perform a one shot listener
		this.listener = new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				start();
			}
		};
		this.gameTable.getGameState().stateProperty().addListener(listener);
		this.playersHBox = new HBox();
		this.vBox = new VBox(playersHBox);
		this.getDialogPane().setContent(vBox);
		displayTableParameters();
		initPlayersHBox();
		initButtons();
	}

	private void displayTableParameters() {
		addLabel(gameTable.getName().toUpperCase());
		addLabel(RULES_TEXT + parameters.getRules().getVariant().name());
		addLabel(CHIPS_TEXT + Integer.toString(parameters.getNbChip()));
		addCheckBox(CHAT_TEXT, parameters.isAuthorizeSpecToChat());
		addCheckBox(SPECTATORS_TEXT, parameters.isAuthorizeSpec());
		addLabel(PLAYERS_TEXT + parameters.getNbPlayerMin() + " à " + parameters.getNbPlayerMin());
	}

	private void addLabel(String text) {
		vBox.getChildren().add(new Label(text));
	}

	private void addCheckBox(String text, boolean isAuthorized) {
		CheckBox checkBox = new CheckBox(text);
		checkBox.setDisable(true);
		vBox.getChildren().add(checkBox);
		if (isAuthorized) {
			checkBox.setSelected(true);
		} else {
			checkBox.setIndeterminate(true);
		}
	}

	private void start() {
		gameTable.getGameState().stateProperty().removeListener(listener);
		if (user.isSame(gameTable.getCreator())) {
			this.interImplDataTable.launchGame();
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				close();
			}
		});
	}

	private void quit() {
		this.interImplDataTable.quitGame();
		this.stage.close();
	}

	private void initPlayersHBox() {
		for (User user : gameTable.getPlayerList()) {
			Rectangle rectangle = getPlayerAvatar(user);
			this.playersHBox.getChildren().add(rectangle);
			this.playerViews.put(user.getPublicData().getUUID(), rectangle);
		}

		ListChangeListener<User> playersChangeListener;
		playersChangeListener = change -> {
			while (change.next()) {
				if (change.wasAdded()) {
					for (User addedUser : change.getAddedSubList()) {
						Rectangle rectangle = getPlayerAvatar(addedUser);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								playersHBox.getChildren().add(rectangle);
							}
						});
						this.playerViews.put(addedUser.getPublicData().getUUID(), rectangle);
					}
				}
				if (change.wasRemoved()) {
					for (User removedUser : change.getRemoved()) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								playersHBox.getChildren()
										.remove(playerViews.remove(removedUser.getPublicData().getUUID()));
							}

						});
					}
				}
				if (user.isSame(gameTable.getCreator())) {
					this.getDialogPane().lookupButton(buttonStart)
							.setDisable(players.size() < parameters.getNbPlayerMin());
				}
			}
		};
		players.addListener(playersChangeListener);
	}

	private void initButtons() {
		this.buttonStart = new ButtonType(BUTTON_START_TEXT);
		this.buttonQuit = new ButtonType(BUTTON_QUIT_TEXT);
		if (user.isSame(gameTable.getCreator())) {
			this.getButtonTypes().setAll(buttonStart, buttonQuit);
			this.getDialogPane().lookupButton(buttonStart).setDisable(players.size() != parameters.getNbPlayerMin());
		} else {
			this.getButtonTypes().setAll(buttonQuit);
		}
		Optional<ButtonType> result = this.showAndWait();
		try {
			if (result.get() == buttonStart) {
				start();
			} else {
				quit();
			}
		} catch (NoSuchElementException noSuchElementException) {

		}
	}

	private Rectangle getPlayerAvatar(User user) {
		Rectangle rectangle = new Rectangle(20, 30);
		rectangle.setFill(new ImagePattern(Utility.getPlayerAvatar(user)));
		return rectangle;
	}
}
