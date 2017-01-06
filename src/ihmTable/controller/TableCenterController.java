package ihmTable.controller;

import java.io.IOException;
import java.util.HashMap;

import data.GameTable;
import data.User;
import data.client.InterImplDataTable;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TableCenterController {

    @FXML
    private GridPane tableCenterView;

    private GameTable gameTable;
    private DiceLauncherController diceLaunchController;
	private HashMap<User, Pane> playerViews;
	private ObservableList<User> playersList;
	private PlayerStatsController playerStatsController;

	/**
	 * Initialize the controller
	 */
	public void initialize() throws IOException {
		this.playerViews = new HashMap<User, Pane>();

		//DiceLaucher creation
		FXMLLoader diceLauncherLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/DiceLauncher.fxml"));
		addToTableCenterView(diceLauncherLoader.load(), 1, 1, VPos.CENTER, HPos.CENTER);
		this.diceLaunchController = diceLauncherLoader.getController();
	}

	/**
	 * Set data to the controller
	 * @param interImplDataTable the Data's interface
	 * @param user the connected user
	 * @param playerStatsController the controller of the player stats view
	 * @throws IOException
	 */
	public void setData(InterImplDataTable interImplDataTable, User user, PlayerStatsController playerStatsController) throws IOException {
		this.gameTable = interImplDataTable.getActualTable();
		this.diceLaunchController.setData(interImplDataTable, user);
		this.playersList = this.gameTable.getPlayerList();
		this.playerStatsController = playerStatsController;
		initPlayers();
	}

	/**
	 * Create view for each players and add listener to player list
	 * @throws IOException
	 */
	private void initPlayers() throws IOException {
		for(User player : this.playersList) {
			createPlayerView(player);
		}
		this.playersList.addListener((ListChangeListener.Change<? extends User> change) -> onPlayerListChange(change));
	}

	/**
	 * Update the view when players list changes
	 * @param change the changes occurred in players list
	 */
	private void onPlayerListChange(ListChangeListener.Change<? extends User> change) {
		while(change.next()) {
			if (change.wasAdded()) {
				for (User addedUser : change.getAddedSubList()) {
					try {
						createPlayerView(addedUser);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if(change.wasRemoved()) {
				for(User removedUser : change.getRemoved()) {
					this.tableCenterView.getChildren().remove(this.playerViews.get(removedUser.getPublicData().getUUID()));
					this.playerViews.remove(removedUser);
				}
			}
		}
	}

	/**
	 * Create a view for the given user
	 * @param user user for who the view should be created
	 * @throws IOException
	 */
	private void createPlayerView(User user) throws IOException {
		int playersCount = playerViews.size() + 1;
		if(playersCount < 9) {
			FXMLLoader playerLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Player.fxml"));
			Pane player = playerLoader.load();
			PlayerController playerController = playerLoader.getController();
			playerController.setData(this.gameTable.getGameState(), user, this.playerStatsController);
			this.playerViews.put(user, player);
			//depending on the player number a position is given in the grid
			switch(playersCount) {
			case 1:
				addToTableCenterView(player, 1, 0, VPos.TOP, HPos.CENTER);
				break;
			case 2:
				addToTableCenterView(player, 1, 2, VPos.BOTTOM, HPos.CENTER);
				break;
			case 3:
				addToTableCenterView(player, 0, 1, VPos.CENTER, HPos.CENTER);
				break;
			case 4:
				addToTableCenterView(player, 2, 1, VPos.CENTER, HPos.CENTER);
				break;
			case 5:
				addToTableCenterView(player, 0, 0, VPos.BOTTOM, HPos.RIGHT);
				break;
			case 6:
				addToTableCenterView(player, 2, 2, VPos.TOP, HPos.LEFT);
				break;
			case 7:
				addToTableCenterView(player, 2, 0, VPos.BOTTOM, HPos.LEFT);
				break;
			case 8:
				addToTableCenterView(player, 0, 2, VPos.TOP, HPos.RIGHT);
				break;
			}
		}
	}

	/**
	 * Add the given pane the grid according to the given column, row, vertical position and horizontal position
	 * @param pane pane to add to the grid
	 * @param columnIndex column in which the pane should be added
	 * @param rowIndex row in which the pane should be added
	 * @param vPos vertical position of the pane in the cell
	 * @param hPos horizontal position of the pane in the cell
	 */
	private void addToTableCenterView(Pane pane, int columnIndex, int rowIndex, VPos vPos, HPos hPos) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	GridPane.setHalignment(pane, hPos);
				GridPane.setValignment(pane, vPos);
		    	tableCenterView.add(pane, columnIndex, rowIndex);
		    }
		});
	}
}
