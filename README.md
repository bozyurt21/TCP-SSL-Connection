# TCP/SSL Connection to The Server

This is a Java project which takes SSL or TCP as an input from the user (client) and connect the client to their desired server.

In this project there were several challenges naming:
+ Connecting multiple clients to the server (Used Mulithread functionality of Java)
+ Serving more than one server in different ports (Again I used multithreaded functionality of Java since opening one server to get connected block other server to be connected.)
+ After user connect with the respective server, based on their inputs reading text the file and sending the whole text. (It was hard because the files were big and also in SSL connection I need to check whether user has access to get the file or not. I used trustkey to authenticate user. I also used keystore and certifier in server side too. To manage to get keystore, truststore file and certifier I used [this link](https://docs.oracle.com/cd/E19509-01/820-3503/ggsxx/index.html).) I used below code to manage sending the whole file:

```java
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
```
As it can be seen from above, I am reading the file based on the provided filename from the user. If it does not exist then I am informing user with **File Not Found** error. If there is, I am reading till there is nothing left and **flushing** the line immediately to the client since if I do not, there will be high queuing delay that could cause my client to not been able to recieve the file.
