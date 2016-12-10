package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ConnectionServer extends Thread{

	private Server server;
	private ServerSocket serverSocket;
	private Socket socket;
	
	public ConnectionServer(Server server, ServerSocket serverSocket) {
		this.server = server;
		this.serverSocket = serverSocket;
	}
	
	public void run() {
		PrintStream ps = null;
		String msg;
		
		try {
			this.socket = new Socket("127.0.0.1", 1984);
		} catch (SocketException e) {
			System.out.println("[SERVER] : No other server connected. Waiting for one to connect ...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			this.socket = serverSocket.accept();
			
			ps = new PrintStream(socket.getOutputStream());
			ps.println("[SERVER] : You are connected linked to the other server");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true){
			msg = receiveMessage();
		}
	}
	
	public void sendMessage(String msgRecu){
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println("[SERVER] : We are linked!");
			System.out.println("[SERVER] : Sending a message to the other server.");
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public String receiveMessage(){
		String msg = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			msg = br.readLine();
			System.out.println("[SERVER] : "+msg);
		} catch (SocketException exp){
			System.out.println("[SERVER] : Connexion lost with the other server.");
			try {
				socket.close();
				return "close";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		return msg;
	}	
}
