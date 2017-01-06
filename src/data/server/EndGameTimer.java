package data.server;

import java.util.TimerTask;

import data.GameTable;
import data.State;

public class EndGameTimer extends TimerTask{

	private GameTable table;
	private ServerDataEngine engine;


	public EndGameTimer(GameTable table, ServerDataEngine engine) {
		super();
		this.table = table;
		this.engine = engine;
	}

	public void run() {
	    if(table.getSame(engine.getTableList())!=null && table.getSame(engine.getTableList()).getGameState().getState()==State.END)
	    	engine.dropTable(table);
	   	//this.cancel();
	}

}
