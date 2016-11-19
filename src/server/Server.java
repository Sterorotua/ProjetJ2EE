package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		try{
			ServerSocket socketServer = new ServerSocket(1984);
			System.out.println("[Server] Serveur en ligne");
			Socket socket = socketServer.accept();
			System.out.println("[Server] Connexion d'un client");
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println("Vous êtes connecté au serveur. Un petit mot?");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("[Server] "+br.readLine());
			
			socket.close();
			socketServer.close();
			
			System.out.println("[Server] Serveur fermé");
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
