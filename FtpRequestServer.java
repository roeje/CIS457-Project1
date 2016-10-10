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
        controlSocket = new ServerSocket(10003);
        dataSocket = new ServerSocket(10004);
    }

    private void List() {
      // Define data connection. Send dir contents.
      Socket skt = myServerSocket.accept();
      File currentDirectory = new File(".");
      File[] files = currentDirectory.listFiles();
      ArrayList<String> list = new ArrayList<String>();
      int cutPosition = 1 + System.getProperty("user.dir").length();
      for (File file : files) {
        try{
        list.add(file.getCanonicalPath().substring(cutPosition));
      }
      catch(Exception e) {}
      }
      try{
        ObjectOutputStream objectOutput = new ObjectOutputStream(skt.getOutputStream());
        objectOutput.writeObject(list);

      } catch(IOException ioe){
        ioe.printStackTrace();
      }
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
