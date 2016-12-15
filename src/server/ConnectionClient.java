package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import database.Database;

public class ConnectionClient extends Thread{

	private Server server;
	private Socket socket;
	private Database db;
	private int idUser;
	InfoUser infoUser;
	UpdateGUI updateGui;
	
	private static Logger logger = Logger.getLogger(Server.class);
	
	ConnectionClient(Server server, Socket socket, Database db, int idUser){
		this.server = server;
		this.socket = socket;
		this.idUser = idUser;
		this.db = db;
		this.infoUser = new InfoUser();
		this.infoUser.setNickname(Integer.toString(this.idUser));
	}
	
	public void run(){
		PrintStream ps;
		this.server.addClient();
		try {
			this.updateGui = new UpdateGUI(this, this.db);
			
			
			this.processing(); //Lancement de la gestion des commandes
	
			socket.close();
			this.server.removeClient(this);
			
		} catch (SocketException exp){
			System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
			logger.error("Connection lost with Client id = " + this.idUser);
			try {
				socket.close();
				logger.info("Client socket has been close Client id = " + this.idUser);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}

	//Décripte le String reçu et effectue les actions demandés
	public void processing() {
		String msg;
		scanner : while (true) {
			msg = this.receiveMessage();
			StringTokenizer st = new StringTokenizer(msg); //Sépare la commande du message
			String cmd = st.nextToken();
			msg = msg.replace(cmd+" ", "");
			
			switch(cmd) {
											
				case "/nbClient" : this.sendMessage(""+server.getListCo().size());
							break;
							
				case "/clearCoDB" : db.clearCoDB();
							break;
			
				//Demande de connexion d'un utilisateur
				case "/g" : this.connectionUser(msg);
							logger.info("User asked a connection. /g command");
							break;
						
				//Demande de connexion d'un admin
				case "/l" : String nickname = st.nextToken();
							msg = msg.replace(nickname+" ", "");
							String password = st.nextToken();
							this.connectionAdmin(nickname, password);
							logger.info("Admin asked a connection. /l command");
							break;
				
				//Demande d'envoie d'un broadcast
				case "/b" : this.broadcast(msg);
							logger.info("Broadcast message asked. /b command");
							break;
						
				//Demande d'envoie d'un message privé
				case "/w" : String nicknameReceiver = st.nextToken();
							msg = msg.replace(nicknameReceiver+" ", "");
							this.sendPrivateMessage(msg, nicknameReceiver);
							logger.info("Private message asked. /w command");
							break;
							
				case "/s" : nickname = st.nextToken();
							msg = msg.replace(nickname+" ", "");
							int status = Integer.parseInt(st.nextToken());
							this.db.setStatus(nickname, this.infoUser.isAdmin(), status);
							logger.info("Status change asked. /s command");
							break;
						
				//Demande d'arret de la connexion
				case "/c" : this.updateGui.stop();
							if (this.infoUser.getStatus() == 1 || this.infoUser.getStatus() == 2 || this.infoUser.getStatus() == 3){
								this.disconnectInDB();
							}
							logger.info("Disconnect asked. /c command");
							break scanner;
				
				//Demande de notification d'un utilisateur
				case "/n" : String nicknameNotified = st.nextToken();
							if (this.notifieUser(nicknameNotified) >= 3){
								ConnectionClient coToBan = server.getCo(nicknameNotified);
								coToBan.sendMessage("/banned "+nicknameNotified);
								this.broadcastServer("User ["+nicknameNotified+"] BANNED (has been notified 3 time).");
								logger.info("Notification asked. /n command");
							}
							break;
								 
				case "/ban" : if (this.infoUser.isAdmin()){
								  String nicknameBanned = st.nextToken();
								  ConnectionClient coToBan = server.getCo(nicknameBanned);
								  coToBan.sendMessage("/banned "+nicknameBanned);
								  this.broadcastServer("User ["+nicknameBanned+"] BANNED by ["+this.infoUser.getNickname()+"].");
								  logger.info("Ban asked. /ban command");
							  }
							  break;
							  
				case "/banned" : this.db.setBan(this.infoUser.getNickname(), true);
								 logger.info("Banning a user. /banned comand");
				 			     break;
							  
				case "/kick" : if (this.infoUser.isAdmin()){
								  String nicknameKicked = st.nextToken();
								  ConnectionClient coToKick = server.getCo(nicknameKicked);
								  coToKick.sendMessage("/kicked "+nicknameKicked);
								  this.broadcastServer("User ["+nicknameKicked+"] KICKED by ["+this.infoUser.getNickname()+"].");
								  logger.info("Kicking a user. /kick command");
							  }	
							  break;
							  
				case "/kicked" : break;
							  
				case "/getHisto" : String dest = st.nextToken();
									if(dest.equals("Broadcast")){
										this.sendHistoryBroacast();	
								}
									break;
							
				case "/authorize" : if (this.infoUser.isAdmin()){
									   String nicknameAuthorized = st.nextToken();
									   this.db.clearNotifications(nicknameAuthorized);
									   this.db.setBan(nicknameAuthorized, false);
									   logger.info("Authorizing an admin. /authorize command");
								    }
								   break;
				
				default : System.out.println("normal message : "+msg);
						  logger.info("Default output");
			}
			
		}
	}
	
	//Envoie d'un message simple
	public void sendMessage(String msgToSend){
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
	
			if (msgToSend.equals("quit")){
				System.out.println("[SERVER] : Sending GoodBye to [CLIENT "+ this.idUser +"].");
				ps.println("[SERVER] : Message recu : ADIOS");
				logger.info("quit message sent");
			}
			else {
				ps.println(msgToSend);
			}			
		} catch (SocketException exp){
			System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
			logger.error("Connection lost with a client");
			try {
				socket.close();
				this.disconnectInDB();
				logger.info("Closing socket and disconnection from DB");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	//Envoie d'un message privé à un utilisateur spécifique
	public void sendPrivateMessage(String msgToSend, String nicknameReceiver) {
		ConnectionClient coReceiver = server.getCo(nicknameReceiver);
		if (coReceiver == null){
			this.sendMessage(nicknameReceiver+ "unknown");
		}
		else {
			try {
				PrintStream ps = new PrintStream(coReceiver.socket.getOutputStream());
				ps.println("/w "+this.infoUser.getNickname()+" ["+this.infoUser.getNickname()+"] : "+msgToSend);
				System.out.println("[SERVER] : "+"[CLIENT "+ this.idUser +"] - "+this.infoUser.getNickname()+" Sending a private message to "+nicknameReceiver+".");
				logger.info("Private message sent");
			} catch (SocketException exp){
				System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
				logger.error("Connection lost with a client");
				try {
					socket.close();
					this.disconnectInDB();
					logger.info("Closing socket and disconnection from DB");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException exp) {
				exp.printStackTrace();
			}
		}
	}
	
	//Réceptionne un message
	public String receiveMessage(){
		String msgReceived = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			msgReceived = br.readLine();
			//System.out.println("[CLIENT n°"+ this.idUser +"] : "+msg);
			logger.info("Message received");
		} catch (SocketException exp){
			System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
			try {
				socket.close();
				logger.info("Closing socket and ask disconnection with /c");
				return "/c";
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		return msgReceived;
	}
	
	//Envoie un message à tous les utilisateurs
	public void broadcast(String msgToBroadcast) {
		ArrayList<ConnectionClient> listCo = server.getListCo(); //Liste contenant toutes les connexion de client au serveur
		this.db.insertMsg(msgToBroadcast, this.infoUser.getNickname(), "Broadcast");
		for (int i=0 ; i<listCo.size() ; i++) {
			Socket socketBroadcast = listCo.get(i).socket;
				if(!socketBroadcast.equals(socket)){ //On ne broadcast pas sur l'utilisateur qui envoie le message
				try {
					PrintStream ps = new PrintStream(socketBroadcast.getOutputStream());
					ps.println("/b ["+this.infoUser.getNickname()+"] : "+msgToBroadcast);
					logger.info("Sending broadcast message");
				} catch (SocketException exp){
					System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
					logger.error("Connection lost with Client");
					try {
						socket.close();
						this.disconnectInDB();
						logger.info("Closing socket and disconnecting from DB");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (IOException exp) {
					exp.printStackTrace();
				}
			}
		}
		System.out.println("["+this.infoUser.getNickname()+"] broadcast : "+msgToBroadcast);
	}
	
	public void broadcastServer(String msgToBroadcast) {
		ArrayList<ConnectionClient> listCo = server.getListCo(); //Liste contenant toutes les connexion de client au serveur
		
		for (int i=0 ; i<listCo.size() ; i++) {
			Socket socketBroadcast = listCo.get(i).socket;
			try {
				PrintStream ps = new PrintStream(socketBroadcast.getOutputStream());
				ps.println("/b [SERVER] : "+msgToBroadcast);
				logger.info("Broadcast server message");
			} catch (SocketException exp){
				System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
				logger.error("Connection lost with Client");
				try {
					socket.close();
					this.disconnectInDB();
					logger.info("Closing socket and disconnecting from DB");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException exp) {
				exp.printStackTrace();
			}
		}
		System.out.println("[SERVER] broadcast : "+msgToBroadcast);
	}
	
	
	public void sendHistoryBroacast(){
		ArrayList <InfoMessage> listMsg = this.db.getHistoryBroadcast();
		String msgToSend = "/history Broadcast ";
		for(InfoMessage message : listMsg){
			if(this.infoUser.getNickname().equals(message.getSender())){
				msgToSend = msgToSend.concat("[ME] : ");
			}
			else{
				msgToSend = msgToSend.concat("["+message.getSender()+"] : ");
			}
			msgToSend = msgToSend.concat(message.getMsg()+"|||||");
		}
		this.sendMessage(msgToSend);
	}
	
	
	//Recupére l'objet InfoUser contenant toutes les info de l'utilisateur
	public InfoUser getInfoUser(){
		logger.info("Retrieving infoUser");
		return this.infoUser;
	}
	
	//Vérifie les informations de connnexion de l'utilisateur NORMAL
	public void connectionUser(String nickname){
		this.infoUser = db.getUserConnection(nickname);
		if (this.infoUser.isBanned()){
			System.out.println("[SERVER] : User banned ["+this.infoUser.getNickname()+"] tried to connect on [CLIENT"+this.idUser+"].");
			this.sendMessage("userBanned");
			logger.info("User is banned");
		}
		else if (this.infoUser.getStatus() == 6){
			System.out.println("[SERVER] : Tried to connect with an admin pseudo ["+this.infoUser.getNickname()+"] as a guest on [CLIENT"+this.idUser+"].");
			this.sendMessage("usedByAdmin");
			logger.info("User is Admin");
		}
		else if (this.infoUser.getStatus() == 7){
			System.out.println("[SERVER] : User tried to connect as an already connected user ["+this.infoUser.getNickname()+"] as a guest on [CLIENT"+this.idUser+"].");
			this.sendMessage("userAlreadyConnected");
			logger.info("User is already connected");
		}
		else {
			this.sendMessage("connectionUserGranted");
			this.updateGui.start(); //Lancement du Thread de mise à jour de l'IHM
			System.out.println("[SERVER] : User connected as ["+this.infoUser.getNickname()+"] on [CLIENT"+this.idUser+"].");
			logger.info("User is granted to connect");
		}
	}
	
	//Vérifie les informations de connexion de l'utilisateur ADMIN
	public void connectionAdmin(String nickname, String password){
		if (this.db.getConnectedAdmin(nickname) == false) {
			this.infoUser = db.getAdminConnection(nickname, password);
			if(this.infoUser.isAdmin()){
				this.sendMessage("connectionAdminGranted");
				this.updateGui.start();//Lancement du Thread de mise à jour de l'IHM
				System.out.println("[SERVER] : Admin connected as ["+this.infoUser.getNickname()+"] on [CLIENT"+this.idUser+"].");
				logger.info("Admin connected");
			}
			else{
				System.out.println("[SERVER] : Tried to connected as Admin ["+this.infoUser.getNickname()+"] on [CLIENT"+this.idUser+"].");
				this.sendMessage("");
				logger.info("Try to connect as an admin");
			}
		}
		else{
			System.out.println("[SERVER] : User tried to connect as an already connected admin ["+this.infoUser.getNickname()+"] on [CLIENT"+this.idUser+"].");
			this.sendMessage("userAlreadyConnected");
			logger.info("Admin is already connected");
		}
	}
	
	//Déconnecte l'utilisateur de la BDD
	public void disconnectInDB(){
		this.db.setStatus(this.infoUser.getNickname(), this.infoUser.isAdmin(), 4);
		logger.info("User disconnected from DB");
	}
	
	//Notifie l'utilisateur
	public int notifieUser(String nicknameNotified){
		int nbNotif = 0;
		this.db.addNotification(nicknameNotified);
		nbNotif = this.db.getNotifications(nicknameNotified);
		this.broadcastServer("User ["+nicknameNotified+"] has been NOTIFIED.");
		logger.info("User notified");
		return nbNotif;		
	}
}
