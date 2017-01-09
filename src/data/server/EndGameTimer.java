package data.server;

import java.util.TimerTask;

import data.GameTable;
import data.State;

public class EndGameTimer extends TimerTask{

	private GameTable table;
	private ServerDataEngine engine;
	private boolean activated;


	public EndGameTimer(GameTable table, ServerDataEngine engine) {
		super();
		this.table = table;
		this.engine = engine;
		this.activated =true;
	}

	public void run() {
	    if(activated && table.getSame(engine.getTableList())!=null && table.getSame(engine.getTableList()).getGameState().getState()==State.END)
	    	engine.dropTable(table);
	   	//this.cancel();
	}

	public void deactivate(GameTable table)
	{
		if(table.isSame(this.table))
			this.activated=false;
	}
}
