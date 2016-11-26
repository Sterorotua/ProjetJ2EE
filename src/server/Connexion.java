package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

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
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("[Client n°"+ this.idUser +"] : "+br.readLine());
			socket.close();
			System.out.println("[Server] Client deconnecte.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
