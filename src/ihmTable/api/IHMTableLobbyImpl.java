package ihmTable.api;

import java.io.IOException;
import java.util.Optional;

import data.User;
import data.client.InterImplDataTable;
import ihmTable.controller.TableController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class IHMTableLobbyImpl implements IHMTableLobby {

	private static final String EXIT_GAME_ALERT_HEADER = "Partie en cours";
	private static final String EXIT_GAME_ALERT_CONTENT = "Vous allez quitter une partie en cours.\nVoulez-vous continuer ?";

	private static InterImplDataTable interImplDataTable;
	private static Stage stage;

	public void displayTable(InterImplDataTable interImplDataTable, User user) throws IOException {
		this.interImplDataTable = interImplDataTable;

		// Loading table
		FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Table.fxml"));
		Pane table = (Pane) tableLoader.load();

		// Displaying the new stage
		stage = new Stage();
		stage.setTitle(interImplDataTable.getActualTable().getName());
		stage.setScene(new Scene(table));
		stage.setMinWidth(800);
		stage.setMinHeight(600);
		stage.setMaximized(true);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		    	event.consume();
		    	showExitModal();
		    }
		});

		// Setting data to table
		TableController tableController = (TableController) tableLoader.getController();
		tableController.setData(interImplDataTable, user);
	}

	public static void showExitModal() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setHeaderText(EXIT_GAME_ALERT_HEADER);
    	alert.setContentText(EXIT_GAME_ALERT_CONTENT);
    	alert.initStyle(StageStyle.UNDECORATED);

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		interImplDataTable.quitGame();
    		stage.close();
    	} else {
    		alert.close();
    	}
	}

	public static Stage getStage() {
		return stage;
	}
}
