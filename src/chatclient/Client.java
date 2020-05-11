package chatclient;


import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

public class Client{ 
	

	public static void main(String args[]) throws UnknownHostException, IOException{ 
		Scanner scan = new Scanner(System.in); 
		
		// get  IP from host name
		InetAddress ip = InetAddress.getByName("localhost"); 
		
		// connect to IP, port using socket connection
		Socket socket = new Socket(ip, 8080); 
		
		// data input/output streams. nothing special
		DataInputStream dis = new DataInputStream(socket.getInputStream()); 
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); 
		
		// login process
		// welcome msg, ask user for username, send username to server
		System.out.println("Hello, welcome to Biggus Brainus chat. Please enter a user name: ");
		String username = scan.next();
		dos.writeUTF(username); 
		
		
		// client waits for ACK msg before continuing
		while(true) {
			try {
				String msg = dis.readUTF(); 
				if(msg.equals("LOGIN-OK")) {
					break;
				}
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
		}
		
		
		
		// thread for sendding messages
		// if "." is entered, send logout request to server
		Thread writeMsg = new Thread(new Runnable(){ 
			@Override
			public void run() { 
				while (true) { 
					String msg = scan.nextLine(); 
					try { 
						dos.writeUTF(msg); 
					} catch (IOException e) { 
						e.printStackTrace(); 
					} 
				} 
			} 
		}); 
		
		
		
		// thread for reading messages
		// if the client RECEIVES "QUIT-OK" this means previously it sent a logout request
		// to the server. server removes it from lists, closes socket, etc, then sends an OK
		// back to client. then client quits. otherwise, simply display the message
		Thread readMsg = new Thread(new Runnable(){ 
			@Override
			public void run() { 
				while (true) { 
					try { 
						String msg = dis.readUTF();
						
						
						if(msg.equals(".")) {
							System.exit(0);
						}
						System.out.println(msg); 
					} catch (IOException e) { 
						e.printStackTrace(); 
					} 
				} 
			} 
		}); 
		writeMsg.start(); 
		readMsg.start(); 

	} 
} 