package ihmTable.api;

public class IHMTableDataImpl implements IHMTableData {

	@Override
	public void closeTable() {
		IHMTableLobbyImpl.getStage().close();
	}
}
