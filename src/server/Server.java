package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
    private boolean acceptMore = true;
    private ServerSocket serverSocket = null;
    private int port = 1984;
    private int nbUsers = 0;
    private Connexion[] listCo;

	public static void main(String[] args) {
		new Server();
	}
	
	public Server(){
		
		try {
			this.listCo = new Connexion[5];
            serverSocket = new ServerSocket(port);
   			System.out.println("[Server] : Serveur en ligne.");
   			
   			while (acceptMore) {
   				System.out.println("[Server] : Attente de la connexion d'un client ...");
	         	Socket socket = serverSocket.accept();

        	   if (this.nbUsers <= this.listCo.length-1) {
	               this.listCo[this.nbUsers] = new Connexion(this, socket, this.nbUsers);
	               System.out.println("[Server] : [Client n°"+ this.nbUsers +"] connecte.");
	               this.listCo[this.nbUsers].start();
	               this.nbUsers ++;
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
}
