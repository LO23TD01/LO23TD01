package sharedDataStructure;

//TODO: proper signatures
public abstract class Utilisateur {
	//return type covariance is awesome! (but fuck java for not having it implemented)
	public abstract ProfilePublic getProfile();
	
	public abstract void lancerDes();
	public abstract void relancerDes(boolean a, boolean b, boolean c);
	public abstract void creerTable();
	public abstract void lancerPartie();
	public abstract void demanderArretPartie();	
}
