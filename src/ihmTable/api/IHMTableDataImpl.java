package ihmTable.api;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

public class IHMTableDataImpl implements IHMTableData {

	private static final String ERROR_ALERT_HEADER = "Une erreur s'est produite";

	@Override
	public void closeTable() {
		IHMTableLobbyImpl.getStage().close();
	}

	@Override
	public void closeTable(String message) {
		showErrorAlert(message);
	}

	private void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(ERROR_ALERT_HEADER);
		alert.setContentText(message);
    	alert.initStyle(StageStyle.UNDECORATED);

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK) {
    		closeTable();
    	}
	}
}
