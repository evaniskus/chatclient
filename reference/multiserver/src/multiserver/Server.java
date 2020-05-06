package multiserver;
import java.net.*; 
import java.io.*; 
import java.text.*;
import java.time.*;
import java.util.*;

public class Server 
{ 
	//initialize socket and input stream 
	private Socket		 socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in	 = null;
	private DataOutputStream out = null;

	Server(int port){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
 
                System.out.println("New client connected");
                
                // client connected, parse headers
                InputStreamReader isr =  new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                String line = reader.readLine();     
                String request = "";
                
               
                // parse GET header for query 
          

                while (!line.isEmpty()) {
                    if(line.contains("GET")) {
                    	
                    	request = line;
                    	System.out.println(request);
                        String[] split = request.split(" ");
                         
                        request = split[1];
                         
                    }
                    else if(line.contains("PUT")) {
                    	request = line;
                    	System.out.println(request);
                        String[] split = request.split(" ");
                         
                        request = split[1];
                    	
                    }
                    line = reader.readLine();
                }
               

            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) 
	{ 
		Server server = new Server(8080); 
		
		
	} 
} 
