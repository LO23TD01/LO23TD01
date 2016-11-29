package IHM_MAIN.src.controller;

import IHM_MAIN.src.WaitingWindow;
import IHM_MAIN.src.MainApp.DataConnection;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TableCreation{
		
    @FXML
    CheckBox spectatorsAllowed;

    @FXML
    PasswordField tablePassword;

    @FXML
    ComboBox<Integer> maxPlayers;

    @FXML
    ComboBox<Integer> minPlayers;

    @FXML
    ComboBox<Integer> maxTokens;

    @FXML
    CheckBox chatEnabled;

    @FXML
    TextField tableName;

	public TableCreation(){
			
	}

	public void cancel(ActionEvent e){
		Stage stage = (Stage)((Node)(e.getSource())).getScene().getWindow();
		stage.close();
	}
	
	public void send_create_table_signal(){
		// data.createNewTable(name, pwd, min, max, max_token, withSpec, withChat);
		/*Alert alert = new Alert(AlertType.INFORMATION, "Signal sent to data, preparing party:\n"
				+ "Table Name:"+tableName.getText()+"\n" 
				+ "Password:"+tablePassword.getText()+"\n"
				+ "Max Players:"+maxPlayers.getValue()+"\n"
				+ "Min Players:"+minPlayers.getValue()+"\n"
				+ "Max Tokens:"+maxTokens.getValue()+"\n"
				+ "With Chat:"+chatEnabled.isSelected()+"\n"
				+ "With Spec:"+spectatorsAllowed.isSelected()+"\n");
		alert.showAndWait();
		Stage stage = (Stage) tableName.getScene().getWindow();
		stage.close();*/
		
		Stage stage = (Stage) tableName.getScene().getWindow();
		WaitingWindow t = new WaitingWindow(stage);
		DataConnection data = new DataConnection(t);
		data.start();
		t.showAndWait();
		stage.close();

	}
	public void open_table(){
		
	}
	
	public void ok(){
		send_create_table_signal();
		open_table();
	}
	
	private class DataConnection extends Thread{
		WaitingWindow waitingWindow;
		
		public void run(){
			try {
					Thread.sleep(1000);
					waitingWindow.close();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public DataConnection(WaitingWindow t){
			waitingWindow = t;
		}
	}

}