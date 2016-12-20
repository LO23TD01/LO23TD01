package IHM_MAIN.src.controller;

import data.GameTable;
import data.client.InterImplDataMain;
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
	InterImplDataMain interfaceData;
	
	GameTable joiningGame;
	
    public void setInterfaceData(InterImplDataMain interf){
        this.interfaceData = interf;
    }
    public void setJoiningGame(GameTable game){
        this.joiningGame = game;
    }
    @FXML
	private void handleJoinAP(){
    	interfaceData.askJoinTable(joiningGame, true);
    	System.out.println("coucou");
	}
    @FXML
	private void handleJoinAS(){
		interfaceData.askJoinTable(joiningGame, false);
	}
    @FXML
	private void handleCancel(){
		Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.close();
	}
	
	
	
	
}
