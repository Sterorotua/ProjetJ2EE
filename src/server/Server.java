package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
    private boolean acceptMore = true;
    private ServerSocket serverSocket = null;
    private int port = 1984;
    private int nbUsers = 0;
    private Socket[] listSocket;

	public static void main(String[] args) {
		new Server();
	}
	
	public Server(){
		
		try {
			this.listSocket = new Socket[5];
            serverSocket = new ServerSocket(port);
   			System.out.println("[Server] Serveur en ligne.");
   			
   			while (acceptMore) {
   				System.out.println("[Server] Attente de la connexion d'un client ...");
	         	Socket socket = serverSocket.accept();

        	   if (this.nbUsers < this.listSocket.length) {
	               this.listSocket[this.nbUsers] = socket;
	               this.newClient();
	               System.out.println("[Server] Client n°"+ this.nbUsers +" connecte.");
	               new Thread(new SocketThread(socket)).start(); 
        	   }
        	   else {
        		   System.out.println("[Server] Nombre max de client atteint.");
        	   }
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
	
	public synchronized void newClient(){
		this.nbUsers = this.nbUsers+1;
	}
}
