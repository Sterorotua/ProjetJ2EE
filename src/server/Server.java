package server;
import database.Database;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class Server {
	
    private ServerSocket serverSocket;
    private Database db;
    private int port;
    private int nbUsersMax;
    private int nbUsers = 1;
    
    private ArrayList<ConnectionClient> listCo;
	private static Logger logger = Logger.getLogger(Server.class);


	public Server(){
		
		ServerConfig conf = new ServerConfig();
		this.port = conf.getServerPort();
		this.nbUsersMax = conf.getNbUsersMax();
		this.db = new Database();
		

		
		try {
			this.listCo = new ArrayList<ConnectionClient>(); //Liste des Thread de connexion avec les clients 
            serverSocket = new ServerSocket(this.port);
            logger.info("Server online");
   			System.out.println("[SERVER] : Server online at "+serverSocket.getInetAddress()+":"+serverSocket.getLocalPort());
   			
   			while (true) {
   				System.out.println("[SERVER] : Waiting for a client to connect ...");
	         	Socket socket = this.serverSocket.accept(); //Attente de la connexion d'un client

        	   if (this.listCo.size() < nbUsersMax) { 
        		   ConnectionClient Co = new ConnectionClient(this, socket, this.db, this.nbUsers); //Création d'un objet connexion client (qui est un thread)
	               this.listCo.add(Co); //on ajout cet objet à la liste des connexions avec les clients
	               System.out.println("[SERVER] : [CLIENT n°"+ this.nbUsers +"] connected.");
	               Co.start();
        	   }
        	   else {
        		   logger.warn("Client limit has been reached");
        		   System.out.println("[SERVER] Too many client!");
        	   }
           }
       } catch (IOException exp) {
           exp.printStackTrace();
       } finally {
           try {
               logger.info("Server socket has been closed properly");
               this.serverSocket.close();
           } catch (Exception e) {
        	   logger.error("Error during server socket closing");
          }
       }
	}
	
	public ArrayList<ConnectionClient> getListCo(){
		return this.listCo;
	}
	
	//Récupération d'une connexion en fonction du nom de l'utilisateur
	public ConnectionClient getCo(String nickname){
		ConnectionClient coToReturn = null;
		for(ConnectionClient co : listCo) {
			if(co.getInfoUser().getNickname() != null && co.getInfoUser().getNickname().equals(nickname)){
				coToReturn = co;
			}
		}
		return coToReturn;
	}
	
	public synchronized void addClient(){
		//logger.info("Variable nbUsers incremented: nbUsers = " + this.nbUsers);
		this.nbUsers ++;
	}
	
	//Supprime un objet connexionClient de la liste
	public synchronized void removeClient(ConnectionClient co){
		Iterator<ConnectionClient> it = this.listCo.iterator();
		while (!it.next().equals(co)){}
		it.remove();
		//logger.info("Client has been removed properly");
	}
}
