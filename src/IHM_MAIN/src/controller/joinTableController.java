package IHM_MAIN.src.controller;

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
	
	private void handleJoinAP(){
		
	}
	
	private void handleJoinAS(){
		
	}
	
	private void handleCancel(){
		Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.close();
	}
	
	
	
	
}
