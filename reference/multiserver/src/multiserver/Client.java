 package multiserver;
import java.net.*; 
import java.io.*; 

public class Client {
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	
	public Client(String host, int port, String method, String file) {
		try{
   			InetAddress ip = InetAddress.getByName(host);
   			
   			socket = new Socket(ip, port);
   			System.out.println("Connected to server");
   			
   			// accept input from console
//   			input = new DataInputStream(System.in);
//   			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//   			String request = reader.readLine();
//   			
//   			System.out.println(request);
//   			
   			
   			String request = method + " " + file;
   			socket.getOutputStream().write(request.getBytes("UTF-8"));

   			
//   			output = new DataOutputStream(socket.getOutputStream());
//   			BufferedReader out = new BufferedReader(new InputStreamReader(input));
 			
   			
   		}catch(Exception e){
   			e.getMessage();
   		}
		
		
	}
	
	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		String host = "";
		int port = 0;
		String method = "";
		String file = "";
		
		// myclient host port method file

		
//		if (args.length == 0){
//			System.out.println("Please run the command again with exactly FOUR arguments");
//			System.out.println("myclient host port method file");
//			System.exit(0);			
//		}
//		else if(args.length == 4){
//			host = args[0];
//			port = Integer.valueOf(args[1]);
//			method = args[2];
//			file = args[3];				
//		}
//		else {
//			System.out.println("Please run the command again with exactly FOUR arguments");
//			System.out.println("myclient host port method file");
//			System.exit(0);
//		}
//		
		
		
		
		Client client = new Client("127.0.0.1", 8080, "GET", "file.txt"); 
		//Client client = new Client(host, port, method, file); 
	}

}
