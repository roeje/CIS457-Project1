import java.io.* ;
import java.net.* ;
import java.util.* ;

final class FtpRequestClient implements Runnable {
    final static String CRLF = "\r\n";
    Socket controlSocket;
    Socket dataSocket;

    // Control Connection
    DataInputStream controlIn;
    DataOutputStream controlOut;

    // Data Connection
    DataInputStream dataIn;
    DataOutputStream dataOut;
    
    BufferedReader br;
    //  Create dataConnection DataStreams(TCP connections) independently within each control function

    // Constructor
    public FtpRequestClient(Socket socket) throws Exception {
		    this.socket = socket;
    }

    private void List() {
      // Define data connection. Wait for list. Display to user
      File currentDirectory = new File(".");
      File[] files = currentDirectory.listFiles();
      ArrayList<String> list = new ArrayList<String>();
      int cutPosition = 1 + System.getProperty("user.dir").length();
      for (File file : files){
        try{
        list.add(file.getCanonicalPath().substring(cutPosition));
      }
      catch(Exception e) {}
      }
    }

    private void Get(String fileName) {
      // Define data connection. Read incomming data. Save file.

    }

   private void Send(String fileName) {
      // Define data connection, wait for confirm, send file


   }

   // Implement the run() method of the Runnable interface.
   public void run() {
      // Wait for input from user. On input trigger appropriate function.

      try {
  		    processRequest();
  		} catch (Exception e) {
  		    System.out.println(e);
  		}
   }
}
