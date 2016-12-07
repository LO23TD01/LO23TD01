package ihmTable.api;

import java.io.IOException;

import data.User;
import data.client.InterImplDataTable;

public interface IHMTableLobby {
	public void displayTable(InterImplDataTable interImplDataTable, User user) throws IOException;
}
