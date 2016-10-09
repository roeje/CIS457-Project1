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
          br = new BufferedReader(new InputStreamReader(System.in));
    }

    private void List() {
      // Define data connection. Wait for list. Display to user
      dataIn = new DataInputStream()
      for (){
        try{

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
  		    while(true) {
             String command;
             System.out.println("Enter a command:");
             command = br.readLine();
             String[] commandList = command.split("\\s+");

             if (commandList[0] == "CONNECT") {

             }



          }
  		} catch (Exception e) {
  		    System.out.println(e);
  		}
   }
}
