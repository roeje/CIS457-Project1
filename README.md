#CIS 457 Project 1

## Simple Java FTP client/server implementation

###Design:
The design of our FTP implementation utilizes both a server and a client java class. When run, the two class create separate instances that interact with one another to transfer files. The FtpServer.java class creates an instance (thread) of FtpRequestServer.java for every client that connects to the server. Each FtpRequestServer instance contains all logic necessary to interact with the server and send and receive files and user commands.
The FtpClient.java class starts a single instance of FtpRequestClient.java. The FtpRequestClient instance contains all logic necessary to connect to a specified server, and send/receive user commands or files to and from the server.

###Commands: 
* CONNECT `<SP> <hostname> <SP> <portnumber>` - The connect command takes two parameters. The first parameter is the address of the machine we are connecting to. The second is the port on the machine that we are establishing the connection on. When a Connect command is given to the client, it will create a new TCP connection to the server's control socket. If the connection is successful, both the server and client both output a message stating the success of the command.
	
* LIST - The List command takes no parameters. Upon receiving the list command, the client will create a new TCP connection and send a list command to the server. Once the server receives the list command, it connects to the TCP data connection hosted by the client and retrieves a list of all files from its current working directory and send it to the client. The client will then output these files names to the user terminal and terminate the TCP data connection. The filename does not contain the entire file path, only the name of the file itself.
	
* RETR `<SP> <filename>` - The Retrieve command takes one parameter, the name of the file the client wishes to retrieve from the server. Upon receiving the command, the client will create a new TCP connection and send a control command to the server instructing it to connect to this new data connection and send the specified file. Once the server receives the client's request command, it grabs the file from its working directory (if it exists), connects to the client’s TCP connection and uses this connection to send the file to the client. If the file does not exist an error message will be displayed. The client will receive the file from the server and write it to its current working directory and then terminate the TCP data connection.

* STOR `<SP> <filename>` - The Store command takes one parameter, the name of the file to be sent to the server. Upon receiving the command, the client creates a new TCP connection and sends a control command to the server instructing it to connect to its new data connection. Once the server connects to the client data connection, the client writes the specified file to the server. The server reads in the file data and stores the file in its current work directory. 

* QUIT - The Quit command is passed to the client where it terminates its control TCP connection and sends a Quit command to the server. The server then terminates the thread associated with this particular client.

###Features:
* The STOR and RETR command utilize BufferedInputStreams to simply the file transfer process, and improve the speed of each file transfer. 
* When a client first connects to a server thread, the client passes its ip and current port to the server application. This allows the server to connect to data TCP connections on the client application regardless of whether the server is running locally or on a remote machine.
* A test command was implemented to verify that the initial control connection between the server and the client was established correctly.
