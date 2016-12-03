package ihmTable.controller;

import java.io.IOException;

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
    private VBox PLayerStats_VBoxLeft1;

    @FXML
    private AnchorPane PlayerStats_TitlePane1;

    @FXML
    private Label PlayerStats_TitleLabelPlayer;

    @FXML
    private HBox PlayerStats_InfosHBoxParent1;

    @FXML
    private VBox PlayerStats_PlayerInfoVBox1;

    @FXML
    private ImageView PlayerStats_Jetons;

    @FXML
    private ImageView PlayerStats_PartiesPerdues;

    @FXML
    private HBox PlayerStats_DicesHBox1;

    @FXML
    private AnchorPane PLayersStats_ScoreNumberAnchorPane1;

    @FXML
    private Label PlayerStats_ScoreLabel;

    @FXML
    private VBox PLayerStats_StatsVBox;

    @FXML
    private Label playerStats_LabelStats;

    @FXML
    private Label PlayerStats_Jeton_Score_Label;

    @FXML
    private Label PlayerStats_Jeton_Loss_Score_Label;

    @FXML
    private AnchorPane PlayerStats_StatsPane;

    private DiceController dice1, dice2, dice3;
    // Cr�ation des images
    Image Jeton = new Image("/ihmTable/resources/png/JetonOK.png");
    Image JetonLoss = new Image("/ihmTable/resources/png/JetonLoss.png");

    public void initialize() throws IOException {
    	//Gestion des erreurs
        handleAsserts();
        //Gestion des images
        handleImage(PlayerStats_Jetons, Jeton);
        handleImage(PlayerStats_PartiesPerdues, JetonLoss);

        PlayerStats_Jeton_Loss_Score_Label.setText("0");
        PlayerStats_Jeton_Loss_Score_Label.setTextFill(Color.RED);

        //Bindings
        //titlelableplayer
        //scorelabel
        //scorelabel
        //scoreloss





        setPlayerDice();
    }

    private void handleAsserts() {
        assert PLayerStats_VBoxLeft1 != null : "fx:id=\"PLayerStats_VBoxLeft1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_TitlePane1 != null : "fx:id=\"PlayerStats_TitlePane1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_TitleLabelPlayer != null : "fx:id=\"PlayerStats_TitleLabelPlayer\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_InfosHBoxParent1 != null : "fx:id=\"PlayerStats_InfosHBoxParent1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_PlayerInfoVBox1 != null : "fx:id=\"PlayerStats_PlayerInfoVBox1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_Jetons != null : "fx:id=\"PlayerStats_Jetons\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_PartiesPerdues != null : "fx:id=\"PlayerStats_PartiesPerdues\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_DicesHBox1 != null : "fx:id=\"PlayerStats_DicesHBox1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PLayersStats_ScoreNumberAnchorPane1 != null : "fx:id=\"PLayersStats_ScoreNumberAnchorPane1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_ScoreLabel != null : "fx:id=\"PlayerStats_ScoreLabel\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PLayerStats_StatsVBox != null : "fx:id=\"PLayerStats_StatsVBox\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert playerStats_LabelStats != null : "fx:id=\"playerStats_LabelStats\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_StatsPane != null : "fx:id=\"PlayerStats_StatsPane\" was not injected: check your FXML file 'PlayerStats.fxml'.";
    }

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

    // Permet d'assigne rune image � un imageview en conservant ses prori�t�.
    private void handleImage(ImageView image, Image toAssign) {
        image.setImage(toAssign);
    }
}
