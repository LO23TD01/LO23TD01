package IHM_MAIN.src.controller;

import data.client.ClientDataEngine;
import data.client.InterfaceDataIHMLobby;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
public class RegisterWindow{
	//FXML field instances from view :

	@FXML
	TextField loginField;
	@FXML
	TextField pswdField;
	
	//FXML button instance from the view
	@FXML
	Button validerButton;
	
	//we need these tow instances and the corresponding setters so we can then use them to access the data/IHM-Lobby interface methods
	data.client.ClientDataEngine clientData;
    data.client.InterfaceDataIHMLobby interfaceData;
    //setters for the ClientDataEngine and InterfaceDataIHMLobby instances
    public void setClientData(ClientDataEngine client){
		this.clientData = client;
	}
    
	public void setInterfaceDataIHM(InterfaceDataIHMLobby interf){
		this.interfaceData = interf;
	}
	
	public RegisterWindow(){
			
	}

	public void cancel(ActionEvent e){
		Stage stage = (Stage)((Node)(e.getSource())).getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void handleValiderButton() {
		//send create profile request to server
		this.interfaceData.createProfile(this.loginField.getText(), this.pswdField.getText());
	}
	
	
}