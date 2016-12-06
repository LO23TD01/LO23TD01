//package data;
//
//import java.awt.Image;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.UUID;
//
//import javax.imageio.ImageIO;
//
//import data.client.ClientDataEngine;
//import data.client.InterfaceDataIHMLobby;
//import data.client.ProfileManager;
//
//public class test_xmlise {
//
//	public static void main(String[] args) {
//	// image
//	Image img = null;
//	try {
//		img = ImageIO.read(new File("C:\\Users\\Benoit\\Pictures\\avatar.png"));
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//
//	// client
//	Rights r = new Rights(true,true,false);
//	Client c = new Client(r);
//
//
//	Contact c1 = new Contact(null, "c1", "c1", "c1", 0);
//	Contact c2 = new Contact(null, "c2", "c2", "c2", 0);
//
//	c.addContact(c1);
//	c.addContact(c2);
//
//	ContactCategory cc1 = new ContactCategory("categorie1",r);
//
//	List<ContactCategory> categoryList = c.getCategoryList();
//	categoryList.add(cc1);
//	c.setCategoryList(categoryList);
//
//	// créaton du profile
//	UUID id = UUID.fromString("1a6aeeaa-acc7-40c7-8cf4-e221c2e72f19");
//	Profile p = new Profile(id,"login","nickname","psw","firstName","surName",12,img,0,0,0,c);
//	p.setClient(c);
//
//	//xmlise
//
//	new File("MesProfiles").mkdir();
//
//	p.Xmlise();
//
//
//	ProfileManager PM = new ProfileManager();
//	Profile p_get = PM.getLocalProfile("login", "psw");
//
//	Profile p_get2 = PM.getLocalProfile(id);
//
//	System.out.println(p_get.getFirstName());
//	System.out.println(p_get2.getFirstName());
//
//
//
//	ClientDataEngine cde = new ClientDataEngine();
//
//	cde.createProfile("loginIHM", "pswIHM");
//	Profile newP = PM.getLocalProfile("loginIHM", "pswIHM");
//	cde.getProfileManager().setCurrentProfile(PM.getLocalProfile("loginIHM", "pswIHM"));
//	newP.setLogin("newlog");
//
//
//	cde.changeMyProfile(newP); // Etant donné que l'utilisateur à changé de Login, on supprime l'ancien fichier pour en créer un new.
//
//}
//
//}
