package ihmTable.api;

import java.util.Optional;

import ihmTable.controller.TableController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

/**
 * Implementation of {@link IHMTableData}
 *
 * @see IHMTableData
 */
public class IHMTableDataImpl implements IHMTableData {

	/**
	 * Title of the dialog
	 */
	private static final String ERROR_ALERT_HEADER = "Une erreur s'est produite";

	/**
	 * @see ihmTable.api.IHMTableData#closeTable()
	 */
	@Override
	public void closeTable() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				TableController.closeVoteAlert();
				IHMTableLobbyImpl.getStage().close();
			}
		});
	}

	/**
	 * @see ihmTable.api.IHMTableData#closeTable(java.lang.String)
	 */
	@Override
	public void closeTable(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				showErrorAlert(message);
			}
		});
	}

	/**
	 * Display a dialog with a message
	 * @param message the message displayed in the dialog
	 */
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
