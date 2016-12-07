package network.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import network.messages.ConnectionMessage;
import network.messages.IMessage;

/**
 * Handles the client's connection to the server
 */
public class SocketClientHandler implements Runnable{

	private Socket 				clientSocket;
	private ObjectInputStream 	inputStream;
	private ObjectOutputStream 	outputStream;
	private ComServer server;

	/*
	 *
	 * Constructor
	 *
	 */

	/**
	 * SocketClientHandler's constructor's
	 * @param clientSocket The socket associated with the connection
	 * @param server Server to connect
	 */
	public SocketClientHandler(Socket clientSocket, ComServer server){
		this.clientSocket = clientSocket;
		this.server = server;

		try {
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 *
	 * Overridden methods
	 *
	 */

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
            try {

                IMessage o = (IMessage) inputStream.readObject();

                if(o != null){
                	if(o.getClass() == ConnectionMessage.class){
                		ConnectionMessage message = (ConnectionMessage) o;
                		server.replaceWithUUID(clientSocket.getInetAddress().toString(), message.uuid);
                	}


                	o.process(server.getDataEngine());
                }


            } catch (EOFException e) {
                try {
					outputStream.close();
					inputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

                Thread.currentThread().interrupt();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
	}

	/**
	 * Sends a message to the server
	 * @param message Message to send
	 */
	public void sendMessage(IMessage message){
		try {

            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
