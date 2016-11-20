package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
    private static boolean acceptMore = true;

	public static void main(String[] args) {

		new Server();
	}
	
	public Server(){
		ServerSocket serverSocket = null;
		try {
            serverSocket = new ServerSocket(1984, 100);
   			System.out.println("[Server] Serveur en ligne.");
           while (acceptMore) {
        	   System.out.println("[Server] Attente de la connexion d'un client ...");
               Socket socket = serverSocket.accept();
               System.out.println("[Server] Client connecte.");

               new Thread(new SocketThread(socket)).start();    
           }
       } catch (IOException exp) {
           exp.printStackTrace();
       } finally {
           try {
               serverSocket.close();
           } catch (Exception e) {
          }
       }
	}
}
