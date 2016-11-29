package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

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
			ps.println("[Client n°"+ this.idUser +"] : Vous etes connecte au serveur.");
			
			scanner : while (true) {
				switch(msg = this.receptionMessage()) {
					case "quit" : this.envoiMessage(msg);
								  System.out.println("[Server] Client deconnecte.");
								  
					case "close" : break scanner;
					default : this.envoiMessage(msg);
				}
			}	
			socket.close();
			this.server.removeClient(idUser);
			
		} catch (SocketException exp){
			System.out.println("[Server] Perte de la connexion avec le [Client n°"+ this.idUser +"].");
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
				System.out.println("[Server] Envoi d'un message d'aurevoir a [Client n°"+ this.idUser +"].");
				ps.println("[Server] Message recu : ADIOS");
			}
			else {
				System.out.println("[Server] Envoi d'un message a [Client n°"+ this.idUser +"].");
				ps.println("[Server] Message recu");
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
			System.out.println("[Client n°"+ this.idUser +"] : "+msg);
		} catch (SocketException exp){
			System.out.println("[Server] Perte de la connexion avec le [Client n°"+ this.idUser +"].");
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
