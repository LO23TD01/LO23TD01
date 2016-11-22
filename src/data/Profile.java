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

@XmlRootElement
@XmlType(propOrder = {"uuid", "login", "nickName", "psw", "firstName", "surName", "age", "avatar", "nbGameWon", "nbGameLost", "nbGameAbandonned", "client"})
public class Profile implements Serializable {
	private UUID uuid;
	private String login;
	private String nickName;
	private String psw;
	private String firstName;
	private String surName;
	private int age;
	private Image avatar;
	private int nbGameWon;
	private int nbGameLost;
	private int nbGameAbandonned;
	private Client client;
	
	/*
	 * 
	 * Constructors
	 * 
	 */
	
	public Profile() {
		this.uuid = UUID.randomUUID();
	}
	
	public Profile(UUID uuid) {
		this.uuid = uuid;
	}
	
	public Profile(UUID uuid, String nickname, String firstName, String surName, int age) {
		this.uuid = uuid;
		this.nickName = nickname;
		this.firstName = firstName;
		this.surName = surName;
		this.age = age;
	}
	
	public Profile(UUID uuid, String login, String nickname, String psw, String firstName, String surName, int age,
			Image avatar, int nbGameWon, int nbGameLost, int nbGameAbandonned, Client client) {
		super();
		this.uuid = uuid;
		this.login = login;
		this.nickName = nickname;
		this.psw = psw;
		this.firstName = firstName;
		this.surName = surName;
		this.age = age;
		this.avatar = avatar;
		this.nbGameWon = nbGameWon;
		this.nbGameLost = nbGameLost;
		this.nbGameAbandonned = nbGameAbandonned;
		this.client = client;
	}
	
	/*
	 * 
	 * Methods
	 * 
	 * 
	 */
	
	public void Xmlise(){
		try {
			
		//create JAXB context
		JAXBContext context = JAXBContext.newInstance(Profile.class);
		
		//Create Marshaller using JAXB context
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 
		//Do the marshal operation
		marshaller.marshal(this, new FileOutputStream(".\\monProfile.xml"));
		System.out.println("java object converted to xml successfully.");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		
		
		/* ATTANTION : ne fonctionne pas si le client du profile possède une liste de contact... (boucle)
		 * 
		 * Le problème sera bientôt réglé.
		 *  
		 *  */
	}

	
	/*
	 * 
	 * Getters & Setters
	 * 
	 * 
	 */
	
	public UUID getUuid() {
		return uuid;
	}

	public String getLogin() {
		return login;
	}

	public String getNickName() {
		return nickName;
	}

	public String getPsw() {
		return psw;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurName() {
		return surName;
	}

	public int getAge() {
		return age;
	}

	public Image getAvatar() {
		return avatar;
	}

	public int getNbGameWon() {
		return nbGameWon;
	}

	public int getNbGameLost() {
		return nbGameLost;
	}

	public int getNbGameAbandonned() {
		return nbGameAbandonned;
	}

	public Client getClient() {
		return client;
	}

	@XmlElement
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@XmlElement
	public void setLogin(String login) {
		this.login = login;
	}

	@XmlElement
	public void setNickName(String nickname) {
		this.nickName = nickname;
	}

	@XmlElement
	public void setPsw(String psw) {
		this.psw = psw;
	}

	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement
	public void setSurName(String surName) {
		this.surName = surName;
	}

	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}

	@XmlElement
	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}

	@XmlElement
	public void setNbGameWon(int nbGameWon) {
		this.nbGameWon = nbGameWon;
	}

	@XmlElement
	public void setNbGameLost(int nbGameLost) {
		this.nbGameLost = nbGameLost;
	}

	@XmlElement
	public void setNbGameAbandonned(int nbGameAbandonned) {
		this.nbGameAbandonned = nbGameAbandonned;
	}

	@XmlElement
	public void setClient(Client client) {
		this.client = client;
	}
	
	
}
