package IHM_MAIN.src.model;

import javafx.beans.property.SimpleStringProperty;

public class Game {
    private SimpleStringProperty name;
    private SimpleStringProperty players;
    private SimpleStringProperty spectators;
    private SimpleStringProperty owner;
 
    public Game(String n, String p, String s, String o) {
        this.name = new SimpleStringProperty(n);
        this.players = new SimpleStringProperty(p);
        this.spectators = new SimpleStringProperty(s);
        this.owner = new SimpleStringProperty(o);
    }

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public void setPlayers(SimpleStringProperty players) {
		this.players = players;
	}

	public void setSpectators(SimpleStringProperty spectators) {
		this.spectators = spectators;
	}

	public void setOwner(SimpleStringProperty owner) {
		this.owner = owner;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public SimpleStringProperty getPlayers() {
		return players;
	}

	public SimpleStringProperty getSpectators() {
		return spectators;
	}

	public SimpleStringProperty getOwner() {
		return owner;
	}
 
        
}