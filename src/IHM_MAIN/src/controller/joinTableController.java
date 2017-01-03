package IHM_MAIN.src.controller;

import data.GameTable;
import data.client.InterImplDataMain;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
	*	joinTableController is the controller for the Join table window.
	*	<br>It implements utility methods to handle joining tables, as a player or spectator.
*/
public class joinTableController {
	@FXML
	Button asPlayer;

	@FXML
	Button asSpec;

	@FXML
	Button cancel;
	InterImplDataMain interfaceData;
	GameTable joiningGame;

	/**
	*	setInterfaceData is a simple setter. It allows to set the DataInterface to the window controller.
	*	@param interf An InterImplDataMain object, containing the implementation of the Data API.
	*	@see InterImplDataMain
	*/
    public void setInterfaceData(InterImplDataMain interf){
        this.interfaceData = interf;
    }

    /**
	*	setJoiningGame is a simple setter. It sets the current status of the window and the Game the player is trying to join.
	*	<br>It has to be done this way since there is no way of knowing which type of connection the user is going to choose.
	*	@param game A GameTable object. It represents the game the player is joining.
	*	@see GameTable
	*/
    public void setJoiningGame(GameTable game){
        this.joiningGame = game;
    }

    /**
	*	handleJoinAP is the method used to join a tbale as a Player.
	*	<br>MUST be used AFTER joinTableController#setJoiningGame.
	*	@see joinTableController#setJoiningGame
	*/
    @FXML
	private void handleJoinAP(){
    	interfaceData.askJoinTable(joiningGame, true);
	}

	/**
	*	handleJoinAS is the method used to join a tbale as Spectator.
	*	<br>MUST be used AFTER joinTableController#setJoiningGame.
	*	@see joinTableController#setJoiningGame
	*/
    @FXML
	private void handleJoinAS(){
		interfaceData.askJoinTable(joiningGame, false);
	}

	/**
	*	handleCancel is used to handle the user choice to cancel joining a table.
	*/
    @FXML
	private void handleCancel(){
		Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.close();
	}	
}
