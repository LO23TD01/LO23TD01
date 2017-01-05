package ihmTable.controller;

import java.io.IOException;

import data.GameState;
import data.User;
import ihmTable.util.PlayerDiceController;
import ihmTable.util.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class PlayerController extends PlayerDiceController {

    @FXML
    private Label tokens;

    @FXML
    private Label lostScore;

    @FXML
    private Circle avatarContainer;

    @FXML
    private Text playerName;

	private User user;
	private GameState gameState;

	public void initialize() throws IOException {
		super.initialize();
		this.diceController1.setDice(false, diceContainer.widthProperty(), diceContainer.heightProperty().multiply(0.3));
		this.diceController2.setDice(false, diceContainer.widthProperty(), diceContainer.heightProperty().multiply(0.3));
		this.diceController3.setDice(false, diceContainer.widthProperty(), diceContainer.heightProperty().multiply(0.3));
		this.tokens.setText("0");
		this.lostScore.setText("0");
	}

	public void setData(GameState gameState, User user) {
		this.user = user;
		this.gameState = gameState;
		setPlayerData(this.gameState.getData(this.user, false));
		setPlayer();
		this.gameState.actualPlayerProperty().addListener(event -> onActualPlayerChange());
	}

	private void onActualPlayerChange() {
		if(this.gameState.getActualPlayer().getPublicData().getUUID().equals(this.user.getPublicData().getUUID())) {
			setPlayerData(this.gameState.getData(this.user, false));
		}
	}

	private void setPlayer() {
		this.playerName.setText(this.user.getPublicData().getNickName());
		this.avatarContainer.setFill(new ImagePattern(Utility.getPlayerAvatar(this.user)));
		int[] dice = playerData.getDices();
		diceController1.setValue(dice[0]);
		diceController2.setValue(dice[1]);
		diceController3.setValue(dice[2]);
	}
}
