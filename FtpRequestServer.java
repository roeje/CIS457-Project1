import java.io.* ;
import java.net.* ;
import java.util.* ;

final class FtpRequestServer implements Runnable {
    final static String CRLF = "\r\n";
    int dataPort = 10004;
    Socket controlSocket;
    Socket dataSocket;

    // Control Connection
    DataInputStream controlIn;
    DataOutputStream controlOut;

    // Data Connection
    DataInputStream dataIn;
    DataOutputStream dataOut;

    // Constructor
    FtpRequestServer(Socket socket) throws Exception {
      try {
         this.controlSocket = socket;
         this.controlIn = new DataInputStream(controlSocket.getInputStream());
         this.controlOut = new DataOutputStream(controlSocket.getOutputStream());
      } catch (Exception e) {
         System.out.println(e);
      }
    }

    void createDataConnection() {
      try {
         this.dataSocket = new Socket("localhost", dataPort);
         this.dataIn = new DataInputStream(this.dataSocket.getInputStream());
         this.dataOut = new DataOutputStream(this.dataSocket.getOutputStream());

      } catch (Exception e) {
         System.out.println(e);
      }
    }

    void listDirContents() {
      System.out.println("List all files in current directory requet recieved:");
      try {
         createDataConnection();
         File[] files = new File(".").listFiles();

         for (File file : files) {
            this.dataOut.writeUTF(file.getName());
         }
         System.out.println("Files Sent!");
      } catch (Exception e) {
         System.out.println(e);
      }
   }
   void getFile(String fileName) {
      // Define data connection. Send file.

   }

   void sendFile(String fileName) {
      // Define data connection. Wait for data. Save file.

   }

   // Implement the run() method of the Runnable interface.
   public void run() {
      // Listen on command connection for Command from client. Trigger correct function based on client command.
      System.out.println("Server Thread Started:");
      while(true) {
         try {
            String cmd = controlIn.readUTF();
            System.out.println("New Command: " + cmd);
            switch(cmd.toUpperCase()) {
               case "LIST":
                  listDirContents();
                  break;
               case "RETR":
                  break;
               case "STOR":
                  break;
               case "QUIT":
                  break;
               case "TEST":
                  System.out.println("Test Recieved!!!");
                  break;
            }

     		} catch (Exception e) {
     		    System.out.println(e);
     		}
      }

   }
}
