package IHM_MAIN.src;

import java.io.IOException;

import IHM_MAIN.src.controller.PersonController;
import IHM_MAIN.src.controller.joinTableController;
import data.*;
import data.client.InterImplDataTable;
import ihmTable.api.IHMTableLobbyImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

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
	public void displayProfile(User user, Window parent) throws IncompleteProfileException{
		/*System.out.println(profile.getLogin());
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/GestionProfil.fxml"));
		PersonController controller = loader.getController();
		controller.setProfile(profile);
		controller.disableButtonsAndFields();*/
		try {
 			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./view/GestionProfil.fxml"));
 			AnchorPane root;
			root = (AnchorPane) fxmlLoader.load();
 			//Window parent = createGame.getScene().getWindow();
 			PersonController controller = (PersonController) fxmlLoader.getController();
 			controller.setPerson(user);
 			controller.disableButtonsAndFields();

 			Stage stage = new Stage();
 			stage.setScene(new Scene(root));
 			stage.initModality(Modality.WINDOW_MODAL);
 			stage.initOwner(parent);
 		    stage.setTitle("Afficher profil");
 		    stage.setResizable(false);
 			stage.show();

 		} catch (Exception e1) {
 			e1.printStackTrace();
 		}
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
