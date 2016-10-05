import java.io.* ;
import java.net.* ;
import java.util.* ;

public final class FtpServer {
    public static void main(String argv[]) throws Exception {
   	// Get the port number from the command line.
   	int port = (new Integer(argv[0]));

   	// Establish the listen socket.
   	ServerSocket socket = new ServerSocket(port);
      System.out.println("FTP Server started on port: " + port.toString());

   	while (true) {
   	    // Listen for a TCP connection request.
   	    Socket connection = socket.accept();

   	    // Construct an object to process the HTTP request message.
   	    FTPRequest request = new FTPRequest(connection);

   	    // Create a new thread to process the request.
   	    Thread thread = new Thread(request);

   	    // Start the thread.
   	    thread.start();
   	}
    }
}
