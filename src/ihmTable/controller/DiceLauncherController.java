package ihmTable.controller;

import java.io.IOException;

import data.GameTable;
import data.User;
import data.UserRole;
import data.client.InterImplDataTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

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

		// Listener pour quand la valeur d'un dés change
		dice1.value.addListener(event -> valueDice1Change());
		dice2.value.addListener(event -> valueDice2Change());
		dice3.value.addListener(event -> valueDice3Change());

		// Listener pour quand un dés est selectionné ou non
		dice1.selected.addListener(event -> selectedDiceChange());
		dice2.selected.addListener(event -> selectedDiceChange());
		dice3.selected.addListener(event -> selectedDiceChange());

		// TODO: *** Problème getDice n'est pas une property qui est renvoyé
		//this.interImplDataTable.getActualTable().getGameState().getData(this.interImplDataTable.getActualTable().getGameState().getActualPlayer(), false).getDices();
	}

	public void setData(InterImplDataTable interImplDataTable, User user) throws IOException{
		this.interImplDataTable = interImplDataTable;
		this.localUser = user;
		this.interImplDataTable.getActualTable().getGameState().getActualPlayer().getPublicData().uuidProperty().addListener(event -> actualPlayerChange()); // quand le joueur qui joue change
	}

	private void launch() {
		// TODO: *** problème imcompréhensible dataEngine is null ? Pb initialisation.

		//this.interImplDataTable.throwDice(dice1.isSelected(), dice2.isSelected(), dice3.isSelected());
		System.out.println("dice1: "+ dice1.isSelected() +", dice2:"+ dice2.isSelected()+", dice3:"+ dice3.isSelected());
		dice1.setSelected(false);
		dice2.setSelected(false);
		dice3.setSelected(false);

		dice1.setValue();
		dice2.setValue();
		dice3.setValue();
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

	private void valueDice1Change(){
		dice1.setValue(dice1.getValue()); // Mise a jour de l'image correspondant nouvelle valeur du dé
		System.out.println("dice1 change");
	}

	private void valueDice2Change(){
		dice2.setValue(dice2.getValue()); // Mise a jour de l'image correspondant nouvelle valeur du dé
		System.out.println("dice2 change");
	}

	private void valueDice3Change(){
		dice3.setValue(dice3.getValue()); // Mise a jour de l'image correspondant nouvelle valeur du dé
		System.out.println("dice3 change");
	}

	private void selectedDiceChange(){
		// TODO: *** problème imcompréhensible dataEngine is null ? Pb initialisation.
		//this.interImplDataTable.selectDice(dice3.isSelected(), dice2.isSelected(), dice3.isSelected());
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

		// TODO: *** Problème getDice n'est pas une property qui est renvoyé
		// Mise à jour des donnée
		//this.interImplDataTable.getActualTable().getGameState().getData(this.interImplDataTable.getActualTable().getGameState().getActualPlayer(), false).getDices();
	}
}
