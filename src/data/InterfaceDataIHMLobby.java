package data;

import java.util.ArrayList;
import java.util.List;

public class InterfaceDataIHMLobby {

	public void getListTable() {

	}

	private boolean enterMyPassword(String login, String password) {

		return false;
	}

	public void login(String login, String Password, IPData ipd) {
		enterMyPassword(login, Password);
	}

	public void logout() {

	}
	
	
	
	
	//TODO argument
	public void createProfile(Object... args) {
		
	}
	
	public void getTableInfo(GameTable g) {
		
	}
	
	public void addNewTable(GameTable g) {
		
	}
	
	//TODO arg names
	public void askJoinTable(GameTable g, boolean b) {
		
	}
	
	
	
	public Profile getLocalProfile() {
		return null;
	}
	
	public Profile getLocalProfile(String login, String password) {
		return getLocalProfile();
	}
	
	//TODO type UUID?
	public Profile getLocalProfile(int id) {
		return getLocalProfile();
	}
	
	//TODO args
	public Profile changeMyProfile(Object... args) {
		return null;
	}
	
	public void getListUsers() {
		
	}
	
	public void askRefreshUsersList() {
		
	}
	
	public void getProfileFromOtherUser(User other) {
		
	}
	
	public boolean addContact(int UUID) {
		return false;
	}
	
	public boolean deleteContact(int UUID) {
		return false;
	}
	
	public List<Contact> getContactList() {
		return new ArrayList<Contact>();
	}
	
	public boolean addCategory(String name, Object... rights) {
		return false;
		
	}
	
	public boolean deleteCategory(int UUID) {
		return false;
	}
	
	public List<ContactCategory> getCategoryList() {
		return new ArrayList<ContactCategory>();
	}
	
	public boolean modifyCategory(int UUID, String name, Object... rights) {
		return false;
	}
	
	public boolean addContactToCategory(int UUIDContact, int UUIDCategory) {
		return false;
	}
	
	public boolean removeContactFromCategory(int UUIDContact, int UUIDCategory) {
		return false;
	}
}
