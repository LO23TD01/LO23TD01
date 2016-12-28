package main;

import data.server.InterfaceSingleThreadData;
import data.server.ServerDataEngine;
import network.server.ComServer;

public class MainServer {

	public static void main (String[] args){
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
