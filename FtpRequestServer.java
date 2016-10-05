import java.io.* ;
import java.net.* ;
import java.util.* ;

final class FtpRequestServer implements Runnable {
    final static String CRLF = "\r\n";
    Socket socket;

    // Constructor
    public FtpRequestServer(Socket socket) throws Exception {
		    this.socket = socket;
    }


    private void Read() {


    }

   private void Save() {


   }

   // Implement the run() method of the Runnable interface.
   public void run() {
      try {
  		    processRequest();
  		} catch (Exception e) {
  		    System.out.println(e);
  		}
   }
}
