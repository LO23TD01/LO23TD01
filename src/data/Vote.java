
public class Vote {
	private User user;
	private boolean value;
	private GameTable parent;
	
	public Vote(User user, boolean value, GameTable parent) {
		super();
		this.user = user;
		this.value = value;
		this.parent = parent;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	public GameTable getParent() {
		return parent;
	}
	public void setParent(GameTable parent) {
		this.parent = parent;
	}
	
	
}
