package data.client;

import data.UserRole;

public interface InterfaceDataIHMTable {
	public UserRole getUserRole();
	
	public void throwDice(boolean a, boolean b, boolean c);
	
	public void selectDice(boolean a, boolean b, boolean c);
	
	public void launchGame();
	
	public void quitGame();
	
	public void sendMessage(String msg);
	
	public void acceptReplay();
	
	public void refuseReplay();
}
