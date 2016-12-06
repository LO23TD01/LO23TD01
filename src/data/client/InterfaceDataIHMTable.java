package data.client;

import data.ChatMessage;
import data.UserRole;

public interface InterfaceDataIHMTable {
	public UserRole getUserRole();
	
	public void throwDice(boolean a, boolean b, boolean c);
	
	public void selectDice(boolean a, boolean b, boolean c);
	
	public void launchGame();
	
	public void quitGame();
	
	void sendMessage(ChatMessage msg);
	
	public void acceptReplay();
	
	public void refuseReplay();
}
