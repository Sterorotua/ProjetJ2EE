package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {
	
    private boolean acceptMore = true;
    private ServerSocket serverSocket = null;
    private int port = 1984;
    private int nbUsers = 1;
    
    private ArrayList listCo;

	public static void main(String[] args) {
		new Server();
	}
	
	public Server(){
		
		try {
			this.listCo = new ArrayList<Connexion>();
			
            serverSocket = new ServerSocket(port);
   			System.out.println("[Server] : Serveur en ligne.");
   			
   			while (acceptMore) {
   				System.out.println("[Server] : Attente de la connexion d'un client ...");
	         	Socket socket = serverSocket.accept();

        	   if (this.listCo.size() < 5) {
        		   Connexion Co = new Connexion(this, socket, this.nbUsers);
	               this.listCo.add(Co);
	               System.out.println("[Server] : [Client n°"+ this.nbUsers +"] connecte.");
	               Co.start();
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
	
	public synchronized void addClient(){
		this.nbUsers ++;
	}
	public synchronized void removeClient(Connexion co){
		Iterator it = listCo.iterator();
		
		while (!it.next().equals(co)){
			
		}
		it.remove();
	}
}
