import java.net.*;
import java.io.*;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
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
      for(String fileName : list){
      	System.out.println(fileName);
      }
    }

}