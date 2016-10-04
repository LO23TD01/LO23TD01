package sharedDataStructure;

import java.util.List;

import localData.Createur;
import localData.Enregistrement;
import localData.Jeu;

public class TableJeu {
	
	public String nom;
	public Createur createur;
	public Parametres parametresTable;
	public List<Utilisateur> listeJoueurs;
	public List<Utilisateur> listeSpectateurs;
	public Jeu etatDuJeu;
	public Chat chatLocal;
	public Enregistrement historique;
	
}
