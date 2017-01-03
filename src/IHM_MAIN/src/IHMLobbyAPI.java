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

/**
*	IHMLobbyAPI is the API used by Data to display
*	tables or profiles after a request
*
*/
public class IHMLobbyAPI {
	
	public static class IncompleteProfileException extends Exception{
		private static final long serialVersionUID = 1L;
	}

	// The API to display specific windows
	// in the Lobby Module
	private static Stage window_to_kill;
	
	public IHMLobbyAPI(){}

	/**
	*	IHMLobbyAPI#displayProfile allows the opening of a profile window, 
	*	and takes a Profile and a parent Window.
	*	<br>This method is to be used by Data after a profile request.
	*	@param profil A Profile object, containing the profile to be displayed.
	*	@param parent The parent Window, used to give the profile window a parent to attach to.
	*	@throws IncompleteProfileException
	*	@see Profile
	*	@see Window
	*/
	public void displayProfile(Profile profil, Window parent) throws IncompleteProfileException{
		try {
 			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./view/GestionProfil.fxml"));
 			AnchorPane root;
			root = (AnchorPane) fxmlLoader.load();
 			//Window parent = createGame.getScene().getWindow();
 			PersonController controller = (PersonController) fxmlLoader.getController();
 			controller.setPerson(profil);
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
	
	/**
	*	IHMLobbyAPI#displayTable allows the opening of a table window, 
	*	and takes a Table implementation and a User.
	*	<br>This method is to be used by Data after a table request.
	*	@param inter An InterImplDataTable object, containing the table data to be displayed.
	*	@param user The User object used to display the table to.
	*	@see InterImplDataTable
	*	@see User
	*/
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

	/**
	*	IHMLobbyAPI#setWindowToDestroy sets the next window to kill when displaying a table.
	*	<br>This method is <b>only</b> to be used when killing the window that requested the table.
	*	@param target A Stage object, containing the window to be destroyed.
	*	@see Stage
	*/
	public void setWindowToDestroy(Stage target){
		IHMLobbyAPI.window_to_kill = target;
	}
	
}
