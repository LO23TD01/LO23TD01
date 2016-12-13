package IHM_MAIN.src.controller;

import data.client.ClientDataEngine;
import data.client.InterImplDataMain;
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
	
	//this is an instance of the current stage so we can close it when the user validate his new profile
	private Stage currentStage;
	
	//we need these tow instances and the corresponding setters so we can then use them to access the data/IHM-Lobby interface methods
	data.client.ClientDataEngine clientData;
    data.client.InterfaceDataIHMLobby interfaceData;
    data.client.InterImplDataMain interImplDataMain;
    //setters for the ClientDataEngine and InterfaceDataIHMLobby instances
    public void setClientData(ClientDataEngine client){
		this.clientData = client;
	}
	public void setInterfaceDataIHM(InterfaceDataIHMLobby interf){
		this.interfaceData = interf;
	}
	public void setInterImplDataMain(InterImplDataMain interImplDataMain) {
		this.interImplDataMain = interImplDataMain;
	}
	
	public void setCurrentStage(Stage dialogStage) {
        this.currentStage = dialogStage;
    }
	
	public RegisterWindow(){
			
	}

	public void cancel(ActionEvent e){
		Stage stage = (Stage)((Node)(e.getSource())).getScene().getWindow();
		stage.close();
	}
	
	//handleValiderButton

	 @FXML
	 private void handleValiderButton(ActionEvent event) {
		
		try{
			System.out.print("\ncreate a new profile with \nlogin : "+this.loginField.getText() + "\npassword : " + this.pswdField.getText() +"\n");
			this.interImplDataMain.createProfile(this.loginField.getText(), this.pswdField.getText());
			System.out.print("just created a new profile\n");
			currentStage.close();
		}
		catch(Exception e){
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while creating profile");
            alert.setContentText("Error while creating profile");
            alert.showAndWait();
		}
		
	 }
	
}