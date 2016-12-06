package data.client;

import java.io.File;
import java.util.List;
import java.util.UUID;

import data.Contact;
import data.ContactCategory;
import data.GameTable;
import data.IPData;
import data.Profile;
import data.Rights;
import data.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.client.ComClient;

public class InterImplDataMain implements InterfaceDataIHMLobby{

	private ClientDataEngine dataEngine;
	private ObservableList<User> userList;
	private ObservableList<GameTable> tableList;

	//appelé uniquement par le ClientDataEngine
	public InterImplDataMain(ClientDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;
		this.userList = this.dataEngine.getUserList();
		this.tableList = this.dataEngine.getTableList();
	}

	@Override
	public void getListTable() {
		// TODO Auto-generated method stub

	}

	/**
	 * Cette mÃ©thode vÃ©rifie si le login/mot de passe est correct. Elle connecte ensuite le client au serveur de jeu.
	 *
	 * @param login
	 * @param password
	 * @param ipd
	 * @throws Exception
	 */
	@Override
	public void login(String login, String password, IPData ipd) {
//		if (!this.dataEngine.getProfileManager().checkPassword(login, password))
//			throw new Exception("Mauvais mot de passe");
		this.dataEngine.setComClientInterface(new ComClient(ipd.getValue(), 4000));
		// TODO choisir un port
		this.dataEngine.getComClientInterface().connection(this.getLocalProfile(login, password));
	}

	@Override
	public void logout() {
		this.dataEngine.getComClientInterface().logoutUserRequest(this.dataEngine.getProfileManager().getCurrentProfile().getUUID());
		this.dataEngine.getProfileManager().logout(getLocalProfile());
		this.tableList.clear();
		this.userList.clear();
	}

	@Override
	// Cette fonction est appelée par IHM lobby lors de la création d'un profil c'est pour cela que l'on xmlise ce profile.
	public void createProfile(String login, String psw) {
		this.dataEngine.getProfileManager().createProfile(login,psw).Xmlise();
	}


	@Override
	public void askJoinTable(GameTable g, boolean b) {
		this.dataEngine.getComClientInterface().askJoinTable(this.dataEngine.getProfileManager().getCurrentProfile().getUUID(), g.getUid(), b);
	}

	@Override
	public Profile getLocalProfile() {
		return this.dataEngine.getProfileManager().getCurrentProfile();
	}

	@Override
	public Profile getLocalProfile(String login, String password) {
		return this.dataEngine.getProfileManager().getLocalProfile(login,password);
	}

	@Override
	public Profile getLocalProfile(UUID id) {
		return this.dataEngine.getProfileManager().getLocalProfile(id);
	}

	@Override
	// Cette fonction est appelée par IHM lobby lors de la modification d'un profil c'est pour cela que l'on xmlise ce profile.
	public Profile changeMyProfile(Profile new_profile) {
		// Si jamais l'utilisateur veut changer son mdp/login on supprime l'ancien profile XML et on en crée un nouveau.
		if (getLocalProfile().getLogin() != new_profile.getLogin() || getLocalProfile().getPsw() != new_profile.getPsw()){
			String path = "MesProfiles\\"+getLocalProfile().getLogin()+"-"+getLocalProfile().getPsw()+".xml";
			File file = new File(path);
			file.delete();
		}
		Profile p =  this.dataEngine.getProfileManager().modifyProfile(getLocalProfile(), new_profile);
		p.Xmlise();
		return p;
	}

	@Override
	public void getListUsers() {
		this.dataEngine.getComClientInterface().updateUsersList(this.dataEngine.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public void askRefreshUsersList() {
		this.dataEngine.getComClientInterface().askRefreshUsersList( this.dataEngine.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public void getProfileFromOtherUser(User other) {
		this.dataEngine.getComClientInterface().getProfile(other.getPublicData().getUUID(),
				 this.dataEngine.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public boolean addContact(UUID uuid) {
		//TOREVIEW il fuat pas modifier le XML aussi par hasard ?
		return  this.dataEngine.getProfileManager().getCurrentProfile().getClient()
				.addContact(new Contact(uuid, null, null, null, 0));
	}

	@Override
	public boolean deleteContact(UUID uuid) {
		//TOREVIEW il fuat pas modifier le XML aussi par hasard ?
		return  this.dataEngine.getProfileManager().getCurrentProfile().getClient().removeContact(uuid);
	}

	@Override
	public List<Contact> getContactList() {
		return  this.dataEngine.getProfileManager().getCurrentProfile().getClient().getContactList();
	}

	@Override
	public boolean addCategory(String name, Object... rights) {
		//TOREVIEW il fuat pas modifier le XML aussi par hasard ?
		return  this.dataEngine.getProfileManager().getCurrentProfile().getClient().addCategory(name, (Rights) rights[0]);
	}

	@Override
	public boolean deleteCategory(UUID uuid) {
		//TOREVIEW il fuat pas modifier le XML aussi par hasard ?
		return  this.dataEngine.getProfileManager().getCurrentProfile().getClient().deleteCategoryByUuid(uuid);
	}

	@Override
	public List<ContactCategory> getCategoryList() {
		return  this.dataEngine.getProfileManager().getCurrentProfile().getClient().getCategoryList();
	}

	@Override
	public boolean modifyCategory(UUID uuid, String name, Object... rights) {
		//TOREVIEW il fuat pas modifier le XML aussi par hasard ?
		return  this.dataEngine.getProfileManager().getCurrentProfile().getClient().modifyCategory(uuid, name, (Rights) rights[0]);
	}

	@Override
	public boolean addContactToCategory(UUID uuidContact, UUID uuidCategory) {
		//TOREVIEW il fuat pas modifier le XML aussi par hasard ?
		return  this.dataEngine.getProfileManager().getCurrentProfile().getClient().addContactToCategory(uuidContact, uuidCategory);
	}

	@Override
	public boolean removeContactFromCategory(UUID uuidContact, UUID uuidCategory) {
		//TOREVIEW il fuat pas modifier le XML aussi par hasard ?
		return  this.dataEngine.getProfileManager().getCurrentProfile().getClient().removeContactFromCategory(uuidContact,
				uuidCategory);
	}




}
