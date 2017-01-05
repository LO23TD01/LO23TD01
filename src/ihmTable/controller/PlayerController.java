package ihmTable.controller;

import java.io.IOException;

import data.GameState;
import data.User;
import ihmTable.util.Utility;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
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
    private Label lostScore;

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
		this.lostScore.setText("0");
		InvalidationListener listener = new InvalidationListener() {

		    @Override
		    public void invalidated(Observable observable) {
		    	playerView.widthProperty().removeListener(this);
		        setPrefProperties();
		    }
		};
		playerView.widthProperty().addListener(listener);
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
		int[] dice = this.playerData.getDices();
		this.diceController1.setValue(dice[0]);
		this.diceController2.setValue(dice[1]);
		this.diceController3.setValue(dice[2]);
	}

	private void setPrefProperties() {
		Utility.bindPrefProperties(leftContainer, playerView.widthProperty().multiply(tokens.getPrefWidth() / playerView.getWidth()), playerView.heightProperty());
		Utility.bindPrefProperties(centerContainer, playerView.widthProperty().multiply(avatarContainer.getRadius() * 2 / playerView.getWidth()), playerView.heightProperty());
		Utility.bindPrefProperties(diceContainer, playerView.widthProperty().multiply(diceController1.getPrefSize() / playerView.getWidth()), playerView.heightProperty());
		Utility.bindPrefProperties(tokens, leftContainer.widthProperty(), leftContainer.widthProperty());
		Utility.bindPrefProperties(lostScore, leftContainer.widthProperty(), leftContainer.widthProperty());
		centerContainer.widthProperty().addListener(event -> System.out.println("testw " + centerContainer.getWidth()));
		centerContainer.heightProperty().addListener(event -> System.out.println("testh " + centerContainer.getHeight()));
		this.avatarContainer.radiusProperty().bind(Bindings.min(centerContainer.widthProperty().divide(2), centerContainer.heightProperty().divide(2)));
	}
}
