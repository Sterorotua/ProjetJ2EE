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
		try {
			ps = new PrintStream(socket.getOutputStream());
			ps.println("[Client n°"+ this.idUser +"] : Vous etes connecte au serveur.");
			
			for(int i=9 ; i>=0 ; i--){
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println("[Client n°"+ this.idUser +"] : "+br.readLine());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.messageSuivant(i);
			}
			
			socket.close();
			System.out.println("[Server] Client deconnecte.");
			
		} catch (SocketException exp){
			System.out.println("[Server] Perte de la connexion avec le client.");
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
	
	public void messageSuivant(int i){
		System.out.println("[Server] Envoi d'un message a [Client n°"+ this.idUser +"].");
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());

			if (i == 0){
				ps.println("quit");
			}
			else {
				ps.println("[Server] : Encore " +i+ " messages");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
