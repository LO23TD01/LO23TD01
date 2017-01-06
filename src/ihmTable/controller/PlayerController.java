package ihmTable.controller;

import java.io.IOException;

import data.GameState;
import data.User;
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

	protected User user;
	private GameState gameState;

	public void initialize() throws IOException {
		super.initialize();
		this.tokens.setText("0");
		setPrefProperties();
	}

	public void setData(GameState gameState, User user) {
		this.gameState = gameState;
		this.gameState.actualPlayerProperty().addListener((observable, oldValue, newValue) -> onActualPlayerChange(newValue));
		setUser(user);
	}

	public void setData(GameState gameState, User user, PlayerStatsController playerStatsController) {
		setData(gameState, user);
		playerView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> playerStatsController.setUser(user));
	}

	protected void setUser(User user) {
		this.user = user;
		updatePlayerData();
		updatePlayerView();
	}

	private void updatePlayerData() {
		super.setPlayerData(this.gameState.getData(this.user, false));
		updateTokens();
	}

	private void onActualPlayerChange(User actualPlayer) {
		updatePlayerData();
	}

	private void updatePlayerView() {
		this.playerName.setText(this.user.getPublicData().getNickName());
		this.avatarContainer.setFill(new ImagePattern(Utility.getPlayerAvatar(this.user)));
		updateDiceViews();
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
