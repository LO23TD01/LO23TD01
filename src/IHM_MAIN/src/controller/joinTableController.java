package IHM_MAIN.src.controller;

import data.GameTable;
import data.client.ClientDataEngine;
import data.client.InterfaceDataIHMLobby;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class joinTableController {
	@FXML
	Button asPlayer;
	@FXML
	Button asSpec;
	@FXML
	Button cancel;
	InterfaceDataIHMLobby interfaceData;
	ClientDataEngine clientData;
	GameTable joiningGame;
	
	public void setClientData(ClientDataEngine client){
        this.clientData = client;
    }
    public void setInterfaceDataIHM(InterfaceDataIHMLobby interf){
        this.interfaceData = interf;
    }
    public void setJoiningGame(GameTable game){
        this.joiningGame = game;
    }
    @FXML
	private void handleJoinAP(){
    	interfaceData.askJoinTable(joiningGame, true);
    	//compléter avec ouverture table de jeu 
	}
    @FXML
	private void handleJoinAS(){
		interfaceData.askJoinTable(joiningGame, false);
		//compléter avec ouveture table de jeu
	}
    @FXML
	private void handleCancel(){
		Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.close();
	}
	
	
	
	
}
