package chatclient;

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

public class Client  
{ 


  public static void main(String args[]) throws UnknownHostException, IOException  
  { 
      Scanner scan = new Scanner(System.in); 
        
      // ip address from hostname
      InetAddress ip = InetAddress.getByName("localhost"); 
        
      // connect to server using ip, port
      Socket socket = new Socket(ip, 8080); 
        
      // create input and output streams
      DataInputStream dis = new DataInputStream(socket.getInputStream()); 
      DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); 
      
      
      // thread for reading msgs
      
      
      
      // thread for writing msgs
    
  } 
} 