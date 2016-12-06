package IHM_MAIN.src.controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RegisterWindow{
		
	@FXML
	TextField pseudoField;
	@FXML
	TextField loginField;
	@FXML
	TextField nomField;
	@FXML
	TextField prenomField;
	@FXML
	TextField ageField;
	
	@FXML
	Button validerButton;
	
	
	public RegisterWindow(){
			
	}

	public void cancel(ActionEvent e){
		Stage stage = (Stage)((Node)(e.getSource())).getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void handleValiderButton() {
		//send create profile request to server
	}
	
	
}