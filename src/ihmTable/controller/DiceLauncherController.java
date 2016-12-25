package ihmTable.controller;

import java.io.IOException;

import data.User;
import data.client.InterImplDataTable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class DiceLauncherController {

	@FXML
	private HBox diceContainer;

	@FXML
	private Button launchButton;

	private DiceController dice1, dice2, dice3;

	public InterImplDataTable interImplDataTable;

	private User localUser;

	public void initialize() throws IOException {
		FXMLLoader diceLoader1 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		diceContainer.getChildren().add(diceLoader1.load());
		dice1 = (DiceController) diceLoader1.getController();
		dice1.setPosition(1);

		FXMLLoader diceLoader2 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		diceContainer.getChildren().add(diceLoader2.load());
		dice2 = (DiceController) diceLoader2.getController();
		dice2.setPosition(2);

		FXMLLoader diceLoader3 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		diceContainer.getChildren().add(diceLoader3.load());
		dice3 = (DiceController) diceLoader3.getController();
		dice3.setPosition(3);

		launchButton.setOnAction(event -> launch());

		// Listener pour quand un dés est selectionné ou non
		dice1.selected.addListener(event -> selectedDiceChange());
		dice2.selected.addListener(event -> selectedDiceChange());
		dice3.selected.addListener(event -> selectedDiceChange());
	}

	public void setData(InterImplDataTable interImplDataTable, User user) throws IOException{
		this.interImplDataTable = interImplDataTable;
		this.localUser = user;

		// TODO: *** A tester listerner dice
		// Mise à jour des donnée avec listener sur les valeurs des dés
		this.interImplDataTable.getActualTable().getGameState().getData(this.interImplDataTable.getActualTable().getGameState().getActualPlayer(), false).d1Property().addListener((observable, oldValue, newValue) -> valueDice1Change(observable, oldValue, newValue));
		this.interImplDataTable.getActualTable().getGameState().getData(this.interImplDataTable.getActualTable().getGameState().getActualPlayer(), false).d2Property().addListener((observable, oldValue, newValue) -> valueDice2Change(observable, oldValue, newValue));
		this.interImplDataTable.getActualTable().getGameState().getData(this.interImplDataTable.getActualTable().getGameState().getActualPlayer(), false).d3Property().addListener((observable, oldValue, newValue) -> valueDice3Change(observable, oldValue, newValue));
		this.interImplDataTable.getActualTable().getGameState().getActualPlayer().getPublicData().uuidProperty().addListener(event -> actualPlayerChange()); // quand le joueur qui joue change
	}

	private void launch() {
		// TODO: *** A tester

		//debug pour avoir des dés ...
		dice1.setSelected(true);
		dice2.setSelected(true);
		dice3.setSelected(true);
		this.interImplDataTable.throwDice(dice1.isSelected(), dice2.isSelected(), dice3.isSelected());
		System.out.println("dice1: "+ dice1.isSelected() +", dice2:"+ dice2.isSelected()+", dice3:"+ dice3.isSelected());
		dice1.setSelected(false);
		dice2.setSelected(false);
		dice3.setSelected(false);

	}

	// Rend le bouton lancer les dés cliquable
	public void setEnableLaunchButton(){
		launchButton.setDisable(false);
		dice1.setSelectable(true);
		dice2.setSelectable(true);
		dice3.setSelectable(true);
	}

	// Rend le bouton lancer les dés non cliquable
	public void setDisableLaunchButton(){
		launchButton.setDisable(true);
		dice1.setSelectable(false);
		dice2.setSelectable(false);
		dice3.setSelectable(false);
	}

	// Listener sur les valeurs des dés
	private Object valueDice1Change(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		dice1.setValue(newValue.intValue()); // Mise a jour de l'image correspondant nouvelle valeur du dé
		System.out.println("value dice1 change");
		return null;
	}

	private Object valueDice2Change(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		dice2.setValue(newValue.intValue()); // Mise a jour de l'image correspondant nouvelle valeur du dé
		System.out.println("value dice2 change");
		return null;
	}

	private Object valueDice3Change(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		dice3.setValue(newValue.intValue()); // Mise a jour de l'image correspondant nouvelle valeur du dé
		System.out.println("value dice3 change");
		return null;
	}

	private void selectedDiceChange(){
		// TODO: *** A tester
		this.interImplDataTable.selectDice(dice3.isSelected(), dice2.isSelected(), dice3.isSelected());
	}

	private void actualPlayerChange(){
		// Quand le joueur qui à la main change
		System.out.println("Changement de joueur");
		if(this.interImplDataTable.getActualTable().getGameState().getActualPlayer().getPublicData().uuidProperty() == this.localUser.getPublicData().uuidProperty()){
			setEnableLaunchButton();
		}
		else{
			setDisableLaunchButton();
		}

		// Mise à jour des donnée avec listener sur les valeurs des dés
		this.interImplDataTable.getActualTable().getGameState().getData(this.interImplDataTable.getActualTable().getGameState().getActualPlayer(), false).d1Property().addListener((observable, oldValue, newValue) -> valueDice1Change(observable, oldValue, newValue));
		this.interImplDataTable.getActualTable().getGameState().getData(this.interImplDataTable.getActualTable().getGameState().getActualPlayer(), false).d2Property().addListener((observable, oldValue, newValue) -> valueDice2Change(observable, oldValue, newValue));
		this.interImplDataTable.getActualTable().getGameState().getData(this.interImplDataTable.getActualTable().getGameState().getActualPlayer(), false).d3Property().addListener((observable, oldValue, newValue) -> valueDice3Change(observable, oldValue, newValue));
	}

}
