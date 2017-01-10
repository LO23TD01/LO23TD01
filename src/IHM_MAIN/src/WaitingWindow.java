package IHM_MAIN.src;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class WaitingWindow{
	Stage stage;
	public WaitingWindow(Window parent) {	
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/IHM_MAIN/src/view/Loading.fxml"));
			AnchorPane root;
			root = (AnchorPane) fxmlLoader.load();
			Scene new_scene = new Scene(root);
			stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(parent);
			stage.setScene(new_scene);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
	}
	public void showAndWait(){
		stage.showAndWait();
	}
	public void show(){
		stage.show();
	}
	public void close(){
        Platform.runLater(new Runnable() {
            @Override public void run() {
				stage.close();
            }
        });
	}
}
