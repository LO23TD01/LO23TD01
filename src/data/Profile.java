package data;

import java.awt.Image;
import java.util.UUID;

public class Profile {
	private UUID uuid;
	private String login;
	private String nickname;
	private String psw;
	private String firstName;
	private String surName;
	private int age;
	private Image avatar;
	private int nbGameWon;
	private int nbGameLost;
	private int nbGameAbandonned;
	private Client client;
	
	public Profile(UUID uuid, String login, String nickname, String psw, String firstName, String surName, int age,
			Image avatar, int nbGameWon, int nbGameLost, int nbGameAbandonned, Client client) {
		super();
		this.uuid = uuid;
		this.login = login;
		this.nickname = nickname;
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
	
	public void Xmlise(){
		/*
		 * TO-DO
		 */
	}
	
}
