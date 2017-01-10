package ihmTable.util;

import java.util.HashMap;
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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Class to display a dialog when waiting for players. Extends {@link Alert}
 *
 * @see Alert
 */
public class PlayerWaitingAlert extends Alert {

	/**
	 * The text for players
	 */
	private static final String PLAYERS_TEXT = "Joueurs : ";
	/**
	 * The text for spectators
	 */
	private static final String SPECTATORS_TEXT = "Spectateurs";
	/**
	 * The text for chat
	 */
	private static final String CHAT_TEXT = "Chat";
	/**
	 * The text for the title
	 */
	private static final String HEADER = "En attente";
	/**
	 * The text for start button
	 */
	private static final String BUTTON_START_TEXT = "Démarrer";
	/**
	 * The text for quit button
	 */
	private static final String BUTTON_QUIT_TEXT = "Quitter";
	/**
	 * The text for rules
	 */
	private static final String RULES_TEXT = "Règles : ";
	/**
	 * The text for chips
	 */
	private static final String CHIPS_TEXT = "Jetons : ";

	/**
	 * The interface with data
	 *
	 * @see InterImplDataTable
	 */
	private InterImplDataTable interImplDataTable;
	/**
	 * The game table
	 *
	 * @see GameTable
	 */
	private GameTable gameTable;
	/**
	 * The local user
	 *
	 * @see User
	 */
	private User user;
	/**
	 * The parameters of the table
	 *
	 * @see Parameters
	 */
	private Parameters parameters;
	/**
	 * Map containing all the players view
	 */
	private HashMap<UUID, Rectangle> playerViews;
	/**
	 * List containing all the players
	 */
	private ObservableList<User> players;
	/**
	 * The stage containing the table
	 *
	 * @see Stage
	 */
	private Stage stage;
	/**
	 * The main container for dialog content
	 */
	private VBox vBox;
	/**
	 * The container for players' avatar view
	 */
	private HBox playersHBox;
	/**
	 * The button start of the dialog
	 */
	private Button buttonStart;
	/**
	 * Listener to get any changes of the game state change
	 */
	private InvalidationListener listener;

	/**
	 * Create the waiting dialog
	 * @param interImplDataTable the interface with data
	 * @param user the local user
	 * @param stage the stage of the table
	 *
	 * @see PlayerWaitingAlert#interImplDataTable
	 * @see PlayerWaitingAlert#user
	 * @see PlayerWaitingAlert#stage
	 */
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
		this.playerViews = new HashMap<>();

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

	/**
	 * Display all the parameters of the table
	 *
	 * @see PlayerWaitingAlert#parameters
	 */
	private void displayTableParameters() {
		addLabel(gameTable.getName().toUpperCase());
		addLabel(RULES_TEXT + parameters.getRules().getVariant().name());
		addLabel(CHIPS_TEXT + Integer.toString(parameters.getNbChip()));
		addCheckBox(CHAT_TEXT, parameters.isAuthorizeSpecToChat());
		addCheckBox(SPECTATORS_TEXT, parameters.isAuthorizeSpec());
		addLabel(PLAYERS_TEXT + parameters.getNbPlayerMin() + " à " + parameters.getNbPlayerMax());
	}

	/**
	 * Add a text to the view
	 * @param text the text which should be added
	 */
	private void addLabel(String text) {
		vBox.getChildren().add(new Label(text));
	}

	/**
	 * Add a checkbox to the view
	 * @param text the text for the checkbox
	 * @param isAuthorized whether is the value authorized
	 */
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

	/**
	 * Action performed when the user click on start button
	 *
	 * @see PlayerWaitingAlert#buttonStart
	 */
	private void start() {
		gameTable.getGameState().stateProperty().removeListener(listener);
		if (user.isSame(gameTable.getCreator())) {
			this.interImplDataTable.launchGame();
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				getButtonTypes().add(ButtonType.CANCEL);
				close();
				getButtonTypes().remove(ButtonType.CANCEL);
			}
		});
	}

	/**
	 * Action performed when the user click on quit button
	 *
	 * @see PlayerWaitingAlert#buttonQuit
	 */
	private void quit() {
		this.interImplDataTable.quitGame();
		getButtonTypes().add(ButtonType.CANCEL);
		close();
		getButtonTypes().remove(ButtonType.CANCEL);
		this.stage.close();
	}

	/**
	 * Initialize the container of the players view and update it when a new player arrives or a player leaves
	 */
	private void initPlayersHBox() {
		for (User userUpdate : gameTable.getPlayerList()) {
			Rectangle rectangle = getPlayerAvatar(userUpdate);
			this.playersHBox.getChildren().add(rectangle);
			this.playerViews.put(userUpdate.getPublicData().getUUID(), rectangle);
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
								playersHBox.getChildren().remove(playerViews.remove(removedUser.getPublicData().getUUID()));
							}

						});
					}
				}
				if (user.isSame(gameTable.getCreator())) {
					this.buttonStart.setDisable(players.size() < parameters.getNbPlayerMin());
				}
			}
		};
		players.addListener(playersChangeListener);
	}

	/**
	 * Initialize the start button and quit button
	 *
	 * @see PlayerWaitingAlert#buttonStart
	 * @see PlayerWaitingAlert#buttonQuit
	 */
	private void initButtons() {
		HBox buttonsContainer = new HBox();
		buttonsContainer.setSpacing(10);
		buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
		if (user.isSame(gameTable.getCreator())) {
			this.buttonStart = new Button(BUTTON_START_TEXT);
			this.buttonStart.setDisable(true);
			this.buttonStart.setOnAction(event -> start());
			buttonsContainer.getChildren().add(buttonStart);
		}
		Button buttonQuit = new Button(BUTTON_QUIT_TEXT);
		buttonQuit.setOnAction(event -> quit());
		buttonsContainer.getChildren().add(buttonQuit);
		vBox.getChildren().add(buttonsContainer);
		this.getButtonTypes().setAll(new ButtonType[0]);
	}

	/**
	 * Return the given player's avatar
	 * @param user the player
	 * @return the avatar
	 */
	private Rectangle getPlayerAvatar(User user) {
		Rectangle rectangle = new Rectangle(20, 30);
		rectangle.setFill(new ImagePattern(Utility.getPlayerAvatar(user)));
		return rectangle;
	}
}
