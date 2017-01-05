package ihmTable.api;

import java.io.IOException;

import data.User;
import data.client.InterImplDataTable;
import ihmTable.controller.TableController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IHMTableLobbyImpl implements IHMTableLobby {

	public void displayTable(InterImplDataTable interImplDataTable, User user) throws IOException {
		// Loading table
		FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Table.fxml"));
		Pane table = (Pane) tableLoader.load();

		// Displaying the new stage
		Stage stage = new Stage();
		stage.setTitle(interImplDataTable.getActualTable().getName());
		stage.setScene(new Scene(table));
		stage.setMinWidth(800);
		stage.setMinHeight(600);
		stage.setMaximized(true);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.show();

		// Setting data to table
		TableController tableController = (TableController) tableLoader.getController();
		tableController.setData(interImplDataTable, user);
	}
}
