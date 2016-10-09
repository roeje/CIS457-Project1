import java.io.* ;
import java.net.* ;
import java.util.* ;

public final class FtpClient {
    public static void main(String argv[]) throws Exception {
   	// Get the port number from the command line.
   	int port = (new Integer(argv[0]));

   	// Establish the listen socket.
   	Socket socket = new Socket(port);
      System.out.println("FTP Client started on port: " + port.toString());

   	FtpRequestClient client = new FtpRequestClient(socket);
    }
}
