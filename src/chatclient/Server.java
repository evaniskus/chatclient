package chatclient;
import java.io.*; 
import java.util.*;



import java.net.*; 
  

public class Server implements Runnable {
	//initialize socket and input stream 
	static Vector clients = new Vector<>(); 
	private InputStreamReader isr;
	private BufferedReader reader;
	
	public Server (int port, Socket socket) throws IOException {
		 this.isr =  new InputStreamReader(socket.getInputStream());
         this.reader = new BufferedReader(isr);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// This is for after the thread has been created and handles the responses to the client
		// i.e. welcome message. log out, send and receive msgs
		for(int i = 0; i < 5; i++) {
			System.out.println("yeet");
		}
		
	}

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// the main acts as the always on engine of server
		// keeps looping and accepting new client connections
		// then creates a threaded server to handle client
		
		
		int port = 8080;
		try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
 
                System.out.println("New client connected");
                
                // client connected, parse headers
               
                Server server = new Server(port, socket);
 
                //create new threads 
                Thread t = new Thread(server);
              
                t.start();
               

            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	
}
