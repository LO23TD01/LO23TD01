package ihmTable.controller;

import java.io.IOException;

import data.GameState;
import data.User;
import ihmTable.util.Utility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class PlayerController extends PlayerDiceController {

	@FXML
    private HBox playerView;

	@FXML
    private VBox leftContainer;

    @FXML
    private Label tokens;

    @FXML
    private VBox centerContainer;

    @FXML
    private Circle avatarContainer;

    @FXML
    private Label playerName;

	private User user;
	private GameState gameState;

	public void initialize() throws IOException {
		super.initialize();
		this.tokens.setText("0");
		setPrefProperties();
	}

	public void setData(GameState gameState, User user) {
		this.user = user;
		this.gameState = gameState;
		this.gameState.actualPlayerProperty().addListener((observable, oldValue, newValue) -> onActualPlayerChange(newValue));
		setPlayerData();
		setPlayer();
	}

	protected void setPlayerData() {
		super.setPlayerData(this.gameState.getData(this.user, false));
		updateTokens();
	}

	private void onActualPlayerChange(User actualPlayer) {
		if(this.user.isSame(actualPlayer)) {
			setPlayerData();
		}
	}

	private void setPlayer() {
		this.playerName.setText(this.user.getPublicData().getNickName());
		this.avatarContainer.setFill(new ImagePattern(Utility.getPlayerAvatar(this.user)));
		int[] dice = this.playerData.getDices();
		this.diceController1.setValue(dice[0]);
		this.diceController2.setValue(dice[1]);
		this.diceController3.setValue(dice[2]);
	}

	private void updateTokens() {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	tokens.setText(String.valueOf(playerData.getChip()));
		    }
		});
	}

	private void setPrefProperties() {
		Utility.bindPrefProperties(leftContainer, playerView.widthProperty().multiply(0.12), playerView.heightProperty());
		Utility.bindPrefProperties(centerContainer, playerView.widthProperty().multiply(0.2), playerView.heightProperty());
		Utility.bindPrefProperties(diceContainer, playerView.widthProperty().multiply(0.2), playerView.heightProperty());
		Utility.bindPrefProperties(tokens, leftContainer.widthProperty(), leftContainer.widthProperty());
	}
}
