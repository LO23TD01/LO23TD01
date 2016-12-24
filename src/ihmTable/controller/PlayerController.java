package ihmTable.controller;

import java.io.IOException;

import data.GameTable;
import data.PlayerData;
import data.User;
import ihmTable.util.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class PlayerController {

	private static final String DICE_FXML = "/ihmTable/resources/view/Dice.fxml";
	private static final int DICE_SIZE = 40;

	@FXML
	private Circle imgView;

	@FXML
	private Text playerName;

	@FXML
	private VBox diceContainer;

	@FXML
	private Label PlayerStats_Jeton_Score_Label;

	@FXML
	private Label PlayerStats_Jeton_Loss_Score_Label;

	private DiceController dice1, dice2, dice3;

	private User user;

	private PlayerData playerData;


	public void initialize() throws IOException {
		initPlayerDice();
		PlayerStats_Jeton_Loss_Score_Label.setText("0");
	    PlayerStats_Jeton_Loss_Score_Label.setTextFill(Color.RED);
	}

	public void setData(GameTable gameTable, User user) {
		this.user = user;
		this.playerData = gameTable.getGameState().getData(user, false);
		setPlayerName();
		setPlayerAvatar();
		setPlayerDice();
		setPlayerChips();
	}

	private String getPlayerName() {
		return user.getPublicData().getNickName();
	}

	private void setPlayerName() {
		String name = getPlayerName();
		playerName.setText(name);
		playerName.setWrappingWidth(0.0D);
		playerName.setLineSpacing(0.0D);
	}

	private void setPlayerAvatar() {
		Image img = Utility.getPlayerAvatar(user);
		imgView.setFill(new ImagePattern(img));
	}

	private void setPlayerDice() {
		int[] dice = playerData.getDices();
		dice1.setValue(dice[0]);
		dice2.setValue(dice[1]);
		dice3.setValue(dice[2]);
	}

	private void setPlayerChips() {
		PlayerStats_Jeton_Loss_Score_Label.setText(String.valueOf(playerData.getChip()));
	}

	private void initPlayerDice() throws IOException {
		FXMLLoader diceLoader1 = new FXMLLoader(getClass().getResource(DICE_FXML));
		diceContainer.getChildren().add(diceLoader1.load());
		dice1 = (DiceController) diceLoader1.getController();
		dice1.setDice(false, DICE_SIZE);

		FXMLLoader diceLoader2 = new FXMLLoader(getClass().getResource(DICE_FXML));
		diceContainer.getChildren().add(diceLoader2.load());
		dice2 = (DiceController) diceLoader2.getController();
		dice2.setDice(false, DICE_SIZE);

		FXMLLoader diceLoader3 = new FXMLLoader(getClass().getResource(DICE_FXML));
		diceContainer.getChildren().add(diceLoader3.load());
		dice3 = (DiceController) diceLoader3.getController();
		dice3.setDice(false, DICE_SIZE);
	}
}
