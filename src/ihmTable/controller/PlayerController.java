package ihmTable.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class PlayerController {

    private static final int DICE_SIZE = 40;

    @FXML
    private Circle imgView;

    @FXML
    private Text playerName;

    @FXML
    private VBox diceContainer;

	private DiceController dice1, dice2, dice3;

	public void initialize() throws IOException {
		setPlayerName();
		setPlayerAvatar();
		setPlayerDice();
	}

	private String getPlayerName() {
		// get name from DB
		String name = "player1";
		return name;
	}

	private Image getPlayerAvatar() {
		// get picture from DB
		Image img = new Image("/ihmTable/resources/png/user.png");
		return img;
	}

	private void setPlayerName() {
		String name = getPlayerName();
		playerName.setText(name);
		playerName.setWrappingWidth(0.0D);
		playerName.setLineSpacing(0.0D);
	}

	private void setPlayerAvatar() {
		Image img = getPlayerAvatar();
		imgView.setFill(new ImagePattern(img));
	}

	private void setPlayerDice() throws IOException {
		FXMLLoader diceLoader1 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		diceContainer.getChildren().add(diceLoader1.load());
		dice1 = (DiceController) diceLoader1.getController();
		dice1.setDice(false, DICE_SIZE);

		FXMLLoader diceLoader2 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		diceContainer.getChildren().add(diceLoader2.load());
		dice2 = (DiceController) diceLoader2.getController();
		dice2.setDice(false, DICE_SIZE);

		FXMLLoader diceLoader3 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		diceContainer.getChildren().add(diceLoader3.load());
		dice3 = (DiceController) diceLoader3.getController();
		dice3.setDice(false, DICE_SIZE);
	}

}
