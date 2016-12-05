package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class Server {
	
    private boolean acceptMore = true;
    private ServerSocket serverSocket = null;
    private int port;
    private int nbUsersMax;
    private int nbUsers = 1;
    
    private ArrayList<Connection> listCo;

	public static void main(String[] args) {
		new Server();
	}
	
	public Server(){
		
		ServerConfig conf = new ServerConfig();
		port = conf.getServerPort();
		nbUsersMax = conf.getNbUsersMax();
		
		try {
			this.listCo = new ArrayList<Connection>();
			
            serverSocket = new ServerSocket(port);
   			System.out.println("[SERVER] : Server online.");
   			
   			while (acceptMore) {
   				System.out.println("[SERVER] : Waiting for a client to connect ...");
	         	Socket socket = serverSocket.accept();

        	   if (this.listCo.size() < nbUsersMax) {
        		   Connection Co = new Connection(this, socket, this.nbUsers);
	               this.listCo.add(Co);
	               System.out.println("[SERVER] : [CLIENT n°"+ this.nbUsers +"] connected.");
	               Co.start();
        	   }
        	   else {
        		   System.out.println("[SERVER] Too many client!");
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
	
	public ArrayList<Connection> getListCo(){
		return this.listCo;
	}
	
	public synchronized void addClient(){
		this.nbUsers ++;
	}
	public synchronized void removeClient(Connection co){
		Iterator<Connection> it = listCo.iterator();
		
		while (!it.next().equals(co)){}
		it.remove();
	}
}
