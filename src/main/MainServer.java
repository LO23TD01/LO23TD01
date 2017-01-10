package main;

import javax.swing.JFrame;
import javax.swing.JLabel;

import data.server.InterfaceSingleThreadData;
import data.server.ServerDataEngine;
import network.server.ComServer;

public class MainServer {

	public static void main (String[] args){
		
		JFrame window = new JFrame();
		window.setTitle("Serveur 421");
	    //Définit sa taille : 400 pixels de large et 100 pixels de haut
		window.setSize(400, 100);
	    //Nous demandons maintenant à notre objet de se positionner au centre
		window.setLocationRelativeTo(null);
	    //Termine le processus lorsqu'on clique sur la croix rouge
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //Et enfin, la rendre visible        
		window.setVisible(true);
		
		JLabel label1 = new JLabel("Fermez cette fenêtre pour éteindre le serveur.");
		
		window.add(label1);
		
		//Initialisation de ServerDataEngine et du Thread de communication
		ServerDataEngine serverEngine = new ServerDataEngine();
		ComServer serverCom = new ComServer(4000);

		//Liaison des 2 modules
		serverCom.setDataEngine(new InterfaceSingleThreadData(serverEngine));
		serverEngine.setComServer(serverCom);

		//Lancement du Thread principal
		Thread serverThread = new Thread(serverCom);
		serverThread.setDaemon(false);
		serverThread.start();
	}
}
