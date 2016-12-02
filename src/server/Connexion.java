package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;

public class Connexion extends Thread{

	private Server server;
	private Socket socket;
	private int idUser;
	
	Connexion(Server server, Socket socket, int idUser){
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
			ps.println("You are connected on the server as [CLIENT n°"+ this.idUser +"]");
			
			scanner : while (true) {
				switch(msg = this.receptionMessage()) {
					case "quit" : this.envoiMessage(msg);
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
	
	public void envoiMessage(String msgRecu){
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
	
			if (msgRecu.equals("quit")){
				System.out.println("[SERVER] : Sending GoodBye to [CLIENT n°"+ this.idUser +"].");
				ps.println("Message recu : ADIOS");
			}
			else {
				System.out.println("[SERVER] : Sending a message to [CLIENT n°"+ this.idUser +"].");
				ps.println("Message received");
			}			
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public String receptionMessage(){
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
		ArrayList<Connexion> listCo = server.getListCo();
		
		for (int i=0 ; i<listCo.size() ; i++) {
			Socket socketBroad = listCo.get(i).socket;
				if(!socketBroad.equals(socket)){
				try {
					PrintStream ps = new PrintStream(socketBroad.getOutputStream());
					ps.println(msgRecu);
				} catch (IOException exp) {
					System.out.println("erreru broadcast");
					exp.printStackTrace();
				}
			}
		}
	}
	
}
