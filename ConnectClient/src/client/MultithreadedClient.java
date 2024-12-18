package client;

import java.io.IOException;
import java.util.Scanner;

public class MultithreadedClient {
	

	public static void main(String[] args) throws IOException{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Do you want TCP or SSL connection?");
			String choice = scanner.nextLine();
			String filename;
			try {
				if (choice.equalsIgnoreCase("TCP")) {
					TCPServerConnect TCPclient = new TCPServerConnect();
					TCPclient.connect();
					System.out.println("Please enter the name of the file: ('ALL' for all files)");
					filename = scanner.nextLine();
					TCPclient.sendRequest(filename);
					TCPclient.recieveResonse();
					TCPclient.disconnect();
				}
				else if (choice.equalsIgnoreCase("SSL")){
					SSLServerConnect SSLclient = new SSLServerConnect();
					SSLclient.connect();
					System.out.println("Please enter the name of the file: ('ALL' for all files)");
					filename = scanner.nextLine();
					SSLclient.sendRequest(filename);
					SSLclient.recieveResonse();
					SSLclient.disconnect();
						
				}
				else {
					System.out.println("No such Protocol!");
				}
			}
				catch(IOException e) {
					System.out.println("Error occured while trying to connect the client.");
					e.printStackTrace();
				}
				
			}
			
}
			

