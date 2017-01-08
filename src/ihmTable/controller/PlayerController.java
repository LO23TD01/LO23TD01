package ihmTable.controller;

import java.io.IOException;

import data.GameState;
import data.User;
import data.client.InterImplDataTable;
import ihmTable.util.Utility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Controller which manages the player's view and extends PlayerDiceController
 *
 * @see PlayerDiceController
 */
public class PlayerController extends PlayerDiceController {

	/**
	 * Style class for the player who should play
	 */
	private static final String TURN_OF = "turn-of";
	/**
	 * Style class for the player with the best score
	 */
	private static final String BEST_SCORE = "best-score";
	/**
	 * Style class for the player with the score to beat
	 */
	private static final String SCORE_TO_BEAT = "score-to-beat";

	/**
	 * The main container of the player view
	 */
	@FXML
    private HBox playerView;

    /**
     * The label containing the player's tokens' count
     */
    @FXML
	protected Label tokens;

    /**
     * The center container of the view
     */
    @FXML
    private VBox centerContainer;

    /**
     * The circle containing the player's avatar
     *
     * @see Circle
     */
    @FXML
    protected Circle avatarContainer;

    /**
     * The label containing the player's name
     */
    @FXML
    protected Label playerName;

    /**
     * The interface with Data
     *
     * @see InterImplDataTable
     */
    protected InterImplDataTable interImplDataTable;
	/**
	 * The user for who the data are displayed
	 *
	 * @see User
	 */
	protected User user;
	/**
	 * The game state
	 *
	 * @see GameState
	 */
	protected GameState gameState;

	/**
	 * Initialize the controller
	 *
	 * @see ihmTable.controller.PlayerDiceController#initialize()
	 */
	public void initialize() throws IOException {
		super.initialize();
		this.tokens.setText("0");
		setPrefProperties();
	}

	/**
	 * Set the data of the controller
	 * @param interImplDataTable the interface with data
	 * @param user the user for who the data are displayed
	 *
	 * @see PlayerController#interImplDataTable
	 * @see PlayerController#user
	 */
	public void setData(InterImplDataTable interImplDataTable, User user) {
		this.interImplDataTable = interImplDataTable;
		this.gameState = interImplDataTable.getActualTable().getGameState();
		setUser(user);
	}

	/**
	 * Set the data of the controller
	 * @param interImplDataTable the interface with data
	 * @param user the user c
	 * @param playerStatsController the playerStatsController of the table
	 *
	 * @see PlayerStatsController
	 */
	public void setData(InterImplDataTable interImplDataTable, User user, PlayerStatsController playerStatsController) {
		setData(interImplDataTable, user);
		playerView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> playerStatsController.setUser(user));
	}

	/**
	 * Set the user for the view
	 * @param user the user for who the data are displayed
	 */
	protected void setUser(User user) {
		this.user = user;
		updatePlayerData();
		updateView();
	}

	/**
	 * Update the different elements of the view
	 */
	protected void updateView() {
		this.gameState.actualPlayerProperty().addListener((observable, oldValue, newValue) -> onActualPlayerChange(newValue));
		updatePlayerView();
		updateDiceViews();
		updateTokens();
	}

	/**
	 * Update the player data for the user
	 *
	 * @see PlayerDiceController#playerData
	 */
	private void updatePlayerData() {
		super.setPlayerData(this.gameState.getData(this.user, false));
	}

	/**
	 * Action performed when the actual player changes
	 * @param actualPlayer the actual player
	 */
	private void onActualPlayerChange(User actualPlayer) {
		updatePlayerData();
		updateAvatarStyle();
		if(this.playerData != null) {
			updateTokens();
		}
	}

	/**
	 * Update the view specific to the player view
	 */
	protected void updatePlayerView() {
		this.playerName.setText(this.user.getPublicData().getNickName());
		this.avatarContainer.setFill(new ImagePattern(Utility.getPlayerAvatar(this.user)));
		updateAvatarStyle();
	}

	/**
	 * Update avatar's container style
	 *
	 * @see PlayerController#avatarContainer
	 */
	private void updateAvatarStyle() {
		this.avatarContainer.getStyleClass().removeAll(TURN_OF, BEST_SCORE, SCORE_TO_BEAT);
		if(this.user.isSame(this.gameState.getActualPlayer())) {
			this.avatarContainer.getStyleClass().add(TURN_OF);
		} else if(this.user.isSame(this.interImplDataTable.getBest().getPlayer())) {
			this.avatarContainer.getStyleClass().add(SCORE_TO_BEAT);
		} else if(this.user.isSame(this.interImplDataTable.getWorst().getPlayer())) {
			this.avatarContainer.getStyleClass().add(BEST_SCORE);
		}
	}

	/**
	 * Update the tokens count
	 */
	private void updateTokens() {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	tokens.setText(String.valueOf(playerData.getChip()));
		    }
		});
	}

	/**
	 * Set pref properties (with and height) of the different elements of the view
	 */
	protected void setPrefProperties() {
		Utility.bindPrefProperties(centerContainer, playerView.widthProperty().multiply(0.1), playerView.heightProperty());
		Utility.bindPrefProperties(diceContainer, playerView.widthProperty().multiply(0.1), playerView.heightProperty());
//		Utility.bindPrefProperties(tokens, playerView.widthProperty().multiply(0.12), playerView.widthProperty().multiply(0.12));
	}
}
