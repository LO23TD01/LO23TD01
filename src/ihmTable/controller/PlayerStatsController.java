package ihmTable.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PlayerStatsController {

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
	    private ImageView PlayerStats_Dice1;

	    @FXML
	    private ImageView PlayerStats_Dice2;

	    @FXML
	    private ImageView PlayerStats_Dice3;

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

    //Création des images
    Image dice1 = new Image("/ihmTable/resources/png/1.png");
    Image dice2 = new Image("/ihmTable/resources/png/2.png");
    Image dice3 = new Image("/ihmTable/resources/png/3.png");
    Image dice4 = new Image("/ihmTable/resources/png/4.png");
    Image dice5 = new Image("/ihmTable/resources/png/5.png");
    Image dice6 = new Image("/ihmTable/resources/png/6.png");
    Image Jeton = new Image("/ihmTable/resources/png/JetonOK.png");
    Image JetonLoss = new Image("/ihmTable/resources/png/JetonLoss.png");


    public void initialize() throws IOException {

    	handleAsserts();


    	handleImage(PlayerStats_Dice1, dice1);
    	handleImage(PlayerStats_Dice2, dice2);
    	handleImage(PlayerStats_Dice3, dice3);
    	handleImage(PlayerStats_Jetons, Jeton);
    	handleImage(PlayerStats_PartiesPerdues, JetonLoss);

    	PlayerStats_Jeton_Loss_Score_Label.setText("0");
    	PlayerStats_Jeton_Loss_Score_Label.setTextFill(Color.RED);




    }

    private void handleAsserts()
    {
    	assert PLayerStats_VBoxLeft1 != null : "fx:id=\"PLayerStats_VBoxLeft1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_TitlePane1 != null : "fx:id=\"PlayerStats_TitlePane1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_TitleLabelPlayer != null : "fx:id=\"PlayerStats_TitleLabelPlayer\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_InfosHBoxParent1 != null : "fx:id=\"PlayerStats_InfosHBoxParent1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_PlayerInfoVBox1 != null : "fx:id=\"PlayerStats_PlayerInfoVBox1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_Jetons != null : "fx:id=\"PlayerStats_Jetons\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_PartiesPerdues != null : "fx:id=\"PlayerStats_PartiesPerdues\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_DicesHBox1 != null : "fx:id=\"PlayerStats_DicesHBox1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_Dice1 != null : "fx:id=\"PlayerStats_Dice1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_Dice2 != null : "fx:id=\"PlayerStats_Dice2\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_Dice3 != null : "fx:id=\"PlayerStats_Dice3\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PLayersStats_ScoreNumberAnchorPane1 != null : "fx:id=\"PLayersStats_ScoreNumberAnchorPane1\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_ScoreLabel != null : "fx:id=\"PlayerStats_ScoreLabel\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PLayerStats_StatsVBox != null : "fx:id=\"PLayerStats_StatsVBox\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert playerStats_LabelStats != null : "fx:id=\"playerStats_LabelStats\" was not injected: check your FXML file 'PlayerStats.fxml'.";
        assert PlayerStats_StatsPane != null : "fx:id=\"PlayerStats_StatsPane\" was not injected: check your FXML file 'PlayerStats.fxml'.";
    }

    private void handleImage (ImageView image, Image toAssign) //Permet d'assigne rune image à un imageview en conservant ses proriété.
    {
    	image.setImage(toAssign);
    }

}


