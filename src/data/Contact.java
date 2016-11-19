package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {
	private Profile profile;
	
	
	public Contact(){
		
	}
	
	public Contact(Profile profile) {
		this.profile = profile;
	}

	/*
	 * 
	 * Getters & Setters
	 * 
	 * 
	 */
	
	public Profile getProfile() {
		return profile;
	}

	@XmlElement
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	
	
}
