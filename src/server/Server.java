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


	public Server(){
		
		ServerConfig conf = new ServerConfig();
		this.port = conf.getServerPort();
		this.nbUsersMax = conf.getNbUsersMax();
		
		try {
			this.listCo = new ArrayList<ConnectionClient>();
			this.port = 1984;
            serverSocket = new ServerSocket(port);
   			System.out.println("[SERVER] : Server online.");
   			//ConnectionServer coServer = new ConnectionServer(this,serverSocket);
   			//coServer.start();
   			
   			while (acceptMore) {
   				System.out.println("[SERVER] : Waiting for a client to connect ...");
	         	Socket socket = this.serverSocket.accept();

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
               this.serverSocket.close();
           } catch (Exception e) {
          }
       }
	}
	
	public ArrayList<ConnectionClient> getListCo(){
		return this.listCo;
	}
	
	public ConnectionClient getCo(String nickname){
		ConnectionClient coToReturn = null;
		for(ConnectionClient co : listCo) {
			if(co.getNickname() != null && co.getNickname().equals(nickname)){
				coToReturn = co;
			}
		}
		return coToReturn;
	}
	
	public synchronized void addClient(){
		this.nbUsers ++;
	}
	
	public synchronized void removeClient(ConnectionClient co){
		Iterator<ConnectionClient> it = this.listCo.iterator();
		while (!it.next().equals(co)){}
		it.remove();
	}
}
