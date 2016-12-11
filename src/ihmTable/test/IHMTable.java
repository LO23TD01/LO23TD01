package ihmTable.test;

import java.io.IOException;

import data.User;
import data.client.InterImplDataTable;
import ihmTable.api.IHMTableLobby;
import ihmTable.api.IHMTableLobbyImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class IHMTable extends Application {

	private IHMTableLobby ihmTableLobby;

	private InterImplDataTable interImplDataTable;

	@Override
	public void start(Stage stage) throws IOException {
		Button root = new Button("Launch table");
		root.setOnAction(event -> {
			try {
				onLaunchButtonClick();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Scene scene = new Scene(root);
		stage.setTitle("Lobby");
		stage.setScene(scene);
		stage.setMaximized(false);
		stage.show();
	}

	private void onLaunchButtonClick() throws IOException {
		ihmTableLobby = new IHMTableLobbyImpl();
		if(interImplDataTable == null)
			interImplDataTable = new InterImplDataTable();
		//Set user as the creator for testing purpose
		User user = interImplDataTable.getActualTable().getCreator();
		ihmTableLobby.displayTable(interImplDataTable, user);
	}

	public InterImplDataTable getInterImplDataTable() {
		return interImplDataTable;
	}

	public void setInterImplDataTable(InterImplDataTable interImplDataTable) {
		this.interImplDataTable = interImplDataTable;
	}

	public static void main(String[] args) {
		launch(args);
	}

}