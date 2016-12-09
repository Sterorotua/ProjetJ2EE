package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class Server {
	
    private boolean acceptMore = true;
    private ServerSocket serverSocket;
    private int port;
    private int nbUsersMax;
    private int nbUsers = 1;
    
    private ArrayList<ConnectionClient> listCo;

	/*public static void main(String[] args) {
		new Server();
	}*/
	
	public Server(){
		
		ServerConfig conf = new ServerConfig();
		this.port = conf.getServerPort();
		this.nbUsersMax = conf.getNbUsersMax();
		
		try {
			this.listCo = new ArrayList<ConnectionClient>();
			port = 1984;
            serverSocket = new ServerSocket(port);
   			System.out.println("[SERVER] : Server online.");
   			/*ConnectionServer coServer = new ConnectionServer(this,serverSocket);
   			coServer.start();*/
   			
   			while (acceptMore) {
   				System.out.println("[SERVER] : Waiting for a client to connect ...");
	         	Socket socket = serverSocket.accept();

        	   if (this.listCo.size() < nbUsersMax) {
        		   ConnectionClient Co = new ConnectionClient(this, socket, this.nbUsers);
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
	
	public ArrayList<ConnectionClient> getListCo(){
		return this.listCo;
	}
	
	public synchronized void addClient(){
		this.nbUsers ++;
	}
	public synchronized void removeClient(ConnectionClient co){
		Iterator<ConnectionClient> it = listCo.iterator();
		
		while (!it.next().equals(co)){}
		it.remove();
	}
}
