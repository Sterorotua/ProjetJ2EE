package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import server.ConnectionClient;
import server.InfoUser;

public class Database {

	//private static Logger logger = Logger.getLogger(Database.class);

	static Connection conn = null;
	static Statement stmt = null;

	/* DATABASE INFORMATION */
	static final String DB_URL = "jdbc:mysql://localhost/communitisen" 
			+ "?verifyServerCertificate=false"
			+ "&useSSL=false" 
			+ "&requireSSL=false";
	static final String USER = "root";
	static final String PASS = "6Kbxau4*";

	/* Function which add an user to the DB when it connects */
	public Database() {
		// STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("[SERVER] : Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (CommunicationsException ce){
			System.out.println("[SERVER] : Can't connect to the database.");
			System.out.println("[SERVER] : Check your connection with the database and restart the server.");
			while(true);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		System.out.println("[SERVER] : Connected to the database");
	}

	//****************************************************************************************************************************************
	/* Fonctions pour ADMIN */
	
	//*******************************************************
	// Ajoute un admin dans la BDD
	public boolean addAdmin (String nickname, String password) {
		boolean exists = false;
		try {
			if(this.getUserExist(nickname) == false){
				if(this.getAdminExist(nickname) == false){ //Puis on vérifie que le pseudo n'est pas pris par un ADMIN
					String queryAdmin = "INSERT INTO admins (nickname,password) VALUES ('" + nickname + "','" + password + "')";
					stmt.executeUpdate(queryAdmin);
					System.out.println(nickname + " added");
				}
				else {
					System.out.println("[SERVER] : An admin is already named "+nickname+".");
					exists = true;
				}	
			}
			else{
				System.out.println("[SERVER] : A user is already named "+nickname+".");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}
	
	//*******************************************************
	// Enléve l'admin de la BDD
	public void removeAdmin(String nickname) {
		
		if ( nickname.equalsIgnoreCase("Administrator")) {
			//logger.error("Can't delete Administrator");
		}
		String query;
		query = "DELETE FROM admins WHERE nickname = \"" + nickname + "\"";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	//*******************************************************
	//Verifie si le pseudo existe deja dans ADMIN
	public boolean getAdminExist(String nickname){
		boolean exist = false;
		String query = "SELECT Count(*) AS exist FROM admins WHERE nickname='"+nickname.trim()+"'";	
		
		try {
			ResultSet rsAdmin = stmt.executeQuery(query);
			if(rsAdmin.next()){
				if(rsAdmin.getInt("exist") != 0){
					exist = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	//*******************************************************
	// Vérifie les information de connexion de l'admin
	public InfoUser getAdminConnection (String nickname, String password){
		InfoUser user = new InfoUser();
		String query;
		query = "SELECT * FROM admins WHERE nickname='"+nickname.trim()+"' AND password='"+password.trim()+"'";
		try {
			if(this.getConnectedAdmin(nickname) == false){
				ResultSet rs = stmt.executeQuery(query);
				if(rs.next()){
					user.setAdmin(true);
					this.setStatus(nickname, true, 1);
				}
				else {
					user.setAdmin(false);
				}
				user.setNickname(nickname);
				user.setStatus(1);
			}
			else {
				user.setStatus(7);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	//*******************************************************
	//Check si l'admin est déjà connecté
	public boolean getConnectedAdmin(String nickname){
		boolean connected = false;
		String query = "SELECT Count(*) AS connected FROM admins WHERE nickname='"+nickname.trim()+"' AND status=1";	
		
		try {
			ResultSet rsAdmin = stmt.executeQuery(query);
			if(rsAdmin.next()){
				if(rsAdmin.getInt("connected") != 0){
					connected = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connected;
	}
	
	//*******************************************************
	// Récupére tous les admins connectés
	public ArrayList<InfoUser> getConnectedAdmins(){
		String query;
		ArrayList<InfoUser> listInfoUser = new ArrayList<InfoUser> ();
		query = "SELECT * FROM admins WHERE status IN (1,2,3)";
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				InfoUser user = new InfoUser();
				user.setNickname(rs.getString("nickname"));
				user.setStatus(rs.getInt("status"));
				user.setAdmin(true);
				listInfoUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listInfoUser;

	} 
	
	
	//****************************************************************************************************************************************
	/* Fonctions pour USERS */
	
	//*******************************************************
	// Ajoute un utilisateur dans la BDD
	public void addUser(String nickname) {
		String query;
		query = "INSERT INTO users (nickname, status) VALUE ('" + nickname + "', 1)";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//*******************************************************
	//Verifie si le pseudo existe déjà dans USER
	public boolean getUserExist(String nickname){
		boolean exist = false;
		String query = "SELECT Count(*) AS exist FROM users WHERE nickname='"+nickname.trim()+"'";	
		
		try {
			ResultSet rsUser = stmt.executeQuery(query);
			if(rsUser.next()){
				if(rsUser.getInt("exist") != 0){
					exist = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

	//*******************************************************
	// Regarde si le pseudo est déjà pris, sinon l'ajoute en BDD
	public InfoUser getUserConnection(String nickname){
		InfoUser user = new InfoUser();
		String queryUser = "SELECT * FROM users WHERE nickname='" +nickname+ "'";
		try {
			if(this.getAdminExist(nickname) == false){
				if(this.getConnectedUser(nickname) == false){
					ResultSet rsUser = stmt.executeQuery(queryUser);
					if(rsUser.next()){ //Si un user à déjà ce pseudo
						user.setNotifications(rsUser.getInt("notifications"));
						if(rsUser.getBoolean("banned")){
							user.setStatus(5);
							user.setBanned(true);
						}
						else{
							user.setStatus(1);
							this.setStatus(nickname, false, 1);
						}
					}
					else { //Sinon on ajoute le user en BDD
						addUser(nickname);
						user.setNotifications(0);
						user.setStatus(1);
					}
				}
				else{
					user.setStatus(7);
				}
				user.setNickname(nickname);
				user.setAdmin(false);
			}
			else {
				user.setNickname(nickname);
				user.setAdmin(false);
				user.setStatus(6); //Status à 6 n'existe pas en réalité. C'est pour dire que le pseudo est pris par un admin.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	//*******************************************************
	//Verifie si un utilisateur est déjà connecté
	public boolean getConnectedUser(String nickname){
		boolean connected = false;
		String query = "SELECT Count(*) AS connected FROM users WHERE nickname='"+nickname.trim()+"' AND status=1";	
		
		try {
			ResultSet rsUser = stmt.executeQuery(query);
			if(rsUser.next()){
				if(rsUser.getInt("connected") != 0){
					connected = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connected;
	}
	
	//*******************************************************
	//Récupére tous les utilisateurs connectés
	public ArrayList<InfoUser> getConnectedUsers(){
		String query;
		ArrayList<InfoUser> listInfoUser = new ArrayList<InfoUser> ();
		query = "SELECT * FROM users WHERE status IN (1,2,3)";
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				InfoUser user = new InfoUser();
				user.setNickname(rs.getString("nickname"));
				user.setStatus(rs.getInt("status"));
				user.setAdmin(false);
				user.setNotifications(rs.getInt("notifications"));
				listInfoUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listInfoUser;
	}
		
	//*******************************************************
	//Récupére les utilisateur bannis
	public ArrayList<InfoUser> getBannedUsers(){
		ArrayList<InfoUser> listInfoBannedUser = new ArrayList<InfoUser> ();
		String query = "SELECT * FROM users WHERE banned = 1";
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				InfoUser user = new InfoUser();
				user.setNickname(rs.getString("nickname"));
				user.setStatus(rs.getInt("status"));
				user.setAdmin(false);
				user.setNotifications(rs.getInt("notifications"));
				user.setBanned(true);
				listInfoBannedUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listInfoBannedUser;
	}
	
	//*******************************************************
	// Ajoute une notification à l'utilisateur
	public void addNotification(String nickname) {
		String query;
		query = "UPDATE users SET notifications = notifications + 1 WHERE nickname ='" + nickname + "'";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	//*******************************************************
	// Récupére le nombre de notifications d'un utilisateur
	public int getNotifications (String nickname) {
		int notifications = 0;
		String query = "SELECT notifications FROM users WHERE nickname = '" + nickname + "'";
		try {
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				notifications = rs.getInt("notifications");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return notifications;
	}
	
	public void clearNotifications(String nickname){
		String query = "UPDATE users SET notifications = 0 WHERE nickname ='" + nickname + "'";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	//*******************************************************
	//Récupére la liste des utilisateurs notifiés
	public ArrayList<InfoUser> getNotifiedUsers(){
		ArrayList<InfoUser> listInfoNotifiedUser = new ArrayList<InfoUser> ();
		String query = "SELECT * FROM users WHERE notifications > 0";
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				InfoUser user = new InfoUser();
				user.setNickname(rs.getString("nickname"));
				user.setStatus(rs.getInt("status"));
				user.setAdmin(false);
				user.setNotifications(rs.getInt("notifications"));
				user.setBanned(rs.getBoolean("banned"));
				listInfoNotifiedUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listInfoNotifiedUser;
	}
	
	//*******************************************************
	// Récupére le status d'un utilisateur
	public String getStatus(String nickname) {
		String query;
		String status = "";
		query = "SELECT value FROM status_ref sr,users u WHERE sr.status_ref_id = u.status_id AND nickname = \""
				+ nickname + "\"";
		try {
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					status = rs.getString(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (status.isEmpty()) {
			//logger.error("Status vide");
		}
		return status;
	}
	
	//*******************************************************
	// Met à jour le status d'un utilisateur
	public void setStatus(String nickname, String status) {
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		myMap.put("Online", 1);
		myMap.put("Busy", 2);
		myMap.put("Absent", 3);
		myMap.put("Offline", 4);
		int status_id = myMap.get(status);

		String query;
		query = "UPDATE users SET status = " + status_id + " WHERE nickname = \"" + nickname + "\"";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//*******************************************************
	// Met à jour le status d'un utilisateur
	public void setStatus(String nickname, boolean admin, int status) {
		String query;
		if (admin == false){
			query = "UPDATE users SET status = " + status + " WHERE nickname = '" + nickname + "'";
		}
		else{
			query = "UPDATE admins SET status = " + status + " WHERE nickname = '" + nickname + "'";
		}
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//*******************************************************
	//set le ban d'un utilisateur en bdd
	public void setBan(String nickname, boolean  ban){
		String query = "UPDATE users SET banned = " + ban + " WHERE nickname = '" + nickname + "'";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//*******************************************************
	public void finalize() {

		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
		System.out.println("Disconnecting...");
	}
}
