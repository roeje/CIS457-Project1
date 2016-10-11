import java.io.* ;
import java.net.* ;
import java.util.* ;

final class FtpRequestClient implements Runnable {
   final static String CRLF = "\r\n";
   int controlPort;
   String serverName;
   String dataServerName = "localhost";
   Socket controlSocket;
   Socket dataSocket;

   // Control Connection
   DataInputStream controlIn;
   DataOutputStream controlOut;

   // Data Connection
   DataInputStream dataIn;
   DataOutputStream dataOut;

   BufferedReader br;


   // Constructor for class
   FtpRequestClient() throws Exception {
      br = new BufferedReader(new InputStreamReader(System.in));
   }

   // Send Test Command
   void test() {
      System.out.println("Testing Control Connection:");
      try {
         this.controlOut.writeUTF("TEST");
      } catch (Exception e) {
         System.out.println(e);
      }
   }

   void createDataConnection() {
      try {
         this.dataSocket = new ServerSocket(this.controlPort + 1).accept();
         this.dataIn = new DataInputStream(this.dataSocket.getInputStream());
         this.dataOut = new DataOutputStream(this.dataSocket.getOutputStream());
      } catch (Exception e) {
         System.out.println(e);
      }
   }

   void connectToServer(int port, String serverName) {
      this.serverName = serverName;
      this.controlPort = port;
      try {
         this.controlSocket = new Socket(serverName, port);
         this.controlIn = new DataInputStream(this.controlSocket.getInputStream());
         this.controlOut = new DataOutputStream(this.controlSocket.getOutputStream());
         this.controlOut.writeUTF("TEST");
         System.out.println("Connection Established to " + serverName + " on port: " + port);
      } catch(Exception e) {
         System.out.println(e);
      }
   }

   void listDirContents() {
      System.out.println("Requesting dirctory contents:");
      try {
         this.controlOut.writeUTF("LIST");

         // Create data connection and wait for incomming connection from server
         createDataConnection();
         String file = this.dataIn.readUTF();
         System.out.println("Files are: ");
         while (file != null) {
            System.out.println(file);
            file = this.dataIn.readUTF();
         }
         this.dataSocket.close();

      } catch (Exception e) {
         System.out.println(e);
      }

      // ArrayList<String> fileList = new ArrayList<String>();
      // try {
      //   ObjectInputStream objectInput = new ObjectInputStream(dataSocket.getInputStream());
      //   try {
      //     Object object = objectInput.readObject();
      //     fileList = (ArrayList<String>) object;
      //     for(String fileName : fileList){
      //       System.out.println(fileName);
      //     }
      //  } catch(Exception e){ e.printStackTrace();}
      // } catch(Exception e) { e.printStackTrace();}

   }

   void retreveFile(String fileName) {

      System.out.println("Client sending get request for file: " + fileName);

      try {

         this.controlOut.writeUTF("RETR");
         this.controlOut.writeUTF(fileName);

         createDataConnection();
         System.out.println("After CONN");
         String serverMessage = this.dataIn.readUTF();

         if(serverMessage.compareTo("File Not Found") == 0){
            System.out.println("File not found on Server ...");
            return;
         }
         else {

            if(serverMessage.compareTo("READY") == 0){
               System.out.println("Retrieving file: " + fileName + "...");
               String newFileName = "NEW" + fileName;
               File file = new File(newFileName);
               FileOutputStream fout = new FileOutputStream(file);
               int ch;
               String temp;
               do {
                  temp = dataIn.readUTF();
                  ch = Integer.parseInt(temp);
                  if(ch != -1){
                     System.out.println(temp);
                     fout.write(ch);
                  }
               } while(ch!=-1);
                  fout.close();
                  System.out.println(dataIn.readUTF());
            }
            System.out.println("File Retrieved");
         }
         this.dataIn.close();
         this.dataIn = null;
         this.dataOut.close();
         this.dataIn = null;
         this.dataSocket.close();
         this.dataSocket = null;
      } catch (Exception e) {
         System.out.println("blah");
      }
   }


   void storeFile(String fileName) {
      // Define data connection, wait for confirm, send file


   }

   // Implement the run() method of the Runnable interface.
   public void run() {
      // Wait for input from user. On input trigger appropriate function.
      System.out.println("FTP Client Started...");
      System.out.println("Enter Command or Connect to FTP Server:");
      while(true) {
         try {
            String[] cmd = br.readLine().split("\\s+");
            if(cmd[0] == null) {
               System.out.println("Invalid Command");
            }

            else {
               switch(cmd[0].toUpperCase()) {
                  case "CONNECT":
                     System.out.println("Connect:");
                     System.out.println("Port:" + cmd[2]);
                     System.out.println("ServerName: " + cmd[1]);
                     connectToServer(Integer.parseInt(cmd[2]), cmd[1]);
                     break;
                  case "LIST":
                     listDirContents();
                     break;
                  case "RETR":
                     System.out.println("User entered Retreve file: " + cmd[1]);

                     retreveFile(cmd[1]);
                     break;
                  case "STOR":
                     break;
                  case "QUIT":
                     break;
               }
            }

     	   } catch (Exception e) {
     	         System.out.println(e);
     	   }
      }
   }
}
