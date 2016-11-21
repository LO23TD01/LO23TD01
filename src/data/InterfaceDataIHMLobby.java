package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import network.client.ComClient;
import network.client.ComClientInterface;

public class InterfaceDataIHMLobby {

	/**
	 * Variable qui permet de communiquer avec le serveur, initialisée lors du login
	 */
	private ComClientInterface comClientInterface = null;

	/**
	 * Variable qui contient le fichier XML parsé correspondant au profile du joueur
	 */
	private Document parsedProfileXML = null;

	public void getListTable() {

	}

	/**
	 * Cette méthode permet de vérifier si le login et le password sont corrects par rapport au fichier XML contenant le
	 * profile du joueur. La variable met à jour l'attribut parsedProfileXML avec le fichier XML nouvellement parsé, ou
	 * ne change pas l'attribut si le fichier à charger est le même.
	 * 
	 * @param login
	 * @param password
	 * @return true si correct, false sinon
	 */
	private boolean enterMyPassword(String login, String password) {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			// TODO choisir un bon nom du fichier XML pour gérer le cas du doublon de profile. A voir quand la méthode
			// createProfile sera implémentée
			if (parsedProfileXML == null || !parsedProfileXML.getDocumentURI().equals(new File(login + ".xml").toURI().toString()))
				parsedProfileXML = builder.parse(new File(login + ".xml"));

			final Element racine = parsedProfileXML.getDocumentElement();
			final NodeList racineNoeuds = racine.getChildNodes();

			final int nbRacineNoeuds = racineNoeuds.getLength();
			for (int i = 0; i < nbRacineNoeuds; i++) {
				if (racineNoeuds.item(i).getNodeName() == "psw") {
					// TODO fonction de cryptage du mot de passe
					return racineNoeuds.item(i).getTextContent().equals(password);
				}
			}

		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Cette méthode vérifie si le login/mot de passe est correct. Elle connecte ensuite le client au serveur de jeu.
	 * 
	 * @param login
	 * @param password
	 * @param ipd
	 * @throws Exception
	 */
	public void login(String login, String password, IPData ipd) throws Exception {
		if (!enterMyPassword(login, password))
			throw new Exception("Mauvais mot de passe");
		comClientInterface = new ComClient(ipd.getValue(), 4000);
		// TODO choisir un port
		comClientInterface.connection(this.getLocalProfile(login, password));
	}

	public void logout() {

	}

	// TODO argument
	public void createProfile(Object... args) {

	}

	public void getTableInfo(GameTable g) {

	}

	public void addNewTable(GameTable g) {

	}

	// TODO arg names
	public void askJoinTable(GameTable g, boolean b) {

	}

	public Profile getLocalProfile() {
		return null;
	}

	public Profile getLocalProfile(String login, String password) {
		return getLocalProfile();
	}

	// TODO type UUID?
	public Profile getLocalProfile(int id) {
		return getLocalProfile();
	}

	// TODO args
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
