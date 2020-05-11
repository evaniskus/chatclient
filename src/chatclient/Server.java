package chatclient;
import java.io.*; 
import java.util.*;
import java.net.*; 


public class Server implements Runnable{ 
	// typical imports, get cmds from cmdline, create input/output streams, sockets
	Scanner scan = new Scanner(System.in); 
	private String userName; 
	final DataOutputStream dos; 
	final DataInputStream dis; 
	Socket socket; 
	boolean loggedin; 
	
	// client stored in vector b/c vector is synchronized
	// unlike Arraylists, which we where thinking of using at first
	static Vector<Server> clients = new Vector<>(); 
	
	// counter connected clients to keep track of clients
	static int connectedClients = 0; 
	
	
	// default constructor to do fun OOP things
	public Server(Socket s, String name, DataInputStream dis, DataOutputStream dos) { 
		this.dos = dos; 
		this.dis = dis; 
		this.userName = name; 
		this.socket = s; 
		this.loggedin=true; 
	}
	
	
	//  multi-threaded part, once a thread has been started it'll do this
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String received; 
		while (true){ 
			try{ 
				// get received msg
				received = dis.readUTF(); 
				

				// check to see if client send LOGOUT msg, i.e. client requests to leave
				if(received.equals(".")){ 
					connectedClients--;
					for(Server currClient: Server.clients){
						if (currClient.loggedin == true){ 
								currClient.dos.writeUTF(this.userName + " has left the chat"); 
								currClient.dos.writeUTF("There are currently " + (connectedClients) + " users online");
							} 
					}
					dos.writeUTF(".");
					this.loggedin=false; 
					this.socket.close();
					break; 
				} 
				
				// otherwise, send received msg to all other connected clients
				if(!received.equals("")){
					for(Server currClient: Server.clients){
						if (currClient.loggedin == true){ 
								currClient.dos.writeUTF(this.userName+" : "+received); 
							} 
					}	
				}
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
		} 
		try{ 
			this.dis.close(); 
			this.dos.close(); 		
		}catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 
	
	
	public static void main(String[] args) throws IOException{ 
		ServerSocket serversocket = new ServerSocket(8080); 	
		Socket socket; 
		/*
		 * Loop looks for and accepts any client socket requests
		 * Creates data input/output streams
		 * Read initial msg from client whhich contains client's username 
		 * Creates a new server object to handle the client's requests  and  msgs
		 * Adds client to vector list
		 * Announces that a new client has joined the room
		 * Starts a new thread using the newly created server object
		 * 
		 */
		
		
		while (true) { 
			// Accept the incoming request 
			socket = serversocket.accept(); 
			System.out.println("New client connected @ " + socket); 
			
			// create data input/output streams
			DataInputStream dis = new DataInputStream(socket.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); 
			
			
			//  initial msg is username, take that and send back an ACK msg
			String username = dis.readUTF(); 
			dos.writeUTF("LOGIN-OK");
			dos.writeUTF("Welcome, " + username);
			
			
			// create new server object to handle connection
			// then create a new thread for that server object
			Server server = new Server(socket,username, dis, dos); 
			Thread t = new Thread(server); 
			


			clients.add(server); 

			// announce that new client has connected
			for(Server currClient: Server.clients){
				if (currClient.loggedin == true){ 
						currClient.dos.writeUTF(username + " has joined the chat."); 
						currClient.dos.writeUTF("There are currently " + (connectedClients+1) + " users online"); 
					} 
			}

			
			
			t.start(); 


			connectedClients++; 
			

		} 
	}

	
} 

