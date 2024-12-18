package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPServerConnect {
	public final String DEFAULT_ADDRESS = "localhost"; 
	public final int DEFAULT_PORT = 88;
	
	private Socket client;
	protected BufferedReader input;
	protected PrintWriter output;
	private String serverType;
	
	public TCPServerConnect() {
		try {
			this.client = new Socket(this.DEFAULT_ADDRESS, this.DEFAULT_PORT);
			this.serverType = "TCP";
		}
		catch (IOException e) {
			System.out.println("While creating client, there had been some errors please see the below error message: ");
			e.printStackTrace();
		}
	}
	
	// connecting socket to the server
	public void connect() throws IOException {
		try {
			this.input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			this.output = new PrintWriter(this.client.getOutputStream());
			System.out.println("Sucessfully connected to the" + this.serverType + "server at: " + this.client.getRemoteSocketAddress());
		}
		catch (IOException e) {
			System.out.println("Client couldn't connected to server. Please see the below error message: ");
			e.printStackTrace();
		}
	}
	
	// Sending request to the server
	public void sendRequest(String request) { // In this project we are sending the filename
		this.output.println(request);
		this.output.flush();
	}
	
	// Recieve output from the server
	public void recieveResonse() throws IOException {
	    String line = this.input.readLine();
	    while (line != null && !line.equals("END")) { // Stop when "END" is received
	    	System.out.println(line);
	    	System.out.println();
	        line = this.input.readLine();
	    }
	}
	
	// disconnecting the client from the server.
	public void disconnect() {
		System.out.println("Client disconnected from " + this.serverType + " Server.");
		
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public BufferedReader getInput() {
		return input;
	}

	public void setInput(BufferedReader input) {
		this.input = input;
	}

	public PrintWriter getOutput() {
		return output;
	}

	public void setOutput(PrintWriter output) {
		this.output = output;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	
	
}
