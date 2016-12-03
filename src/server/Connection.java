package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Connection extends Thread{

	private Server server;
	private Socket socket;
	private int idUser;
	
	Connection(Server server, Socket socket, int idUser){
		this.server = server;
		this.socket = socket;
		this.idUser = idUser;
	}
	
	public void run(){
		PrintStream ps = null;
		String msg = "";
		
		this.server.addClient();
		
		try {
			ps = new PrintStream(socket.getOutputStream());
			ps.println("[SERVER] : You are connected on the server as [CLIENT n°"+ this.idUser +"]");
			
			scanner : while (true) {
				switch(msg = this.receiveMessage()) {
					case "quit" : this.sendMessage(msg);
								  System.out.println("[SERVER] : Client disconnected.");
								  
					case "close" : break scanner;
					default : this.broadcast(msg);
				}
			}	
			socket.close();
			this.server.removeClient(this);
			
		} catch (SocketException exp){
			System.out.println("[SERVER] : Connexion lost with [CLIENT n°"+ this.idUser +"].");
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public void sendMessage(String msgRecu){
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
	
			if (msgRecu.equals("quit")){
				System.out.println("[SERVER] : Sending GoodBye to [CLIENT n°"+ this.idUser +"].");
				ps.println("[SERVER] : Message recu : ADIOS");
			}
			else {
				System.out.println("[SERVER] : Sending a message to [CLIENT n°"+ this.idUser +"].");
				ps.println("[SERVER] : Message received");
			}			
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public String receiveMessage(){
		String msg = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			msg = br.readLine();
			System.out.println("[CLIENT n°"+ this.idUser +"] : "+msg);
		} catch (SocketException exp){
			System.out.println("[SERVER] : Connexion lost with [CLIENT n°"+ this.idUser +"].");
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
	
	public void broadcast(String msgRecu) {
		ArrayList<Connection> listCo = server.getListCo();
		
		for (int i=0 ; i<listCo.size() ; i++) {
			Socket socketBroad = listCo.get(i).socket;
				if(!socketBroad.equals(socket)){
				try {
					PrintStream ps = new PrintStream(socketBroad.getOutputStream());
					ps.println("[CLIENT n°"+ this.idUser +"] : "+msgRecu);
				} catch (IOException exp) {
					exp.printStackTrace();
				}
			}
		}
	}
	
}
