package network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

import data.Profile;
import data.User;
import network.messages.IMessage;

/**
 * Handler for a socket connected to the game server
 */
public class SocketServerHandler implements Runnable {
	private Socket 				socket;
	private ObjectInputStream 	inputStream;
	private ObjectOutputStream 	outputStream;
	private ComClient 			client;
	
	/**
	 * Constructor for the SocketServerHandler class.
	 * @param socket	the socket to be handled
	 * @param client	the ComClient object the handler is connected to
	 */
	public SocketServerHandler(Socket socket, ComClient client){
		this.socket = socket;
		this.client = client;
		
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
            try {
            	
                IMessage o = (IMessage) inputStream.readObject();
                
                if(o != null)
                	o.process(client.getClientData());
                
            } catch(Exception e){
            	
            	//Déconnexion anormale de l'utilisateur -> fermeture du socket et du thread
            	
            	try {
					outputStream.close();
					inputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            	
            	client.stop();
            	
            	Thread.currentThread().interrupt();
            	
            	System.out.println("Client déconnecté anormalement !");
                e.printStackTrace();
                return;
            }
        }
	}
	
	/**
	 * Sends a message from the client to ther server using the open socket.
	 * @param message	the message to be sent
	 */
	public synchronized void sendMessage(IMessage message){
		try {
        	
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
