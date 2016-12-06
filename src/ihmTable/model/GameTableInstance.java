package ihmTable.model;

import java.io.IOException;
import java.util.List;

import data.ChatMessage;
import data.GameTable;
import data.Parameters;
import data.Profile;
import data.User;
import ihmTable.controller.TableController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameTableInstance {

	private GameTable gameTableInstance;

//	public static GameTable getGameTableInstance() {
//		if (gameTableInstance == null)
//		{
//			synchronized(GameTableInstance.class)
//			{
//				if (gameTableInstance == null)
//				{
//					//erreur car pas initialisé.
//				}
//			}
//		}
//		return gameTableInstance;
//	}

	public GameTableInstance(GameTable gameTableInstance) {
		super();
		this.gameTableInstance = gameTableInstance;
	}

	public GameTableInstance(String name, User creator, Parameters parameters, List<User> playerList, List<User> spectatorList) throws IOException
	{
		super();
		this.gameTableInstance = new GameTable(name, creator, parameters, playerList, spectatorList);
		openTable();
	}
	//todo faire les contstructeurs pour les autres cas.

	private void openTable() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Table.fxml"));
        Parent table = (Parent) fxmlLoader.load();
        TableController controller = (TableController)fxmlLoader.getController();
		controller.setModel(gameTableInstance);
        Stage stage = new Stage();
        stage.setTitle(gameTableInstance.getName());
        stage.setScene(new Scene(table));
        stage.setMaximized(true);
        stage.show();
	}

}
