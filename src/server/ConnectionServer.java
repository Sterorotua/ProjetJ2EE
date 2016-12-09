package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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
			serverSocket.setSoTimeout(2000);
			while(true) {
				System.out.println("ecoute");
				this.socket = serverSocket.accept();
				System.out.println("connexion");
				this.socket = new Socket("172.20.10.2", 1984);
			}
			//ps = new PrintStream(socket.getOutputStream());
			//ps.println("[SERVER] : You are connected linked to the other server");
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
