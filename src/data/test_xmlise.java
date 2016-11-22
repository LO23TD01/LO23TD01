package data;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

public class test_xmlise {

	public static void main(String[] args) {
		// image
		Image img = null;
		try {
			img = ImageIO.read(new File("C:\\Users\\Benoit\\Pictures\\avatar.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// client
		Rights r = new Rights(true,true,false);
		Client c = new Client(r);
		

		Contact c1 = new Contact(null, "c1", "c1", "c1", 0);
		Contact c2 = new Contact(null, "c2", "c2", "c2", 0);
		
		c.addContact(c1);
		c.addContact(c2);
		
		ContactCategory cc1 = new ContactCategory("categorie1",r);
		
		List<ContactCategory> categoryList = c.getCategoryList();
		categoryList.add(cc1);
		c.setCategoryList(categoryList);

		// créaton du profile
		Profile p = new Profile(UUID.randomUUID(),"login","nickname","psw","firstName","surName",12,img,0,0,0,c);
		p.setClient(c);

		//xmlise
		p.Xmlise();
	}

}
