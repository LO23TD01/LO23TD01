package main;

import data.server.ServerDataEngine;
import network.server.ComServer;

public class MainServer {
	
	public static void main (String[] args){
		//Initialisation de ServerDataEngine et du Thread de communication
		ServerDataEngine serverEngine = new ServerDataEngine();
		ComServer server = new ComServer(4000);
		
		//Liaison des 2 modules
		server.setDataEngine(serverEngine);
		serverEngine.setComServer(server);
		
		//Lancement du Thread principal
		Thread serverThread = new Thread(server);
		serverThread.setDaemon(false);
		serverThread.start();
	}
}
