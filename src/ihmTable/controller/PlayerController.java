package ihmTable.controller;

import java.io.IOException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class PlayerController {

	  @FXML
	    private Circle player;

	    @FXML
	    private Button upload_button;

	    @FXML
	    private ImageView imgView;

	    @FXML
	    private ImageView first;

	    @FXML
	    private ImageView second;

	    @FXML
	    private ImageView third;
	    
	    @FXML
	    private Text playerName;
		private Pane playerPane;

	    public void initialize() throws IOException  {
	    	FXMLLoader playerLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Player.fxml"));
	    	playerPane = playerLoader.load();
	    //	AnchorPane.setLeftAnchor(playerLoader, 0.0);
		
	    	setPlayerName();
	    	setPlayerAvatar();
	    	setPlayerDices();
	    	
	    }
	    
	    private int getPlayerDices(){
	        int dice = randGen();
	        return dice;
	     }
	    
	    private String getPlayerName(){
	    	//get name from DB
	    	String name = "player1";
	    	return name;
	    }
	    
	    private Image getPlayerAvatar(){
	    	//get picture from DB
	    	Image img = new Image("/ihmTable/resources/png/Desert.png");
	    	return img;
	    }
	    
	    public static int randGen() {

		    Random rand = new Random();
		    int randomNum = rand.nextInt((6 - 1) + 1) + 1;

		    return randomNum;
		}
	    private void setPlayerName() {

	    	String name = getPlayerName();
	    	playerName.setText(name);
	    	playerName.setWrappingWidth(0.0D);
	    	playerName.setLineSpacing(0.0D);	   
		}
	    
	   
	    private void setPlayerAvatar() {
	    	Image img = getPlayerAvatar();
	    	imgView.setImage(img); 
	        imgView.setPreserveRatio(true);
	        imgView.setSmooth(true);
	        imgView.setCache(true);
	        imgView.setFitWidth(300);
	        imgView.setFitHeight(300);
	      
	        Circle circle = new Circle();
	        circle.setCenterX(100.0f);
	        circle.setCenterY(100.0f);
	        circle.setRadius(100.0f);
	        imgView.setClip(circle);	   
		}
	    
	   
	    
	    private void setPlayerDices(){
	    	int dice1 = getPlayerDices();
	     	int dice2 = getPlayerDices();
	     	int dice3 = getPlayerDices();
	    	
		   Image img1 = new Image("/ihmTable/resources/png/" + dice1 + ".png");
		   Image img2 = new Image("/ihmTable/resources/png/" + dice2 + ".png");
		   Image img3 = new Image("/ihmTable/resources/png/" + dice3 + ".png");
				
		    first.setImage(img1); 	    	
			first.setPreserveRatio(true);
			first.setSmooth(true);
			first.setCache(true);
		    
			second.setImage(img2); 
			second.setPreserveRatio(true);
			second.setSmooth(true);
			second.setCache(true);
			
		    third.setImage(img3); 	    	
		    third.setPreserveRatio(true);
		    third.setSmooth(true);
		    third.setCache(true);
	    }
	    
}
