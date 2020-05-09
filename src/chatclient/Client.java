package chatclient;


import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

public class Client{ 
	

	public static void main(String args[]) throws UnknownHostException, IOException{ 
		Scanner scan = new Scanner(System.in); 
		
		// getting localhost ip 
		InetAddress ip = InetAddress.getByName("localhost"); 
		
		// establish the connection 
		Socket socket = new Socket(ip, 8080); 
		
		// obtaining input and out streams 
		
		DataInputStream dis = new DataInputStream(socket.getInputStream()); 
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); 
		
		// sendMessage thread 
		Thread sendMessage = new Thread(new Runnable() 
		{ 
			@Override
			public void run() { 
				while (true) { 

					// read the message to deliver. 
					String msg = scan.nextLine(); 
					
					try { 
						// write on the output stream 
						dos.writeUTF(msg); 
					} catch (IOException e) { 
						e.printStackTrace(); 
					} 
				} 
			} 
		}); 
		
		// readMessage thread 
		Thread readMessage = new Thread(new Runnable() 
		{ 
			@Override
			public void run() { 

				while (true) { 
					try { 
						// read the message sent to this client 
						String msg = dis.readUTF(); 
						System.out.println(msg); 
					} catch (IOException e) { 

						e.printStackTrace(); 
					} 
				} 
			} 
		}); 

		sendMessage.start(); 
		readMessage.start(); 

	} 
} 