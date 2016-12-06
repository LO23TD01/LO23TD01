package data.client;

import java.util.List;
import java.util.UUID;

import data.Contact;
import data.ContactCategory;
import data.GameTable;
import data.IPData;
import data.Profile;
import data.User;

public interface InterfaceDataIHMLobby {

	public void getListTable();

	/**
	 * Cette méthode vérifie si le login/mot de passe est correct. Elle connecte ensuite le client au serveur de jeu.
	 *
	 * @param login
	 * @param password
	 * @param ipd
	 * @throws Exception
	 */
	public void login(String login, String password, IPData ipd) throws Exception;

	public void logout();

	// TODO argument
	public void createProfile(String login, String psw);

	public void getTableInfo(GameTable g);

	public void addNewTable(GameTable g);

	// TODO arg names
	public void askJoinTable(GameTable g, boolean b);

	public Profile getLocalProfile();

	public Profile getLocalProfile(String login, String password);

	public Profile getLocalProfile(UUID id);

	// TODO args
	public Profile changeMyProfile(Profile new_profile);

	public void getListUsers();

	public void askRefreshUsersList();

	public void getProfileFromOtherUser(User other);

	public boolean addContact(UUID uuid);

	public boolean deleteContact(UUID uuid);

	public List<Contact> getContactList();

	public boolean addCategory(String name, Object... rights);

	public boolean deleteCategory(UUID uuid);

	public List<ContactCategory> getCategoryList();

	public boolean modifyCategory(UUID uuid, String name, Object... rights);

	public boolean addContactToCategory(UUID uuidContact, UUID uuidCategory);

	public boolean removeContactFromCategory(UUID uuidContact, UUID uuidCategory);


}
