package data;

public class test_xmlise {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Profile p = new Profile(null, "test", "test", "test", 0);
		
		Rights r = new Rights(true,true,false);
		Client c = new Client(r);
		p.setClient(c);
		
		
		p.Xmlise();
	}

}
