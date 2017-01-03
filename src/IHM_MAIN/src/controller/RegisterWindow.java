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
    data.client.InterImplDataMain interImplDataMain;
    
    public RegisterWindow(){}

	/**
	*	Sets the Data API we need to call the utility methods.
	*	@param interImplDataMain An InterImplDataMain object containing the implementation of the Data API.
	*/
	public void setInterImplDataMain(InterImplDataMain interImplDataMain) {
		this.interImplDataMain = interImplDataMain;
	}
	
	/**
	*	Sets the current stage for the window.
	*	@param dialogStage The Stage to be used when displaying the window.
	*/
	public void setCurrentStage(Stage dialogStage) {
        this.currentStage = dialogStage;
    }
	
	/**
	*	Sets the focused button so that when pressing enter that button will be triggered.
	*/
	public void setButtonToBeTriggerByEnter() {
		this.validerButton.defaultButtonProperty().bind(this.validerButton.focusedProperty());
	}

	/**
	*	Cancels a given event and closes the stage.
	*	@param e An ActionEvent object. 
	*/
	public void cancel(ActionEvent e){
		Stage stage = (Stage)((Node)(e.getSource())).getScene().getWindow();
		stage.close();
	}
	
	/**
	*	Handles the form validation.
	*	@param event An ActionEvent object.
	*/
	@FXML
	private void handleValiderButton(ActionEvent event) {
		try{
			if(this.loginField.getText().equals(""))
				throw new Exception();
			if(this.pswdField.getText().equals(""))
				throw new Exception();
			try{
				this.interImplDataMain.createProfile(this.loginField.getText(), this.pswdField.getText());
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
		catch(Exception e){
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You should not have a blank password or login");
            alert.setContentText("You should not have a blank password or login");
            alert.showAndWait();
		}
	}
}