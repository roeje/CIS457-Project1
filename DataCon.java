import java.io.* ;
import java.net.* ;
import java.util.* ;

final class DataCon {

   Socket dataSocket = null;
   DataInputStream dataIn = null;
   DataOutputStream dataOut = null;

   void DataCon(int port, String serverName) {
     try {
        this.dataSocket = new Socket("localhost", dataPort);
        this.dataIn = new DataInputStream(this.dataSocket.getInputStream());
        this.dataOut = new DataOutputStream(this.dataSocket.getOutputStream());
   
     } catch (Exception e) {
        System.out.println(e);
     }
   }

}
