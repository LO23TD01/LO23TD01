package IHM_MAIN.src;

import IHM_MAIN.src.controller.PersonController;
import data.*;
import javafx.fxml.FXMLLoader;

public class IHMLobbyAPI {
	
	public static class IncompleteProfileException extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}

	// The API to display specific windows
	// in the Lobby Module
	public IHMLobbyAPI(){
		
	}

	// DATA will use this method to display
	// a user's profile after requesting it
	public void displayProfile(Profile profile) throws IncompleteProfileException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/GestionProfil.fxml"));
		PersonController controller = loader.getController();
		controller.setProfile(profile);
		controller.disableButtonsAndFields();
	}
}
