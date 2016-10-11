import java.io.* ;
import java.net.* ;
import java.util.* ;

final class FtpRequestServer implements Runnable {
    final static String CRLF = "\r\n";
    String clientName;
    int dataPort;
    Socket controlSocket;   

    // Control Connection
    DataInputStream controlIn;
    DataOutputStream controlOut;

    // Constructor
    FtpRequestServer(Socket socket) throws Exception {
      try {
         controlSocket = socket;
         controlIn = new DataInputStream(controlSocket.getInputStream());
         controlOut = new DataOutputStream(controlSocket.getOutputStream());
      } catch (Exception e) {
         System.out.println(e);
      }
    }

    // Implement logic to list content of current working directory
    void listDirContents() {

      System.out.println("Sending list of all directory files...");

      try {        

        // Establish connection to client data TCP socket
        Socket dataSocket = new Socket(clientName, dataPort);
        DataOutputStream dout = new DataOutputStream(dataSocket.getOutputStream());
        
        // Get array of all files in current directory
        File[] files = new File(".").listFiles();     
      
        for (File file : files) {
          dout.writeUTF(file.getName());
        }
        dout.writeUTF("END");
        
        dout.close();
        dout.flush();
        controlOut.flush();
        dataSocket.close(); 

        System.out.println("File List Sent To Client.");     
      
      } catch (Exception e) {
         System.out.println(e);
      }
   }

   // Implement logic to send file to client
   void retreveFile(String fileName) {
      System.out.println("Retreving File: " + fileName);

      try{

        // Connect to client data TCP socket
         Socket dataSocket = new Socket(clientName, dataPort);
         DataOutputStream dout = new DataOutputStream(dataSocket.getOutputStream());
         
         File file = new File(fileName);
         byte[] bytes = new byte[(int)file.length()];

         FileInputStream fin = new FileInputStream(file);

         BufferedInputStream buffin = new BufferedInputStream(fin);

         DataInputStream datain = new DataInputStream(buffin);
         datain.readFully(bytes, 0, bytes.length);

         dout.writeLong(bytes.length);
         dout.write(bytes, 0, bytes.length);

         dataSocket.close();
         dout.flush();         
         
         System.out.println("File Sent To Client.");

      } catch (Exception e) {
        System.out.println(e);
      }

   }

   // Implement logic to save file recieved from client
   void saveFile(String fileName) {
      System.out.println("File: " + fileName + " received from Client.");
      try {
      
         Socket dataSocket = new Socket(clientName, dataPort);
         // InputStream in = dataSocket.getInputStream();
         // DataInputStream din = new DataInputStream(in);
         DataInputStream din = new DataInputStream(dataSocket.getInputStream());
      
         int bytes;
      
         OutputStream out = new FileOutputStream(("copy_" + fileName));
         long sizeOfData = din.readLong();
         byte[] buffer = new byte[1024];
         while (sizeOfData > 0 && (bytes = din.read(buffer, 0, (int) Math.min(buffer.length, sizeOfData))) != -1) {
            out.write(buffer, 0, bytes);
            sizeOfData -= bytes;
         }
      
         out.close();      
         dataSocket.close();    
      
         
         System.out.println("File Saved Successfully...");
      
      } catch (Exception e) {
           System.out.println(e);
      }

   }

   // Implement the run() method of the Runnable interface.
   public void run() {
      // Listen on command connection for Command from client. Trigger correct function based on client command.
      System.out.println("Server Thread Started:");
      while(true) {
         try {
          
            String cmd = controlIn.readUTF(); 
            System.out.println("Server recieved command: " + cmd);

            switch(cmd.toUpperCase()) {
               case "LIST":
                  listDirContents();
                  break;
               case "RETR":
                  String fileName = controlIn.readUTF();
                  retreveFile(fileName);
                  break;
               case "STOR":
                  String fileName2 = controlIn.readUTF();
                  saveFile(fileName2);
                  break;
               case "QUIT":
                  System.out.println("Client Disconnecting...");
                  controlSocket.close();                  
                  return;
               case "DATA":
                  clientName = controlIn.readUTF();
                  dataPort = Integer.parseInt(controlIn.readUTF());                  
                  break;
               case "TEST":
                  break;
            }

     		} catch (Exception e) {
     		    System.out.println(e);
     		}
      }
   }
}
