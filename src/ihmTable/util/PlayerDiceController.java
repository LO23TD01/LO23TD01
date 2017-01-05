package ihmTable.util;

import java.io.IOException;

import data.PlayerData;
import ihmTable.controller.DiceController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.TilePane;

public class PlayerDiceController {

	private static final String DICE_FXML = "/ihmTable/resources/view/Dice.fxml";

	@FXML
	protected TilePane diceContainer;

	protected DiceController diceController1, diceController2, diceController3;
	protected PlayerData playerData;

	public void initialize() throws IOException {
		this.diceController1 = addDice();
		this.diceController2 = addDice();
		this.diceController3 = addDice();
	}

	protected void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
		addDiceListeners();
	}

	private void addDiceListeners() {
		this.playerData.d1Property().addListener((observable, oldValue, newValue) -> onDiceChange(this.diceController1, newValue));
		this.playerData.d2Property().addListener((observable, oldValue, newValue) -> onDiceChange(this.diceController2, newValue));
		this.playerData.d3Property().addListener((observable, oldValue, newValue) -> onDiceChange(this.diceController3, newValue));
	}

	/**
	 * Add a new dice's view into the diceContainer and return its controller
	 * @param diceContainer the pane in which the dice should be added
	 * @return the controller of the dice's view
	 * @throws IOException
	 */
	private DiceController addDice() throws IOException {
		FXMLLoader diceLoader = new FXMLLoader(getClass().getResource(DICE_FXML));
		this.diceContainer.getChildren().add(diceLoader.load());
		DiceController diceController = (DiceController) diceLoader.getController();
		return diceController;
	}

	/**
	 * Update the dice's view when the dice's value changes
	 * @param diceController the controller of the dice's view
	 * @param newValue the new dice's value
	 */
	private void onDiceChange(DiceController diceController, Number newValue) {
		diceController.setValue(newValue.intValue());
	}
}
