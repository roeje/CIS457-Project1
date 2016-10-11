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

final class FtpRequestClient implements Runnable {   
   int controlPort;
   int dataPort;
   String serverName;   
   Socket controlSocket;   

   // Control Connection
   DataInputStream controlIn;
   DataOutputStream controlOut;

   // BufferedReader for user input
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

   // Implment logic for establishing inital control connection
   void connectToServer(int port, String serverName) {

      // Set variabls for connection information
      this.serverName = serverName;
      this.controlPort = port;
      this.dataPort = controlPort + 1;

      try {

         // Connect to server
         controlSocket = new Socket(serverName, port);

         // Create input/output streams for to send commands
         controlIn = new DataInputStream(this.controlSocket.getInputStream());
         controlOut = new DataOutputStream(this.controlSocket.getOutputStream());

         // Send client information: port number and ip to server to allow server to 
         // connect to data TCP connections remotly
         controlOut.writeUTF("DATA");
         InetAddress IP = InetAddress.getLocalHost();
         String clientAddress = IP.getHostAddress();
         controlOut.writeUTF(clientAddress);
         controlOut.writeUTF(Integer.toString(this.controlPort + 1));         

         // Run test to ensure connection was establishd correctly
         this.controlOut.writeUTF("TEST");
         System.out.println("Connection Established to " + serverName + " on port: " + port);
      } catch(Exception e) {
         System.out.println(e);
      }
   }

   // Implement logic to List server directory contents
   void listDirContents() {

      System.out.println("Requesting Dirctory Contents...");
      try {

         // Send connand to server
         this.controlOut.writeUTF("LIST");

         // Create data TCP connection and wait for server response
         ServerSocket server = new ServerSocket(dataPort);  
         Socket dataSocket = server.accept();
         DataInputStream din = new DataInputStream(dataSocket.getInputStream());

         // InputStream in = dataSocket.getInputStream();
         // DataInputStream din = new DataInputStream(in);
      
         // Read in each file and and print in terminal
         String file = din.readUTF();

         System.out.println("Files Receved Are: ");
         while (!(file.equals("END"))) {
            System.out.println(file);
            file = din.readUTF();
         }

         // Close data TCP connection with server
         server.close();
         dataSocket.close();
         controlOut.flush();         
      
      } catch (Exception e) {
         System.out.println(e);
      }
   }

   // Implement logic to request server file
   void requestFile(String fileName) {      

      try {

         // Send command to server
         controlOut.writeUTF("RETR");
         controlOut.writeUTF(fileName);

         // Create data TCP connection
         ServerSocket server = new ServerSocket(dataPort);
         Socket dataSocket = server.accept();
         DataInputStream din = new DataInputStream(dataSocket.getInputStream());

         System.out.println("File " + fileName + " Received from Server.");

         int bytes;

         // Create output stream for file input
         OutputStream out = new FileOutputStream(("copy_" + fileName));
         long sizeOfData = din.readLong();

         // Create byte array to hold input data
         byte[] buffer = new byte[1024];
         while (sizeOfData > 0 && (bytes = din.read(buffer, 0, (int) Math.min(buffer.length, sizeOfData))) != -1) {

            // Write input data to file
            out.write(buffer, 0, bytes);
            sizeOfData -= bytes;
         }

         // Close file stream
         out.close();         
         
         // Close data TCP connection
         server.close();
         dataSocket.close();
         
         System.out.println("File Saved To Client Directory Sucessfully...");

      } catch (Exception e) {
           System.out.println(e);
      }

   }

   // Implement logic to send file to server
   void sendFile(String fileName) {
      System.out.println("Sending file: " + fileName + " to Server...");
      try{

         // Send command to server
         controlOut.writeUTF("STOR");
         controlOut.writeUTF(fileName);
      
         // Create data TCP connection
         ServerSocket server = new ServerSocket(dataPort);
         Socket dataSocket = server.accept();         

         // Create output stream to send file
         DataOutputStream dout = new DataOutputStream(dataSocket.getOutputStream());
      
         // Get file by name
         File file = new File(fileName);

         // Create byte array to hold file data
         byte[] bytes = new byte[(int)file.length()];
      
         // Create file input stream
         FileInputStream fin = new FileInputStream(file);
      
         // Create bufferd stream for file stream
         BufferedInputStream buffin = new BufferedInputStream(fin);
      
         // Create datainput stream for Bufferd file stream
         DataInputStream datain = new DataInputStream(buffin);

         // Read file into byte array
         datain.readFully(bytes, 0, bytes.length);
      
         // Write byte array to data TCP connection
         dout.writeLong(bytes.length);
         dout.write(bytes, 0, bytes.length);
         dout.flush();

         // Close data TCP connection
         server.close();
         dataSocket.close();
         System.out.println("File Sent To Server...");
      
      } catch (Exception e) {
        System.out.println(e);
      }

   }   

   // Implement the run() method of the Runnable interface.
   public void run() {

      // Wait for input from user. On input trigger appropriate function.
      System.out.println("FTP Client Started...");
      System.out.println("Enter Command or Connect to FTP Server:");
      boolean running = true;
      while(running) {
         try {

            // Create array from user command string
            String[] cmd = br.readLine().split("\\s+");
            if(cmd[0] == null) {
               System.out.println("Invalid Command");
            }

            else {

               // Logic to handle various command strings from user
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
                     requestFile(cmd[1]);
                     break;
                  case "STOR":
                     System.out.println("User entered Save file: " + cmd[1]);
                     sendFile(cmd[1]);
                     break;
                  case "QUIT":
                     controlOut.writeUTF("QUIT");
                     System.out.println("Client Disconnected From: " + serverName);
                     controlSocket.close();                     
                     break;
               }
            }

     	   } catch (Exception e) {
     	         System.out.println(e);
     	   }
      }
   }
}
