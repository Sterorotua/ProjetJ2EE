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
	private String nicknameUser;
	private boolean admin;
	
	ConnectionClient(Server server, Socket socket, Database db, int idUser){
		this.server = server;
		this.socket = socket;
		this.idUser = idUser;
		this.admin = false;
		this.db = db;
	}
	
	public void run(){
		PrintStream ps;
		
		this.server.addClient();
		
		try {
			//ps = new PrintStream(socket.getOutputStream());
			//ps.println("[SERVER] : You are connected on the server as [CLIENT n°"+ this.idUser +"]");
			
			this.processing();
	
			socket.close();
			this.server.removeClient(this);
			
		} catch (SocketException exp){
			System.out.println("[SERVER] : Connexion lost with [CLIENT n°"+ this.idUser +"].");
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public void processing() {
		String msg;
		scanner : while (true) {
			msg = this.receiveMessage();
			
			StringTokenizer st = new StringTokenizer(msg);
			String cmd = st.nextToken();
			msg = msg.replace(cmd+" ", "");
			
			switch(cmd) {
				case "/q" : this.sendMessage("quit");
							System.out.println("[SERVER] : Client disconnected.");
							break;
							  
				case "/g" : this.sendMessage("connectionUserGranted");
							this.nicknameUser = msg;
							System.out.println("pseudo : "+this.nicknameUser);
							break;
							  
				case "/c" : break scanner;
				
				case "/b" : this.broadcast(msg);
							System.out.println("broadcast : "+msg);
							break;
							
				case "/w" : String nicknameReceiver = st.nextToken();
							msg = msg.replace(nicknameReceiver+" ", "");
							this.sendPrivateMessage(msg, nicknameReceiver);
							break;
				
				case "/l" : String login = st.nextToken();
							msg = msg.replace(login+" ", "");
							String password = st.nextToken();
							System.out.println(login);
							System.out.println(password);
							if(db.getAdmin(login, password)){
								this.sendMessage("connectionAdminGranted");
							}
							break;
				
				default : System.out.println("normal message : "+msg);
			}
			
		}
	}
	
	public void sendMessage(String msgToSend){
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
	
			if (msgToSend.equals("quit")){
				System.out.println("[SERVER] : Sending GoodBye to [CLIENT n°"+ this.idUser +"].");
				ps.println("[SERVER] : Message recu : ADIOS");
			}
			else {
				System.out.println("[SERVER] : Sending a message to [CLIENT n°"+ this.idUser +"].");
				ps.println(msgToSend);
			}			
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public void sendPrivateMessage(String msgToSend, String nicknameReceiver) {
		ConnectionClient coReceiver = server.getCo(nicknameReceiver);
		if (coReceiver == null){
			this.sendMessage(nicknameReceiver+ "unknown");
		}
		else {
			try {
				PrintStream ps = new PrintStream(coReceiver.socket.getOutputStream());
				ps.println("/w ["+this.nicknameUser+"] : "+msgToSend);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String receiveMessage(){
		String msgReceived = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			msgReceived = br.readLine();
			//System.out.println("[CLIENT n°"+ this.idUser +"] : "+msg);
		} catch (SocketException exp){
			System.out.println("[SERVER] : Connexion lost with [CLIENT n°"+ this.idUser +"].");
			try {
				socket.close();
				return "/c";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		return msgReceived;
	}
	
	public void broadcast(String msgToBroadcast) {
		ArrayList<ConnectionClient> listCo = server.getListCo();
		
		for (int i=0 ; i<listCo.size() ; i++) {
			Socket socketBroadcast = listCo.get(i).socket;
				if(!socketBroadcast.equals(socket)){
				try {
					PrintStream ps = new PrintStream(socketBroadcast.getOutputStream());
					ps.println("/b ["+this.nicknameUser+"] : "+msgToBroadcast);
				} catch (IOException exp) {
					exp.printStackTrace();
				}
			}
		}
	}
	
	public String getNickname(){
		return this.nicknameUser;
	}
	
}
