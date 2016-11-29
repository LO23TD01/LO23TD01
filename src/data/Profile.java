package data;

import java.awt.Image;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlRootElement
@XmlType(propOrder = { "uuid", "login", "nickName", "psw", "firstName", "surName", "age", "avatar", "nbGameWon", "nbGameLost", "nbGameAbandonned", "client" })
public class Profile implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8704008869575118912L;
	private final ObjectProperty<UUID> uuid;
	private final StringProperty login;
	private final StringProperty nickName;
	private final StringProperty psw;
	private final StringProperty firstName;
	private final StringProperty surName;
	private final IntegerProperty age;
	private final ObjectProperty<Image> avatar;
	private final IntegerProperty nbGameWon;
	private final IntegerProperty nbGameLost;
	private final IntegerProperty nbGameAbandonned;
	private final ObjectProperty<Client> client;

	/*
	 *
	 * Constructors
	 *
	 */

	public Profile() {
		this.uuid = new SimpleObjectProperty<UUID>(UUID.randomUUID());
		this.login = new SimpleStringProperty();
		this.nickName = new SimpleStringProperty();
		this.psw = new SimpleStringProperty();
		this.firstName = new SimpleStringProperty();
		this.surName = new SimpleStringProperty();
		this.age = new SimpleIntegerProperty();
		this.avatar = new SimpleObjectProperty<Image>();
		this.nbGameWon = new SimpleIntegerProperty();
		this.nbGameLost = new SimpleIntegerProperty();
		this.nbGameAbandonned = new SimpleIntegerProperty();
		this.client = new SimpleObjectProperty<Client>();
	}

	public Profile(UUID uuid) {
		this.uuid = new SimpleObjectProperty<UUID>(uuid);
		this.login = new SimpleStringProperty();
		this.nickName = new SimpleStringProperty();
		this.psw = new SimpleStringProperty();
		this.firstName = new SimpleStringProperty();
		this.surName = new SimpleStringProperty();
		this.age = new SimpleIntegerProperty();
		this.avatar = new SimpleObjectProperty<Image>();
		this.nbGameWon = new SimpleIntegerProperty();
		this.nbGameLost = new SimpleIntegerProperty();
		this.nbGameAbandonned = new SimpleIntegerProperty();
		this.client = new SimpleObjectProperty<Client>();
	}

	public Profile(UUID uuid, String nickname, String firstName, String surName, int age) {
		this.uuid = new SimpleObjectProperty<UUID>(uuid);
		this.login = new SimpleStringProperty();
		this.nickName = new SimpleStringProperty(nickname);
		this.psw = new SimpleStringProperty();
		this.firstName = new SimpleStringProperty(firstName);
		this.surName = new SimpleStringProperty(surName);
		this.age = new SimpleIntegerProperty(age);
		this.avatar = new SimpleObjectProperty<Image>();
		this.nbGameWon = new SimpleIntegerProperty();
		this.nbGameLost = new SimpleIntegerProperty();
		this.nbGameAbandonned = new SimpleIntegerProperty();
		this.client = new SimpleObjectProperty<Client>();
	}

	public Profile(UUID uuid, String login, String nickname, String psw, String firstName, String surName, int age, Image avatar, int nbGameWon, int nbGameLost,
			int nbGameAbandonned, Client client) {
		this.uuid = new SimpleObjectProperty<UUID>(uuid);
		this.login = new SimpleStringProperty(login);
		this.nickName = new SimpleStringProperty(nickname);
		this.psw = new SimpleStringProperty(psw);
		this.firstName = new SimpleStringProperty(firstName);
		this.surName = new SimpleStringProperty(surName);
		this.age = new SimpleIntegerProperty(age);
		this.avatar = new SimpleObjectProperty<Image>(avatar);
		this.nbGameWon = new SimpleIntegerProperty(nbGameWon);
		this.nbGameLost = new SimpleIntegerProperty(nbGameLost);
		this.nbGameAbandonned = new SimpleIntegerProperty(nbGameAbandonned);
		this.client = new SimpleObjectProperty<Client>(client);
	}

	/*
	 *
	 * Methods
	 *
	 *
	 */

	public void Xmlise() {
		try {

			// create JAXB context
			JAXBContext context = JAXBContext.newInstance(Profile.class);

			// Create Marshaller using JAXB context
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Do the marshal operation
			marshaller.marshal(this, new FileOutputStream(".\\monProfile.xml"));
			System.out.println("java object converted to xml successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * ATTANTION : ne fonctionne pas si le client du profile poss�de une liste de contact... (boucle)
		 *
		 * Le probl�me sera bient�t r�gl�.
		 *
		 */
	}

	/*
	 *
	 * Getters & Setters
	 *
	 *
	 */
	public final ObjectProperty<UUID> uuidProperty() {
		return this.uuid;
	}

	public final UUID getUUID() {
		return this.uuidProperty().get();
	}

	@XmlElement
	public final void setUUID(final UUID uuid) {
		this.uuidProperty().set(uuid);
	}

	public final StringProperty loginProperty() {
		return this.login;
	}

	public final String getLogin() {
		return this.loginProperty().get();
	}

	@XmlElement
	public final void setLogin(final String login) {
		this.loginProperty().set(login);
	}

	public final StringProperty nickNameProperty() {
		return this.nickName;
	}

	public final String getNickName() {
		return this.nickNameProperty().get();
	}

	@XmlElement
	public final void setNickName(final String nickName) {
		this.nickNameProperty().set(nickName);
	}

	public final StringProperty pswProperty() {
		return this.psw;
	}

	public final String getPsw() {
		return this.pswProperty().get();
	}

	@XmlElement
	public final void setPsw(final String psw) {
		this.pswProperty().set(psw);
	}

	public final StringProperty firstNameProperty() {
		return this.firstName;
	}

	public final String getFirstName() {
		return this.firstNameProperty().get();
	}

	@XmlElement
	public final void setFirstName(final String firstName) {
		this.firstNameProperty().set(firstName);
	}

	public final StringProperty surNameProperty() {
		return this.surName;
	}

	public final String getSurName() {
		return this.surNameProperty().get();
	}

	@XmlElement
	public final void setSurName(final String surName) {
		this.surNameProperty().set(surName);
	}

	public final IntegerProperty ageProperty() {
		return this.age;
	}

	public final int getAge() {
		return this.ageProperty().get();
	}

	@XmlElement
	public final void setAge(final int age) {
		this.ageProperty().set(age);
	}

	public final ObjectProperty<Image> avatarProperty() {
		return this.avatar;
	}

	public final Image getAvatar() {
		return this.avatarProperty().get();
	}

	@XmlElement
	public final void setAvatar(final Image avatar) {
		this.avatarProperty().set(avatar);
	}

	public final IntegerProperty nbGameWonProperty() {
		return this.nbGameWon;
	}

	public final int getNbGameWon() {
		return this.nbGameWonProperty().get();
	}

	@XmlElement
	public final void setNbGameWon(final int nbGameWon) {
		this.nbGameWonProperty().set(nbGameWon);
	}

	public final IntegerProperty nbGameLostProperty() {
		return this.nbGameLost;
	}

	public final int getNbGameLost() {
		return this.nbGameLostProperty().get();
	}

	@XmlElement
	public final void setNbGameLost(final int nbGameLost) {
		this.nbGameLostProperty().set(nbGameLost);
	}

	public final IntegerProperty nbGameAbandonnedProperty() {
		return this.nbGameAbandonned;
	}

	public final int getNbGameAbandonned() {
		return this.nbGameAbandonnedProperty().get();
	}

	@XmlElement
	public final void setNbGameAbandonned(final int nbGameAbandonned) {
		this.nbGameAbandonnedProperty().set(nbGameAbandonned);
	}

	public final ObjectProperty<Client> clientProperty() {
		return this.client;
	}

	public final Client getClient() {
		return this.clientProperty().get();
	}

	@XmlElement
	public final void setClient(final Client client) {
		this.clientProperty().set(client);
	}

}
