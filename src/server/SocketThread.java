package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketThread extends Thread{

	 private Socket socket;

     public SocketThread(Socket socket) {
         this.socket = socket;
     }

     @Override
     public void run() {
		PrintStream ps = null;
		try {
			ps = new PrintStream(socket.getOutputStream());
			ps.println("Vous etes connecte au serveur. Un petit mot?");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("[Server] "+br.readLine());
			socket.close();
			System.out.println("[Server] Client deconnecte.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
