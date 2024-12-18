package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLServerConnect {
	private final String TRUST_STORE_NAME = "ssl-trustkey.jks";
	private final String TRUST_STORE_PASSWORD = "passpass";

    private SSLSocketFactory sslSocketFactory;
    private SSLSocket sslSocket;
    private BufferedReader input;
    private PrintWriter output;

    protected String serverAddress = "localhost";
    protected final int serverPort = 89;
    
    public SSLServerConnect() {
        /*
         * Loads the truststore's address of client
         */
        System.setProperty("javax.net.ssl.trustStore", TRUST_STORE_NAME);

        /*
         * Loads the truststore's password of client
         */
        System.setProperty("javax.net.ssl.trustStorePassword", TRUST_STORE_PASSWORD);
    }
    
    public void connect() {
        try {
            sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            sslSocket = (SSLSocket) sslSocketFactory.createSocket(serverAddress, serverPort);
            /*
             * Client starts the handshake before sending any messages
             * If no exception happens, it means the handshake is successful
             */
            sslSocket.startHandshake();
            /*
             * After the handshake, we can send and receive messages normally, like what we
             * did in TCP
             */
            this.input = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            this.output = new PrintWriter(sslSocket.getOutputStream());
            System.out.println("Successfully connected to " + serverAddress + " on port " + serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void disconnect() {
        try {
            this.input.close();
            this.output.close();
            sslSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendRequest(String request) { // In this project we are sending the filename
		this.output.println(request);
		this.output.flush();
	}
    
    public void recieveResonse() throws IOException {
	    String line = this.input.readLine();
	    while (line != null && !line.equals("END")) { // Stop when "END" is received
	    	System.out.println(line);
	    	System.out.println();
	        line = this.input.readLine();
	    }
	}
    
    
    
    

}
