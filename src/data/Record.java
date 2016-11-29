package data;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Record {
	private final ObservableList<GameState> data = FXCollections.observableArrayList();

	public Record() {
	}

	public Record(List<GameState> data) {
		this.data.addAll(data);
	}

	public void addState(GameState s) {
		this.data.add(s);
	}

	public ObservableList<GameState> getData() {
		return data;
	}

	public void setData(List<GameState> data) {
		this.data.clear();
		this.data.addAll(data);
	}

}
