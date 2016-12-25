package ihmTable.controller;

import java.io.IOException;

import data.GameTable;
import data.PlayerData;
import data.User;
import data.client.InterImplDataTable;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.collections.ArrayChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableIntegerArray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PlayerStatsController {

	private static final int DICE_SIZE = 80;

	@FXML
	private HBox PlayerStats_DicesHBox1;

	@FXML
	private Label PlayerStats_TitleLabelPlayer;

	@FXML
	private ImageView PlayerStats_Jetons;

	@FXML
	private ImageView PlayerStats_PartiesPerdues;

	@FXML
	private Label PlayerStats_ScoreLabel;

	@FXML
	private Label playerStats_LabelStats;

	@FXML
	private Label PlayerStats_Jeton_Score_Label;

	@FXML
	private Label PlayerStats_Jeton_Loss_Score_Label;

	@FXML
	private Label PlayerStats_Label_Wins;

	@FXML
	private Label PlayerStats_Label_Lose;

	@FXML
	private Label PlayerStats_Label_Forfeit;

	private DiceController dice1, dice2, dice3;

	public GameTable gameTableInstance;

	private InterImplDataTable interImplDataTable;

	private PlayerData currentPlayerData;

	// Création des images
	Image Jeton = new Image("/ihmTable/resources/png/JetonOK.png");
	Image JetonLoss = new Image("/ihmTable/resources/png/JetonLoss.png");

	/**
	 * Initialisation du controller
	 */
	public void initialize() throws IOException {
		// Gestion des erreurs
		handleAsserts();
		// Gestion des images
		handleImage(PlayerStats_Jetons, Jeton);
		handleImage(PlayerStats_PartiesPerdues, JetonLoss);

		PlayerStats_Jeton_Loss_Score_Label.setText("0");
		PlayerStats_Jeton_Loss_Score_Label.setTextFill(Color.RED);

		setPlayerDice();
	}

	/**
	 * Passage des données par le controller père
	 */
	public void setData(InterImplDataTable interImplDataTable, User user) {
		this.interImplDataTable = interImplDataTable;
		gameTableInstance = interImplDataTable.getActualTable();
		currentPlayerData = gameTableInstance.getGameState().getData(gameTableInstance.getGameState().getActualPlayer(),
				false);
		init();
		Bindings();
	}

	/**
	 * Initialisation des valeurs.
	 */
	private void init() {
		PlayerStats_Label_Forfeit
		.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameAbandonned()));
		PlayerStats_Label_Lose.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameLost()));
		PlayerStats_Label_Wins.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameWon()));
		PlayerStats_Jeton_Score_Label.setText(String.valueOf(currentPlayerData.getChip()));

		handleDices();
	}

	/**
	 * Gestion des dés pour l'initilisation des valeurs
	 */
	private void handleDices() {
		// TODO gérer avec observable array
		int d1;
		int d2;
		int d3;

		int[] dices = currentPlayerData.getDices();

		d1 = dices[0];
		d2 = dices[1];
		d3 = dices[2];

		PlayerStats_ScoreLabel.setText(d1 + " " + d2 + " " + d3);

		dice1.setValue(d1);
		dice2.setValue(d2);
		dice3.setValue(d3);
	}

	/**
	 * Création des bindings
	 */
	private void Bindings() {
		if (currentPlayerData != null) {

			boolean isCurrentPlayerLoser = false;
			for (int i = 0; i < gameTableInstance.getGameState().getLosers().size(); i++) {
				if (gameTableInstance.getGameState().getLosers().get(i).equals(currentPlayerData.getPlayer())) {
					isCurrentPlayerLoser = true;
				}
			}
			if (isCurrentPlayerLoser) {
				PlayerStats_Jeton_Loss_Score_Label.textProperty().set("1");
			}
			// listeners
			currentPlayerData.chipProperty()
			.addListener((observable, oldValue, newValue) -> chipListener(observable, oldValue, newValue));
			currentPlayerData.getPlayer().getPublicData().nbGameWonProperty()
			.addListener((observable, oldValue, newValue) -> totalWinListener(observable, oldValue, newValue));
			currentPlayerData.getPlayer().getPublicData().nbGameLostProperty()
			.addListener((observable, oldValue, newValue) -> totalLossListener(observable, oldValue, newValue));
			currentPlayerData.getPlayer().getPublicData().nbGameAbandonnedProperty().addListener(
					(observable, oldValue, newValue) -> totalForfeitListener(observable, oldValue, newValue));

			// Handle dices when data's done
			currentPlayerData.d1Property().addListener((observable, oldValue, newValue) -> valueDice1Change(observable, oldValue, newValue));
			currentPlayerData.d2Property().addListener((observable, oldValue, newValue) -> valueDice2Change(observable, oldValue, newValue));
			currentPlayerData.d3Property().addListener((observable, oldValue, newValue) -> valueDice3Change(observable, oldValue, newValue));

			// bindings
			PlayerStats_TitleLabelPlayer.textProperty()
			.bind(currentPlayerData.getPlayer().getPublicData().nickNameProperty());
		} else {
			// TODO handle error
		}
	}

	/**
	 * Listener sur les abandons
	 */
	private Object totalForfeitListener(ObservableValue<? extends Number> observable, Number oldValue,
			Number newValue) {

		PlayerStats_Label_Forfeit.setText(String.valueOf(newValue));

		return null;
	}

	/**
	 * Listener sur les défaites
	 */
	private Object totalLossListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		PlayerStats_Label_Lose.setText(String.valueOf(newValue));
		return null;
	}

	/**
	 * Listener sur les victoires
	 */
	private Object totalWinListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		PlayerStats_Label_Wins.setText(String.valueOf(newValue));
		return null;
	}

	/**
	 * Listener sur les jetons
	 */
	private Object chipListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		PlayerStats_Jeton_Score_Label.setText(String.valueOf(newValue));
		return null;
	}

	/**
	 * Listener sur les dés
	 */
	private Object valueDice1Change(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		dice1.setValue(newValue.intValue()); // Mise a jour de l'image correspondant nouvelle valeur du dé
		//System.out.println("value dice1 change");
		return null;
	}

	private Object valueDice2Change(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		dice2.setValue(newValue.intValue()); // Mise a jour de l'image correspondant nouvelle valeur du dé
		//System.out.println("value dice2 change");
		return null;
	}

	private Object valueDice3Change(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		dice3.setValue(newValue.intValue()); // Mise a jour de l'image correspondant nouvelle valeur du dé
		//System.out.println("value dice3 change");
		return null;
	}

	/**
	 * Listener sur les abandons
	 *
	 * @deprecated
	 */
	private int totalForfeitListener() {
		if (currentPlayerData.getPlayer().getPublicData().getNbGameAbandonned() != 0) {
			PlayerStats_Label_Forfeit
			.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameAbandonned()));
			return currentPlayerData.getPlayer().getPublicData().getNbGameAbandonned();
		} else
			return 0;
	}

	/**
	 * Listener sur les défaites
	 *
	 * @deprecated
	 */
	private int totalLossListener() {
		if (currentPlayerData.getPlayer().getPublicData().getNbGameWon() != 0) {
			PlayerStats_Label_Lose
			.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameLost()));
			return currentPlayerData.getPlayer().getPublicData().getNbGameLost();
		} else
			return 0;
	}

	/**
	 * Listener sur les victoires
	 *
	 * @deprecated
	 */
	private int totalWinListener() {
		if (currentPlayerData.getPlayer().getPublicData().getNbGameWon() != 0) {
			PlayerStats_Label_Wins
			.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameWon()));
			return currentPlayerData.getPlayer().getPublicData().getNbGameWon();
		} else
			return 0;
	}

	/**
	 * Listener sur les jetons
	 *
	 * @deprecated
	 */
	private int chipListener() {
		if (currentPlayerData.getChip() != 0) {
			PlayerStats_Jeton_Score_Label.setText(String.valueOf(currentPlayerData.getChip()));
			return currentPlayerData.getChip();
		} else
			return 0;
	}

	/**
	 * Vérification des composants
	 *
	 */
	private void handleAsserts() {

		assert PlayerStats_TitleLabelPlayer != null : "fx:id=\"PlayerStats_TitleLabelPlayer\" was not injected: check your FXML file 'PlayerStats.fxml'.";

		assert PlayerStats_Jetons != null : "fx:id=\"PlayerStats_Jetons\" was not injected: check your FXML file 'PlayerStats.fxml'.";
		assert PlayerStats_PartiesPerdues != null : "fx:id=\"PlayerStats_PartiesPerdues\" was not injected: check your FXML file 'PlayerStats.fxml'.";

		assert PlayerStats_ScoreLabel != null : "fx:id=\"PlayerStats_ScoreLabel\" was not injected: check your FXML file 'PlayerStats.fxml'.";

		assert playerStats_LabelStats != null : "fx:id=\"playerStats_LabelStats\" was not injected: check your FXML file 'PlayerStats.fxml'.";

	}

	/**
	 * Mise en place des dés
	 *
	 */
	private void setPlayerDice() throws IOException {
		FXMLLoader diceLoader1 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		PlayerStats_DicesHBox1.getChildren().add(diceLoader1.load());
		dice1 = (DiceController) diceLoader1.getController();
		dice1.setDice(false, DICE_SIZE);

		FXMLLoader diceLoader2 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		PlayerStats_DicesHBox1.getChildren().add(diceLoader2.load());
		dice2 = (DiceController) diceLoader2.getController();
		dice2.setDice(false, DICE_SIZE);

		FXMLLoader diceLoader3 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		PlayerStats_DicesHBox1.getChildren().add(diceLoader3.load());
		dice3 = (DiceController) diceLoader3.getController();
		dice3.setDice(false, DICE_SIZE);
	}

	/**
	 * Permet d'assigne rune image à un imageview en conservant ses propriétés.
	 *
	 */
	private void handleImage(ImageView image, Image toAssign) {
		image.setImage(toAssign);
	}
}
