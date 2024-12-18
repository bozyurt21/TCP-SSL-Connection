package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

public class SSLServerThread extends Thread{
	
	private SSLSocket sslSocket;
	private String fileName;
	private BufferedReader input;
	private PrintWriter output;
	private Map<String, List<Long>> fileInfo;
	
	public SSLServerThread(SSLSocket socket) {
		this.sslSocket = socket; // initialize the SSL socket
		this.fileInfo = new HashMap<>();
	}
	
	public void run() {
		try {
			this.input = new BufferedReader(new InputStreamReader(this.sslSocket.getInputStream()));
			this.output = new PrintWriter(this.sslSocket.getOutputStream());
			this.fileName = input.readLine();
			System.out.println("Reading the file " + fileName + " ...");
			if (this.fileName.equalsIgnoreCase("ALL")) {
				this.printAll();
				this.printFileInfo();
				output.println(" ");
				output.println("END");
			    output.flush();
			}
			else if (this.fileName != null && !this.fileName.isEmpty()) {
				this.sendFile(this.fileName);
				this.printFileInfo();
				output.println(" ");
				System.out.println("Read the " + fileName + ".");
				output.println("END");
			    output.flush();
			}
			else {
				output.println("Invalid request: Filename is empty or null.");
	            output.println("END");
			}
			output.flush();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				this.closeAll();
			}
			catch (IOException e) {
				System.out.println("Error Occured While Trying To Close Socket!");
			}
		}
			
			
	}

	public long readFile(String filename) throws FileNotFoundException {
		File file = new File("stories/" + filename);
		long fileSize = 0;
		if (!file.exists()) { // if there is no such file
			output.println("File Not Found!!");
			output.flush();
			output.println("END"); // Indicating that the file reading is over!
		}
		else {
			fileSize = file.length();
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				output.println(scanner.nextLine());
				output.flush();	
			}
	 // Indicating that the file reading is over!
			scanner.close();
		}
		return fileSize;
	}
	
	public void sendFile(String filename) throws FileNotFoundException {
	    long startTime = System.currentTimeMillis();
	    long size = this.readFile(filename)/1000;
	    long endTime = System.currentTimeMillis();
	    long time = endTime - startTime;
	    List<Long> info = new ArrayList<>();
	    info.add(size);
	    info.add(time);
	    this.fileInfo.put(filename, info);
	}
	
	public void printFileInfo() { // printing the file info
		for (String key: this.fileInfo.keySet()) {
			if (this.fileInfo.get(key).get(0) != 0) {
				this.output.println("Sent: " + key);
				this.output.println("File size: " + this.fileInfo.get(key).get(0) + "Kb (Kilobytes)");
				this.output.println("Time: " + this.fileInfo.get(key).get(1) + " ms");
				this.output.flush();
			}
		}
	}
	
	public void printAll() throws FileNotFoundException { // printing all file
		System.out.println("Printing all the files...");
		File directoryPath = new File("stories");
		File fileList[] = directoryPath.listFiles();
		for(File file: fileList) {
			this.sendFile(file.getName());
		}
	}
	
	public void closeAll() throws IOException{
		if(this.input != null) {
			this.input.close();
			System.out.println("Input Stream Closed!");
				
		}
		if (this.output != null) {
			this.output.close();
			System.out.println("Output Stream Closed!");
		}
		if (this.sslSocket != null) {
			this.sslSocket.close();
			System.out.println("Socket Closed!");
		}
		
	}

}
