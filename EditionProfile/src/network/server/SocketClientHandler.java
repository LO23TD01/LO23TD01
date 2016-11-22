package network.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import network.messages.IMessage;

public class SocketClientHandler implements Runnable{
	
	private Socket 				clientSocket;
	private ObjectInputStream 	inputStream;
	private ObjectOutputStream 	outputStream;

	public SocketClientHandler(Socket clientSocket){
		this.clientSocket = clientSocket;
		
		try {
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (true) {
            try {
            	
                IMessage o = (IMessage) inputStream.readObject();
                
                if(o != null)
                	o.process();
                
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
	
	public void sendMessage(IMessage message){
		try {
        	
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
