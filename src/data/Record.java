import java.util.List;

public class Record {
	private List<GameState> data;


	public Record(List<GameState> data, GameTable parent) {
		super();
		this.data = data;
	}

	public void addState(GameState s){
		this.data.add(s);
	}

	public List<GameState> getData() {
		return data;
	}

	public void setData(List<GameState> data) {
		this.data = data;
	}

	
	
}
