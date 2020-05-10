package chatclient;
import java.io.*; 
import java.util.*;


import java.net.*; 
  

import java.io.*; 
import java.util.*; 
import java.net.*; 

// Server class 
public class Server 
{ 

	// Vector to store active clients 
	static Vector<ClientHandler> ar = new Vector<>(); 
	
	// counter for clients 
	static int i = 0; 

	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 1234 
		ServerSocket ss = new ServerSocket(8080); 
		
		Socket s; 
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			// Accept the incoming request 
			s = ss.accept(); 

			System.out.println("New client request received : " + s); 
			
			// obtain input and output streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
			
			System.out.println("Creating a new handler for this client..."); 
			String username = dis.readUTF(); 
			
			// Create a new handler object for handling this request. 
			ClientHandler mtch = new ClientHandler(s,username, dis, dos); 
			dos.writeUTF("Welcome, " + username);
			

			// Create a new Thread with this object. 
			Thread t = new Thread(mtch); 
			
			System.out.println("Adding this client to active client list"); 

			// add this client to active clients list 
			ar.add(mtch); 

			
			for(ClientHandler mc: Server.ar){
				if (mc.isloggedin == true){ 
						mc.dos.writeUTF("A new client has joined the chat."); 
					} 
			}
			// start the thread. 
			t.start(); 

			// increment i for new client. 
			// i is used for naming only, and can be replaced 
			// by any naming scheme 
			i++; 

		} 
	} 
} 

// ClientHandler class 
class ClientHandler implements Runnable 
{ 
	Scanner scn = new Scanner(System.in); 
	private String name; 
	final DataInputStream dis; 
	final DataOutputStream dos; 
	Socket s; 
	boolean isloggedin; 
	
	// constructor 
	public ClientHandler(Socket s, String name, 
							DataInputStream dis, DataOutputStream dos) { 
		this.dis = dis; 
		this.dos = dos; 
		this.name = name; 
		this.s = s; 
		this.isloggedin=true; 
	} 

	@Override
	public void run() { 

		String received; 
		while (true) 
		{ 
			try
			{ 
				// receive the string 
				received = dis.readUTF(); 
				
//				System.out.println(received); 
				
				if(received.equals("LOGOUT")){ 
					
					for(ClientHandler mc: Server.ar){
						if (mc.isloggedin == true){ 
								mc.dos.writeUTF(this.name + " has left the chat"); 
							} 
					}
					dos.writeUTF("QUIT-OK");
					
					
					
					
					
					
					this.isloggedin=false; 
					this.s.close(); 
					
					
					
					
					break; 
				} 
//				
				
				
				for(ClientHandler mc: Server.ar){
					if (mc.isloggedin == true){ 
							mc.dos.writeUTF(this.name+" : "+received); 
						} 
				}
				

			} catch (IOException e) { 
				
				e.printStackTrace(); 
			} 
			
		} 
		try
		{ 
			// closing resources 
			this.dis.close(); 
			this.dos.close(); 
			
		}catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 
} 
