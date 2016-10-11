import java.io.* ;
import java.net.* ;
import java.util.* ;

final class FtpRequestClient implements Runnable {
   final static String CRLF = "\r\n";
   int controlPort;
   String serverName;
   String dataServerName = "localhost";
   Socket controlSocket;
   // Socket dataSocket;

   // Control Connection
   DataInputStream controlIn;
   DataOutputStream controlOut;

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

   void connectToServer(int port, String serverName) {
      this.serverName = serverName;
      this.controlPort = port;
      try {
         controlSocket = new Socket("localhost", 10003);
         controlIn = new DataInputStream(this.controlSocket.getInputStream());
         controlOut = new DataOutputStream(this.controlSocket.getOutputStream());

         controlOut.writeUTF("DATA");
         InetAddress IP = InetAddress.getLocalHost();
         String clientAddress = IP.getHostAddress();
         controlOut.writeUTF(clientAddress);
         controlOut.writeUTF(Integer.toString(this.controlPort + 1));

         // dataSocket = new ServerSocket(10004).accept();

         this.controlOut.writeUTF("TEST");
         System.out.println("Connection Established to " + serverName + " on port: " + port);
      } catch(Exception e) {
         System.out.println(e);
      }
   }

   void listDirContents() {

      // System.out.println("Requesting dirctory contents:");
      // try {
      //    this.controlOut.writeUTF("LIST");
      //
      //    InputStream in = dataSocket.getInputStream();
      //    DataInputStream din = new DataInputStream(in);
      //
      //    String file = din.readUTF();
      //    // System.out.println("Files are: ");
      //    while (!(file.equals("END"))) {
      //       System.out.println(file);
      //       file = din.readUTF();
      //    }
      //    din.close();
      //
      // } catch (Exception e) {
      //    System.out.println(e);
      // }
   }

   void requestFile(String fileName) {

      System.out.println("File " + fileName + " received from Server.");

      try {
         controlOut.writeUTF("RETR");
         controlOut.writeUTF(fileName);
         Socket dataSocket = new ServerSocket(10004).accept();
         DataInputStream din = new DataInputStream(dataSocket.getInputStream());

         // InputStream in = dataSocket.getInputStream();
         // DataInputStream din = new DataInputStream(in);


         int bytes;

         OutputStream out = new FileOutputStream(("New" + fileName));
         long sizeOfData = din.readLong();
         byte[] buffer = new byte[1024];
         while (sizeOfData > 0 && (bytes = din.read(buffer, 0, (int) Math.min(buffer.length, sizeOfData))) != -1) {
            out.write(buffer, 0, bytes);
            sizeOfData -= bytes;
         }

         out.close();

         //   dataSocket.close();

         din.close();
         dataSocket.close();
         System.out.println("File Saved Sucessfully...");

      } catch (Exception e) {
           System.out.println(e);
      }

   }

   void sendFile(String fileName) {

      // try{
      //    controlOut.writeUTF("STOR");
      //    controlOut.writeUTF(fileName);
      //
      //    // Socket dataSocket = new Socket(clientName, dataPort);
      //    OutputStream out = dataSocket.getOutputStream();
      //    DataOutputStream dout = new DataOutputStream(out);
      //    // DataOutputStream dout = new DataOutputStream(dataSocket.getOutputStream());
      //
      //    File file = new File(fileName);
      //    byte[] bytes = new byte[(int)file.length()];
      //
      //    FileInputStream fin = new FileInputStream(file);
      //
      //    BufferedInputStream buffin = new BufferedInputStream(fin);
      //
      //    DataInputStream datain = new DataInputStream(buffin);
      //    datain.readFully(bytes, 0, bytes.length);
      //
      //    dout.writeLong(bytes.length);
      //    dout.write(bytes, 0, bytes.length);
      //    dout.flush();
      //    System.out.println("File Sent To Server...");
      //
      // } catch (Exception e) {
      //   System.out.println(e);
      // }

   }

   void quit(){
      try{
         this.controlOut.writeUTF("QUIT");
         if(this.dataIn != null){ this.dataIn.close(); }
         if(this.dataOut != null){ this.dataOut.close(); }
         if(this.dataSocket != null) { this.dataSocket.close();}
      } catch(Exception e){
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
                     requestFile(cmd[1]);
                     break;
                  case "STOR":
                     System.out.println("User entered Save file: " + cmd[1]);
                     sendFile(cmd[1]);
                     break;
                  case "QUIT":
                     controlOut.writeUTF("QUIT");
                     System.out.println("Client Disconnect...");
                     controlSocket.close();
                     // dataSocket.close();
                     break;
               }
            }

     	   } catch (Exception e) {
     	         System.out.println(e);
     	   }
      }
   }
}
