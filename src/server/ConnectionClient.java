package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import database.Database;

public class ConnectionClient extends Thread{

	private Server server;
	private Socket socket;
	private Database db;
	private int idUser;
	InfoUser infoUser;
	UpdateGUI updateGui;
	
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
			try {
				socket.close();
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
											
				//Demande de connexion d'un utilisateur
				case "/g" : this.connectionUser(msg);
							break;
						
				//Demande de connexion d'un admin
				case "/l" : String nickname = st.nextToken();
							msg = msg.replace(nickname+" ", "");
							String password = st.nextToken();
							this.connectionAdmin(nickname, password);
							break;
				
				//Demande d'envoie d'un broadcast
				case "/b" : this.broadcast(msg);
							break;
						
				//Demande d'envoie d'un message privé
				case "/w" : System.out.println("[CLIENT "+ this.idUser +"] - Receiving : "+msg);
							String nicknameReceiver = st.nextToken();
							msg = msg.replace(nicknameReceiver+" ", "");
							this.sendPrivateMessage(msg, nicknameReceiver);
							break;
						
				//Demande d'arret de la connexion
				case "/c" : this.updateGui.stop();
							if (this.infoUser.getStatus() == 1 || this.infoUser.getStatus() == 2 || this.infoUser.getStatus() == 3){
								this.disconnectInDB();
							}
							break scanner;
				
				//Demande de notification d'un utilisateur
				case "/n" : String nicknameNotified = st.nextToken();
							if (this.notifieUser(nicknameNotified) >= 3){
								ConnectionClient coToBan = server.getCo(nicknameNotified);
								coToBan.sendMessage("/banned "+nicknameNotified);
								this.broadcastServer("User ["+nicknameNotified+"] BANNED (has been notified 3 time).");
							}
							break;
				
				case "/banned" : 
							this.db.setBan(this.infoUser.getNickname(), true);
							break;
				
				default : System.out.println("normal message : "+msg);
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
			}
			else {
				ps.println(msgToSend);
			}			
		} catch (SocketException exp){
			System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
			try {
				socket.close();
				this.disconnectInDB();
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
			} catch (SocketException exp){
				System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
				try {
					socket.close();
					this.disconnectInDB();
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
		} catch (SocketException exp){
			System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
			try {
				socket.close();
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
		
		for (int i=0 ; i<listCo.size() ; i++) {
			Socket socketBroadcast = listCo.get(i).socket;
				if(!socketBroadcast.equals(socket)){ //On ne broadcast pas sur l'utilisateur qui envoie le message
				try {
					PrintStream ps = new PrintStream(socketBroadcast.getOutputStream());
					ps.println("/b ["+this.infoUser.getNickname()+"] : "+msgToBroadcast);
				} catch (SocketException exp){
					System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
					try {
						socket.close();
						this.disconnectInDB();
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
			} catch (SocketException exp){
				System.out.println("[SERVER] : Connexion lost with [CLIENT "+ this.idUser +"].");
				try {
					socket.close();
					this.disconnectInDB();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException exp) {
				exp.printStackTrace();
			}
		}
		System.out.println("[SERVER] broadcast : "+msgToBroadcast);
	}
	
	
	//Recupére l'objet InfoUser contenant toutes les info de l'utilisateur
	public InfoUser getInfoUser(){
		return this.infoUser;
	}
	
	//Vérifie les informations de connnexion de l'utilisateur NORMAL
	public void connectionUser(String nickname){
		this.infoUser = db.getUserConnection(nickname);
		if (this.infoUser.isBanned()){
			System.out.println("[SERVER] : User banned ["+this.infoUser.getNickname()+"] tried to connect on [CLIENT"+this.idUser+"].");
			this.sendMessage("userBanned");
		}
		else if (this.infoUser.getStatus() == 6){
			System.out.println("[SERVER] : Tried to connect with an admin pseudo ["+this.infoUser.getNickname()+"] as a guest on [CLIENT"+this.idUser+"].");
			this.sendMessage("usedByAdmin");
		}
		else if (this.infoUser.getStatus() == 7){
			System.out.println("[SERVER] : User tried to connect as an already connected user ["+this.infoUser.getNickname()+"] as a guest on [CLIENT"+this.idUser+"].");
			this.sendMessage("userAlreadyConnected");
		}
		else {
			this.sendMessage("connectionUserGranted");
			this.updateGui.start(); //Lancement du Thread de mise à jour de l'IHM
			System.out.println("[SERVER] : User connected as ["+this.infoUser.getNickname()+"] on [CLIENT"+this.idUser+"].");
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
			}
			else{
				System.out.println("[SERVER] : Tried to connected as Admin ["+this.infoUser.getNickname()+"] on [CLIENT"+this.idUser+"].");
				this.sendMessage("");
			}
		}
		else{
			System.out.println("[SERVER] : User tried to connect as an already connected admin ["+this.infoUser.getNickname()+"] on [CLIENT"+this.idUser+"].");
			this.sendMessage("userAlreadyConnected");
		}
	}
	
	//Déconnecte l'utilisateur de la BDD
	public void disconnectInDB(){
		this.db.setStatus(this.infoUser.getNickname(), this.infoUser.isAdmin(), 4);
	}
	
	//Notifie l'utilisateur
	public int notifieUser(String nicknameNotified){
		int nbNotif = 0;
		this.db.addNotification(nicknameNotified);
		nbNotif = this.db.getNotifications(nicknameNotified);
		this.broadcastServer("User ["+nicknameNotified+"] has been NOTIFIED.");
		return nbNotif;		
	}
}
