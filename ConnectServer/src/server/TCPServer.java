package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	private ServerSocket server;
	private final int DEFAULT_PORT = 88;
	
	
	// Constructor
	public TCPServer() {
		try {
			this.server = new ServerSocket(this.DEFAULT_PORT);
			System.out.println("TCP Server Opened at " + this.DEFAULT_PORT);
		}
		catch(IOException e) {
			System.out.println("Error occured while creating server socket.");
			e.printStackTrace();
		}
		while(true) {
			this.clientAccept();
			
		}
		
	}
	// Accepting incoming client
	public void clientAccept(){
		try {
			Socket client = this.server.accept();
			TCPServerThread serverThread = new TCPServerThread(client);
			System.out.println("TCP Connection is created with the client at the address of " + client.getInetAddress());
			
			serverThread.start();
		}
		catch(IOException e) {
			System.out.println("Error occured while connecting client to the server");
			e.printStackTrace();
		}
	}
	
	

}
