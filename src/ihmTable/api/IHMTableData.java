package ihmTable.api;

/**
 * Interface for Data
 */
public interface IHMTableData {
	/**
	 * Close the table
	 */
	public void closeTable();
	/**
	 * Show a dialog and close the table
	 * @param message the message displayed in the dialog
	 */
	public void closeTable(String message);
}
