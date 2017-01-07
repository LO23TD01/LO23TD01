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

public class PlayerController extends PlayerDiceController {

	private static final String TURN_OF = "turn-of";
	private static final String BEST_SCORE = "best-score";
	private static final String SCORE_TO_BEAT = "score-to-beat";

	@FXML
    private HBox playerView;

    @FXML
	protected Label tokens;

    @FXML
    private VBox centerContainer;

    @FXML
    protected Circle avatarContainer;

    @FXML
    protected Label playerName;

    protected InterImplDataTable interImplDataTable;
	protected User user;
	protected GameState gameState;

	public void initialize() throws IOException {
		super.initialize();
		this.tokens.setText("0");
		setPrefProperties();
	}

	public void setData(InterImplDataTable interImplDataTable, User user) {
		this.interImplDataTable = interImplDataTable;
		this.gameState = interImplDataTable.getActualTable().getGameState();
		setUser(user);
	}

	public void setData(InterImplDataTable interImplDataTable, User user, PlayerStatsController playerStatsController) {
		setData(interImplDataTable, user);
		playerView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> playerStatsController.setUser(user));
	}

	protected void setUser(User user) {
		this.user = user;
		updateView();
	}

	protected void updateView() {
		this.gameState.actualPlayerProperty().addListener((observable, oldValue, newValue) -> onActualPlayerChange(newValue));
		updatePlayerData();
		updatePlayerView();
		updateDiceViews();
		updateTokens();
	}

	private void updatePlayerData() {
		super.setPlayerData(this.gameState.getData(this.user, false));
	}

	private void onActualPlayerChange(User actualPlayer) {
		updatePlayerData();
		updateAvatarStyle();
	}

	protected void updatePlayerView() {
		this.playerName.setText(this.user.getPublicData().getNickName());
		this.avatarContainer.setFill(new ImagePattern(Utility.getPlayerAvatar(this.user)));
		updateAvatarStyle();
	}

	private void updateAvatarStyle() {
		this.avatarContainer.getStyleClass().removeAll(TURN_OF, BEST_SCORE, SCORE_TO_BEAT);
		if(this.user.isSame(this.gameState.getActualPlayer())) {
			this.avatarContainer.getStyleClass().add(TURN_OF);
		} else if(this.user.isSame(this.interImplDataTable.getBest().getPlayer())) {
			this.avatarContainer.getStyleClass().add(BEST_SCORE);
		} else if(this.user.isSame(this.interImplDataTable.getWorst().getPlayer())) {
			this.avatarContainer.getStyleClass().add(SCORE_TO_BEAT);
		}
	}

	private void updateTokens() {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	tokens.setText(String.valueOf(playerData.getChip()));
		    }
		});
	}

	protected void setPrefProperties() {
		Utility.bindPrefProperties(centerContainer, playerView.widthProperty().multiply(0.2), playerView.heightProperty());
		Utility.bindPrefProperties(diceContainer, playerView.widthProperty().multiply(0.2), playerView.heightProperty());
		Utility.bindPrefProperties(tokens, playerView.widthProperty().multiply(0.12), playerView.widthProperty().multiply(0.12));
	}
}
