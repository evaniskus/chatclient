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
		
		
		
		
		
		System.out.println("Hello, welcome to Biggus Brainus chat. Please enter a user name: ");
		String username = scan.next();
		
		// login process
		dos.writeUTF(username); 
		
		
		
		// sendMessage thread 
		Thread sendMessage = new Thread(new Runnable() 
		{ 
			@Override
			public void run() { 
				while (true) { 

					// read the message to deliver. 
					String msg = scan.nextLine(); 
					if(msg.equals(".")) {
						try { 
							// write on the output stream 
							dos.writeUTF("LOGOUT"); 
							System.exit(0);
						} catch (IOException e) { 
							e.printStackTrace(); 
						} 
					}
					
					
					
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