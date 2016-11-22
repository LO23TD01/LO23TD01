package data;

import java.awt.Image;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"uuid", "login", "nickName", "firstName", "surName", "age", "avatar", "nbGameWon", "nbGameLost", "nbGameAbandonned"})
public class Contact {
	private UUID uuid;
	private String login;
	private String nickName;
	private String firstName;
	private String surName;
	private int age;
	private Image avatar;
	private int nbGameWon;
	private int nbGameLost;
	private int nbGameAbandonned;
	
	
	public Contact(){
		
	}
	
	public Contact(UUID uuid, String nickname, String firstName, String surName, int age) {
		this.uuid = uuid;
		this.nickName = nickname;
		this.firstName = firstName;
		this.surName = surName;
		this.age = age;
	}
	
	public Contact(UUID uuid, String login, String nickname, String firstName, String surName, int age,
			Image avatar, int nbGameWon, int nbGameLost, int nbGameAbandonned) {
		super();
		this.uuid = uuid;
		this.login = login;
		this.nickName = nickname;
		this.firstName = firstName;
		this.surName = surName;
		this.age = age;
		this.avatar = avatar;
		this.nbGameWon = nbGameWon;
		this.nbGameLost = nbGameLost;
		this.nbGameAbandonned = nbGameAbandonned;
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

	@XmlElement
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getLogin() {
		return login;
	}

	@XmlElement
	public void setLogin(String login) {
		this.login = login;
	}

	public String getNickName() {
		return nickName;
	}

	@XmlElement
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFirstName() {
		return firstName;
	}

	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	@XmlElement
	public void setSurName(String surName) {
		this.surName = surName;
	}

	public int getAge() {
		return age;
	}

	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}

	public Image getAvatar() {
		return avatar;
	}

	@XmlElement
	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}

	public int getNbGameWon() {
		return nbGameWon;
	}

	@XmlElement
	public void setNbGameWon(int nbGameWon) {
		this.nbGameWon = nbGameWon;
	}

	public int getNbGameLost() {
		return nbGameLost;
	}

	@XmlElement
	public void setNbGameLost(int nbGameLost) {
		this.nbGameLost = nbGameLost;
	}

	public int getNbGameAbandonned() {
		return nbGameAbandonned;
	}

	@XmlElement
	public void setNbGameAbandonned(int nbGameAbandonned) {
		this.nbGameAbandonned = nbGameAbandonned;
	}

}
