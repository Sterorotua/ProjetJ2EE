package server;

import java.util.ArrayList;

import database.Database;

public class UpdateGUI extends Thread{

	ConnectionClient connectionClient;
	Database db;
	ArrayList<InfoUser>  listInfoUsers;
	ArrayList<InfoUser>  listInfoAdmins;

	
	public UpdateGUI(ConnectionClient connectionClient, Database db){
		this.connectionClient = connectionClient;
		this.db = db;
	}
	
	public void run(){
		while(true){
			String usersConnected = "/updListUsers";
			
			listInfoUsers = this.db.getConnectedUsers();
			for(InfoUser infoUser : listInfoUsers){
				usersConnected = usersConnected.concat(" "+infoUser.getNickname());
			}
			
			listInfoAdmins = this.db.getConnectedAdmins();
			for(InfoUser infoAdmin : listInfoAdmins){
				usersConnected = usersConnected.concat(" "+infoAdmin.getNickname());
			}
			
			connectionClient.sendMessage(usersConnected);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
