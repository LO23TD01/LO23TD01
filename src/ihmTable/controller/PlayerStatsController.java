package ihmTable.controller;

import java.io.IOException;

import data.GameTable;
import data.PlayerData;
import data.User;
import data.client.InterImplDataTable;
import ihmTable.util.Utility;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PlayerStatsController {

	@FXML
    private HBox playerStatsView;

    @FXML
    private BorderPane playerContainer;

    @FXML
    private Label titleLabelPlayer;

    @FXML
    private TilePane playerLeftContainer;

    @FXML
    private ImageView jetons;

    @FXML
    private Label jetonScoreLabel;

    @FXML
    private ImageView partiesPerdues;

    @FXML
    private Label jetonLossScoreLabel;

    @FXML
    private VBox playerRightContainer;

    @FXML
    private TilePane diceContainer;

    @FXML
    private Label scoreLabel;

    @FXML
    private VBox stats;

    @FXML
    private Label labelStats;

    @FXML
    private Label labelWins;

    @FXML
    private Label labelLose;

    @FXML
    private Label labelForfeit;

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
		handleImage(jetons, Jeton);
		handleImage(partiesPerdues, JetonLoss);

		jetonLossScoreLabel.setText("0");
		jetonLossScoreLabel.setTextFill(Color.RED);

		setPrefProperties();
		setPlayerDice();
	}

	/**
	 * Passage des données par le controller père
	 */
	public void setData(InterImplDataTable interImplDataTable, User user) {
		this.interImplDataTable = interImplDataTable;
		gameTableInstance = interImplDataTable.getActualTable();
		currentPlayerData = gameTableInstance.getGameState().getData(gameTableInstance.getGameState().getActualPlayer(), false);
		init();
		Bindings();
	}

	/**
	 * Initialisation des valeurs.
	 */
	private void init() {
		labelForfeit.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameAbandonned()));
		labelLose.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameLost()));
		labelWins.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameWon()));
		jetonScoreLabel.setText(String.valueOf(currentPlayerData.getChip()));

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

		scoreLabel.setText(d1 + " " + d2 + " " + d3);

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
				jetonLossScoreLabel.textProperty().set("1");
			}
			// listeners
			currentPlayerData.chipProperty().addListener((observable, oldValue, newValue) -> chipListener(observable, oldValue, newValue));
			currentPlayerData.getPlayer().getPublicData().nbGameWonProperty().addListener((observable, oldValue, newValue) -> totalWinListener(observable, oldValue, newValue));
			currentPlayerData.getPlayer().getPublicData().nbGameLostProperty().addListener((observable, oldValue, newValue) -> totalLossListener(observable, oldValue, newValue));
			currentPlayerData.getPlayer().getPublicData().nbGameAbandonnedProperty().addListener((observable, oldValue, newValue) -> totalForfeitListener(observable, oldValue, newValue));

			// Handle dices when data's done
			currentPlayerData.d1Property().addListener((observable, oldValue, newValue) -> valueDice1Change(observable, oldValue, newValue));
			currentPlayerData.d2Property().addListener((observable, oldValue, newValue) -> valueDice2Change(observable, oldValue, newValue));
			currentPlayerData.d3Property().addListener((observable, oldValue, newValue) -> valueDice3Change(observable, oldValue, newValue));

			// bindings
			titleLabelPlayer.textProperty().bind(currentPlayerData.getPlayer().getPublicData().nickNameProperty());
		} else {
			// TODO handle error
		}
	}

	/**
	 * Listener sur les abandons
	 */
	private Object totalForfeitListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		labelForfeit.setText(String.valueOf(newValue));
		return null;
	}

	/**
	 * Listener sur les défaites
	 */
	private Object totalLossListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		labelLose.setText(String.valueOf(newValue));
		return null;
	}

	/**
	 * Listener sur les victoires
	 */
	private Object totalWinListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		labelWins.setText(String.valueOf(newValue));
		return null;
	}

	/**
	 * Listener sur les jetons
	 */
	private Object chipListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		jetonScoreLabel.setText(String.valueOf(newValue));
		return null;
	}

	/**
	 * Listener sur les dés
	 */
	private Object valueDice1Change(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		dice1.setValue(newValue.intValue()); // Mise a jour de l'image
												// correspondant nouvelle valeur
												// du dé
		// System.out.println("value dice1 change");
		return null;
	}

	private Object valueDice2Change(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		dice2.setValue(newValue.intValue()); // Mise a jour de l'image
												// correspondant nouvelle valeur
												// du dé
		// System.out.println("value dice2 change");
		return null;
	}

	private Object valueDice3Change(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		dice3.setValue(newValue.intValue()); // Mise a jour de l'image
												// correspondant nouvelle valeur
												// du dé
		// System.out.println("value dice3 change");
		return null;
	}

	/**
	 * Listener sur les abandons
	 *
	 * @deprecated
	 */
	private int totalForfeitListener() {
		if (currentPlayerData.getPlayer().getPublicData().getNbGameAbandonned() != 0) {
			labelForfeit.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameAbandonned()));
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
			labelLose.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameLost()));
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
			labelWins.setText(String.valueOf(currentPlayerData.getPlayer().getPublicData().getNbGameWon()));
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
			jetonScoreLabel.setText(String.valueOf(currentPlayerData.getChip()));
			return currentPlayerData.getChip();
		} else
			return 0;
	}

	/**
	 * Vérification des composants
	 *
	 */
	private void handleAsserts() {
		assert titleLabelPlayer != null : "fx:id=\"PlayerStats_TitleLabelPlayer\" was not injected: check your FXML file 'PlayerStats.fxml'.";
		assert jetons != null : "fx:id=\"PlayerStats_Jetons\" was not injected: check your FXML file 'PlayerStats.fxml'.";
		assert partiesPerdues != null : "fx:id=\"PlayerStats_PartiesPerdues\" was not injected: check your FXML file 'PlayerStats.fxml'.";
		assert scoreLabel != null : "fx:id=\"PlayerStats_ScoreLabel\" was not injected: check your FXML file 'PlayerStats.fxml'.";
		assert labelStats != null : "fx:id=\"playerStats_LabelStats\" was not injected: check your FXML file 'PlayerStats.fxml'.";

	}

	/**
	 * Mise en place des dés
	 *
	 */
	private void setPlayerDice() throws IOException {
		FXMLLoader diceLoader1 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		Pane d1 = diceLoader1.load();
		diceContainer.getChildren().add(d1);
		dice1 = (DiceController) diceLoader1.getController();
		dice1.setDice(false, diceContainer.widthProperty().multiply(0.3), diceContainer.heightProperty());

		FXMLLoader diceLoader2 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		Pane d2 = diceLoader2.load();
		diceContainer.getChildren().add(d2);
		dice2 = (DiceController) diceLoader2.getController();
		dice2.setDice(false, diceContainer.widthProperty().multiply(0.3), diceContainer.heightProperty());

		FXMLLoader diceLoader3 = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Dice.fxml"));
		Pane d3 = diceLoader3.load();
		diceContainer.getChildren().add(d3);
		dice3 = (DiceController) diceLoader3.getController();
		dice3.setDice(false, diceContainer.widthProperty().multiply(0.3), diceContainer.heightProperty());
	}

	private void setPrefProperties() {
		Utility.bindPrefProperties(playerContainer, playerStatsView.widthProperty().multiply(0.7), playerStatsView.heightProperty());
		Utility.bindPrefProperties(stats, playerStatsView.widthProperty().multiply(0.3), playerStatsView.heightProperty());
		Utility.bindPrefProperties(playerLeftContainer, playerContainer.widthProperty().multiply(0.3), playerContainer.heightProperty().subtract(titleLabelPlayer.heightProperty()));
		Utility.bindPrefProperties(playerRightContainer, playerContainer.widthProperty().multiply(0.7), playerContainer.heightProperty().subtract(titleLabelPlayer.heightProperty()));
		Utility.bindPrefProperties(diceContainer, playerRightContainer.widthProperty(), playerRightContainer.heightProperty().subtract(scoreLabel.heightProperty()));
	}

	/**
	 * Permet d'assigne rune image à un imageview en conservant ses propriétés.
	 *
	 */
	private void handleImage(ImageView image, Image toAssign) {
		image.setImage(toAssign);
	}
}
