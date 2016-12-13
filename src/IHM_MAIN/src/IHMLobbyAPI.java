package IHM_MAIN.src;

import java.io.IOException;

import IHM_MAIN.src.controller.PersonController;
import data.*;
import data.client.InterImplDataTable;
import ihmTable.api.IHMTableLobbyImpl;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class IHMLobbyAPI {
	
	public static class IncompleteProfileException extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}

	// The API to display specific windows
	// in the Lobby Module
	private static Stage window_to_kill;
	
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
	
	public void displayTable(InterImplDataTable inter, User user){
		IHMTableLobbyImpl it = new IHMTableLobbyImpl();
		try {
			if(IHMLobbyAPI.window_to_kill!=null){
				IHMLobbyAPI.window_to_kill.close();
				IHMLobbyAPI.window_to_kill=null;
			}
			it.displayTable(inter, user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setWindowToDestroy(Stage target){
		IHMLobbyAPI.window_to_kill = target;
	}
	
}
