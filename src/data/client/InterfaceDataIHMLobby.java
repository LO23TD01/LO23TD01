package data.client;

import java.util.List;
import java.util.UUID;

import data.Contact;
import data.ContactCategory;
import data.GameTable;
import data.IPData;
import data.Profile;
import data.Rules;
import data.User;

public interface InterfaceDataIHMLobby {

	/**
	 * Cette méthode vérifie si le login/mot de passe est correct. Elle connecte ensuite le client au serveur de jeu.
	 *
	 * @param login
	 * @param password
	 * @param ipd
	 * @return 
	 * @throws Exception
	 */
	public boolean login(String login, String password, IPData ipd) throws Exception;

	public void logout();

	// TODO argument
	public void createProfile(String login, String psw);

	// TODO arg names
	public void askJoinTable(GameTable g, boolean b);

	public Profile getLocalProfile();

	public Profile getLocalProfile(String login, String password);

	public Profile getLocalProfile(UUID id);

	// TODO args
	public Profile changeMyProfile(Profile new_profile);

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

	public void createNewTable(UUID user, String name, int min, int max, int token, boolean withSpec, boolean withChat, Rules rules);

}
