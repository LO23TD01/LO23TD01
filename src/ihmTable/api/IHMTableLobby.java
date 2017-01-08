package ihmTable.api;

import java.io.IOException;

import data.User;
import data.client.InterImplDataTable;

/**
 * Interface for IHM Lobby
 */
public interface IHMTableLobby {
	/**
	 * Display a table view
	 * @param interImplDataTable the data inferface
	 * @param user the local user
	 * @throws IOException
	 *
	 * @see InterImplDataTable
	 * @see User
	 */
	public void displayTable(InterImplDataTable interImplDataTable, User user) throws IOException;
}
