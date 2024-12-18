package server;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SSLServer{
	
	// TODO: Look if it is necessary at the end
		// Constant keystore Password and 
		private final String SERVER_KEYSTORE_FILE = "ssl-keystore.jks";
	    private final String SERVER_KEYSTORE_PASSWORD = "passpass";  
	    private final String SERVER_KEY_PASSWORD = "passpass";
	    private SSLServerSocket sslServerSocket;
	    private SSLServerSocketFactory sslServerSocketFactory;
	    private final int DEFAULT_PORT = 89;
	    
	    public SSLServer() throws NoSuchAlgorithmException, KeyStoreException, CertificateException, 
	    FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException 
	    
	    {
	    	SSLContext secure = SSLContext.getInstance("TLS");
	    	char keyManagement[] = SERVER_KEYSTORE_PASSWORD.toCharArray();
	    	KeyStore key = KeyStore.getInstance("PKCS12");
	    	key.load(new FileInputStream(this.SERVER_KEYSTORE_FILE), keyManagement);
	    	KeyManagerFactory keyManagerFact = KeyManagerFactory.getInstance("SunX509");
	    	keyManagerFact.init(key, this.SERVER_KEY_PASSWORD.toCharArray());
	    	secure.init(keyManagerFact.getKeyManagers(), null, null);
	    	
	    	this.sslServerSocketFactory = secure.getServerSocketFactory();
	    	this.sslServerSocket = (SSLServerSocket) this.sslServerSocketFactory.createServerSocket(this.DEFAULT_PORT); // creates server socket at indicated port!
	    	System.out.println("SSL server is up and running on port " + this.DEFAULT_PORT);
	    	this.listen();
	    	
	    }
	    
	    public void listen() {
	    	while (true)
	        {
	            listenAndAccept();
	        }
	    }
	    
	    private void listenAndAccept(){
	    	SSLSocket socket;
	    	try {
	    		socket = (SSLSocket) this.sslServerSocket.accept();
	    		System.out.println("SSL Connection is created with the client at the address of" + socket.getRemoteSocketAddress());
	    		SSLServerThread serverThread = new SSLServerThread(socket);
	    		serverThread.start();
	    		
	    	}
	    	catch(IOException e) {
	    		System.out.println("Error occured while trying to access to the SSL server.");
	    		e.printStackTrace();
	    	}
	    }

}
