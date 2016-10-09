import java.io.* ;
import java.net.* ;
import java.util.* ;

final class FtpRequestServer implements Runnable {
    final static String CRLF = "\r\n";
    ServerSocket controlSocket;
    ServerSocket dataSocket;

    // Control Connection
    DataInputStream controlIn;
    DataOutputStream controlOut;

    // Data Connection
    DataInputStream dataIn;
    DataOutputStream dataOut;

    BufferedReader br;
    //  Create data connection independently in each function


    // Constructor
    public FtpRequestServer(Socket socket) throws Exception {
		    this.socket = socket;
    }

    private void List() {
      // Define data connection. Send dir contents.

    }


    private void Get(String fileName) {
      // Define data connection. Send file.

    }

   private void Save(String fileName) {
      // Define data connection. Wait for data. Save file.

   }

   // Implement the run() method of the Runnable interface.
   public void run() {
      // Listen on command connection for Command from client. Trigger correct function based on client command.

      try {
         while() {



         }


  		} catch (Exception e) {
  		    System.out.println(e);
  		}
   }
}
