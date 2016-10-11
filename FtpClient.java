import java.io.* ;
import java.net.* ;
import java.util.* ;

/*
   CIS 457 - Project 1

   Created by:

      Jesse Roe
      Ben Commet
      Brandon Attala

*/

/*
   
   Client Class that handles logic related to sending and receving files and commands
   over TCP connctions

*/

public final class FtpClient {
    public static void main(String argv[]) throws Exception {   	

   	// Establish the listen socket.
   	// Socket socket = new Socket("localhost", port);
    System.out.println("FTP Client started:");

   	FtpRequestClient client = new FtpRequestClient();
    	client.run();
    }
}
