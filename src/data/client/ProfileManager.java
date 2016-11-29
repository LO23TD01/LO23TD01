package data.client;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import data.Profile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ProfileManager {
	private final BooleanProperty hasProfileOpened = new SimpleBooleanProperty();
	private final ObjectProperty<Profile> currentProfile = new SimpleObjectProperty<Profile>();
	/**
	 * Variable qui contient le fichier XML parsé le plus récemment
	 */
	private ObjectProperty<Document> parsedProfileXML = new SimpleObjectProperty<Document>();

	public Profile createProfile(Object... args) {
		return null;
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

	public Profile modifyProfile(Profile profile, Object... args) {
		return null;
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
