import java.io.* ;
import java.net.* ;
import java.util.* ;

public final class FtpClient {
    public static void main(String argv[]) throws Exception {   	

   	// Establish the listen socket.
   	// Socket socket = new Socket("localhost", port);
      System.out.println("FTP Client started:");

   	FtpRequestClient client = new FtpRequestClient();
      client.run();
    }
}
