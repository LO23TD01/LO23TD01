package data.client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import data.Client;
import data.Profile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProfileManager {
	private final BooleanProperty hasProfileOpened = new SimpleBooleanProperty();
	private final ObjectProperty<Profile> currentProfile = new SimpleObjectProperty<Profile>();
	/**
	 * Variable qui contient le fichier XML parsé le plus récemment
	 */
	private ObjectProperty<Document> parsedProfileXML = new SimpleObjectProperty<Document>();

	
	//Diff�rentes fa�on de cr�er un Profile
	public Profile createProfile() {
		return new Profile();
	}
	
	public Profile createProfile(UUID uuid) {
		return new Profile(uuid);
	}

	public Profile createProfile(UUID uuid, String nickname, String firstName, String surName, int age) {
		return new Profile(uuid,nickname,firstName,surName,age);
	}
	
	// Normalement, c'est celui la que doit appeler IHM lobby lors de la cr�ation du premier profil
	public Profile createProfile(String login, String psw) {
		return new Profile(login,psw);
	}
	
	public Profile createProfile(String login, String nickname, String psw, String firstName, String surName, int age) {
		return new Profile(login,nickname,psw,firstName,surName,age);
	}

	public Profile createProfile(UUID uuid, String login, String nickname, String psw, String firstName, String surName, int age, Image avatar, int nbGameWon, int nbGameLost,
			int nbGameAbandonned, Client client){
		return new Profile(uuid,login,nickname,psw,firstName,surName,age,avatar,nbGameWon,nbGameLost,nbGameAbandonned,client);
	}

	/**
	 * Cette méthode permet de vérifier si le login et le password sont corrects
	 * par rapport au fichier XML contenant le profile du joueur. La variable
	 * met à jour l'attribut parsedProfileXML avec le fichier XML nouvellement
	 * parsé, ou ne change pas l'attribut si le fichier à charger est le même.
	 *
	 * @param login
	 * @param psw
	 * @return true si correct, false sinon
	 */
	public Boolean checkPassword(String login, String psw) {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			// TODO choisir un bon nom du fichier XML pour gérer le cas du
			// doublon de profile. A voir quand la méthode
			// createProfile sera implémentée
			if (parsedProfileXML == null
					|| !parsedProfileXML.get().getDocumentURI().equals(new File(login + ".xml").toURI().toString()))
				this.setParsedProfileXML(builder.parse(new File(login + ".xml")));

			final Element racine = parsedProfileXML.get().getDocumentElement();
			final NodeList racineNoeuds = racine.getChildNodes();

			final int nbRacineNoeuds = racineNoeuds.getLength();
			for (int i = 0; i < nbRacineNoeuds; i++) {
				if (racineNoeuds.item(i).getNodeName() == "psw") {
					// TODO fonction de cryptage du mot de passe
					return racineNoeuds.item(i).getTextContent().equals(psw);
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

	public Profile login(String login, String psw) {
		return null;
	}

	public void logout(Profile profile) {

	}
    
	public Profile getLocalProfile(String login, String password) {
		JAXBContext jaxbContext;
		Profile prof = null;
		String path = "MesProfiles\\"+login+"-"+password+".xml";
		
        try {
            JAXBContext context = JAXBContext.newInstance(Profile.class);
            Unmarshaller un = context.createUnmarshaller();
            prof = (Profile) un.unmarshal(new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        
		return prof;
	}

	public Profile getLocalProfile(UUID id) {
		// On r�cup�re tous les fichiers du dossier "MesProfiles"
		File repertoire = new File("MesProfiles");
		String[] L_fichiers =  repertoire.list();
		
		// On parcourt tous les fichiers se terminant bien par .xml .
		for (int i=0; i<L_fichiers.length; i++){
			if (L_fichiers[i].endsWith(".xml")){
				
				JAXBContext jaxbContext;
				Profile prof = null;
				String path = "MesProfiles\\"+L_fichiers[i];
				
		        try {
		            JAXBContext context = JAXBContext.newInstance(Profile.class);
		            Unmarshaller un = context.createUnmarshaller();
		            prof = (Profile) un.unmarshal(new File(path));
		            
		            // Si le profile contient bien le bon UUID on le retourne
		            if (id.compareTo(prof.getUUID()) == 0){
		            	return prof;
		            }
		        } catch (JAXBException e) {
		            e.printStackTrace();
		        }
				
			}
		}
		return null;
	}

	//Modifier le 1er profile pass� en param�tres avec les attributs du 2�me profile (sauf l'UUID)
	// En gros cela copie le contenu de new_profile � la place de celui de profile_to_modify (sauf l'UUID)
	public Profile modifyProfile(Profile profile_to_modify, Profile new_profile) {
		profile_to_modify.setAge(new_profile.getAge());
		profile_to_modify.setAvatar(new_profile.getAvatar());
		profile_to_modify.setClient(new_profile.getClient());
		profile_to_modify.setFirstName(new_profile.getFirstName());
		profile_to_modify.setLogin(new_profile.getLogin());
		profile_to_modify.setNbGameAbandonned(new_profile.getNbGameAbandonned());
		profile_to_modify.setNbGameLost(new_profile.getNbGameLost());
		profile_to_modify.setNbGameWon(new_profile.getNbGameWon());
		profile_to_modify.setNickName(new_profile.getNickName());
		profile_to_modify.setPsw(new_profile.getPsw());
		profile_to_modify.setSurName(new_profile.getSurName());
		
		return profile_to_modify;
	}

	public final BooleanProperty hasProfileOpenedProperty() {
		return this.hasProfileOpened;
	}

	public final boolean isHasProfileOpened() {
		return this.hasProfileOpenedProperty().get();
	}

	public final void setHasProfileOpened(final boolean hasProfileOpened) {
		this.hasProfileOpenedProperty().set(hasProfileOpened);
	}

	public final ObjectProperty<Profile> currentProfileProperty() {
		return this.currentProfile;
	}

	public final Profile getCurrentProfile() {
		return this.currentProfileProperty().get();
	}

	public final void setCurrentProfile(final Profile currentProfile) {
		this.currentProfileProperty().set(currentProfile);
	}

	public final ObjectProperty<Document> parsedProfileXMLProperty() {
		return this.parsedProfileXML;
	}

	public final Document getParsedProfileXML() {
		return this.parsedProfileXMLProperty().get();
	}

	public final void setParsedProfileXML(final Document parsedProfileXML) {
		this.parsedProfileXMLProperty().set(parsedProfileXML);
	}

}
