package network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import network.messages.IMessage;

public class SocketServerHandler implements Runnable {
	private Socket 				socket;
	private ObjectInputStream 	inputStream;
	private ObjectOutputStream 	outputStream;
	
	public SocketServerHandler(Socket socket){
		this.socket = socket;
		
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(socket.getInputStream());
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
                
            } catch(SocketException e){
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
