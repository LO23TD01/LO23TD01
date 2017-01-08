package ihmTable.controller;

import java.io.IOException;

import data.PlayerData;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.layout.TilePane;

/**
 * Controller which manages dice view
 */
public abstract class PlayerDiceController {

	/**
	 * The dice view's fxml file
	 */
	private static final String DICE_FXML = "/ihmTable/resources/view/Dice.fxml";

	/**
	 * Container of the all dice
	 */
	@FXML
	protected TilePane diceContainer;

	/**
	 * Controller of the first dice
	 *
	 * @see DiceController
	 */
	protected DiceController diceController1;
	/**
	 * Controller of the second dice
	 *
	 * @see DiceController
	 */
	protected DiceController diceController2;
	/**
	 * Controller of the third dice
	 *
	 * @see DiceController
	 */
	protected DiceController diceController3;
	/**
	 * The player's data
	 *
	 * @see PlayerData
	 */
	protected PlayerData playerData;

	/**
	 * Initialize the controller
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		this.diceController1 = addDice();
		this.diceController2 = addDice();
		this.diceController3 = addDice();
		if(this.diceContainer.getOrientation() == Orientation.VERTICAL) {
			setDiceProperties(this.diceContainer.widthProperty(), this.diceContainer.heightProperty().multiply(0.3));
		} else {
			setDiceProperties(this.diceContainer.widthProperty().multiply(0.3), this.diceContainer.heightProperty());
		}
	}

	/**
	 * Set the player's data
	 * @param playerData the new player data
	 *
	 * @see PlayerData
	 */
	protected void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
		if(this.playerData != null) {
			addDiceListeners();
		}
//		updateDiceViews();
	}

	/**
	 * Add listeners to all dice property from player data in order to update all dice view
	 */
	private void addDiceListeners() {
		this.playerData.d1Property().addListener((observable, oldValue, newValue) -> this.diceController1.setValue(newValue.intValue()));
		this.playerData.d2Property().addListener((observable, oldValue, newValue) -> this.diceController2.setValue(newValue.intValue()));
		this.playerData.d3Property().addListener((observable, oldValue, newValue) -> this.diceController3.setValue(newValue.intValue()));
	}

	/**
	 * Update the dice views
	 */
	protected void updateDiceViews() {
		this.diceController1.setValue(this.playerData.d1Property().getValue());
		this.diceController2.setValue(this.playerData.d2Property().getValue());
		this.diceController3.setValue(this.playerData.d3Property().getValue());
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
	 * Set the dice's view properties (width and height)
	 * @param widthProperty the width of the dice
	 * @param heightProperty the height of the dice
	 */
	private void setDiceProperties(ObservableValue<? extends Number> widthProperty, ObservableValue<? extends Number> heightProperty) {
		this.diceController1.setProperties(false, widthProperty, heightProperty);
		this.diceController2.setProperties(false, widthProperty, heightProperty);
		this.diceController3.setProperties(false, widthProperty, heightProperty);
	}
}
