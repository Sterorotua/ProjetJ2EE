package server;

import java.util.ArrayList;

import database.Database;

public class UpdateGUI extends Thread{

	ConnectionClient connectionClient;
	Database db;
	ArrayList<InfoUser>  listInfoUsers;
	ArrayList<InfoUser>  listInfoAdmins;
	ArrayList<InfoUser>  listInfoUsersBanned;
	ArrayList<InfoUser>  listInfoUsersNotified;

	
	public UpdateGUI(ConnectionClient connectionClient, Database db){
		this.connectionClient = connectionClient;
		this.db = db;
	}
	
	public void run(){
		while(true){

			String usersConnected = "";
			listInfoUsers = this.db.getConnectedUsers();
			
			for(InfoUser infoUser : listInfoUsers){
				usersConnected = usersConnected.concat(" "+infoUser.getNickname());
			}
			
			listInfoAdmins = this.db.getConnectedAdmins();
			for(InfoUser infoAdmin : listInfoAdmins){
				usersConnected = usersConnected.concat(" "+infoAdmin.getNickname());
			}	
			
			String usersBanned = "/banned";
			if (this.connectionClient.infoUser.isAdmin()){
				listInfoUsersBanned = this.db.getBannedUsers();
				
				for(InfoUser infoUserBanned : listInfoUsersBanned){
					usersBanned = usersBanned.concat(" "+infoUserBanned.getNickname());
				}
			}
			
			String usersNotified = "/notified";
			if (this.connectionClient.infoUser.isAdmin()){
				listInfoUsersNotified = this.db.getNotifiedUsers();

				for(InfoUser infoUserNotified : listInfoUsersNotified){
					usersNotified = usersNotified.concat(" "+infoUserNotified.getNickname());
				}
			}
			
			String msgIHM = "/updIHM";
			msgIHM = msgIHM.concat(usersConnected);
			if (this.connectionClient.infoUser.isAdmin()){
				msgIHM = msgIHM.concat(" "+usersBanned);
				msgIHM = msgIHM.concat(" "+usersNotified);
			}
			System.out.println(msgIHM);

			connectionClient.sendMessage(msgIHM);

			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
