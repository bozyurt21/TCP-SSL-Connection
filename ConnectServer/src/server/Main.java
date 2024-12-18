package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class Main extends Thread{
	
	public static void main(String[] args) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		// Since when I start TCP server connection I cannot 
		Thread tcpServerThread = new Thread(() -> {
            try {
                new TCPServer(); 
            } catch (Exception e) {
                System.err.println("Error starting TCP Server:");
                e.printStackTrace();
            }
        });

        
        Thread sslServerThread = new Thread(() -> {
            try {
                new SSLServer(); 
            } catch (Exception e) {
                System.err.println("Error starting SSL Server:");
                e.printStackTrace();
            }
        });

        // Start both threads
        tcpServerThread.start();
        sslServerThread.start();
        

        System.out.println("Both servers are running...");
	}

}
