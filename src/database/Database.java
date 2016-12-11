package database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

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
	static final String PASS = "root";

	/* Function which add an user to the DB when it connects */
	public Database() {
		// STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addAdmin (String nickname, String password) {
		String sql;
		sql = "INSERT INTO admins (nickname,password) VALUES ('" + nickname + "','" + password + "')";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean getAdmin (String nickname, String password){
		String sql;
		boolean valid = false;
		sql = "SELECT Count(*) AS valid FROM admins WHERE nickname='"+nickname.trim()+"' AND password='"+password.trim()+"'";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt("valid") != 0){
					valid = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(valid);

		return valid;
	}
	
	public void addUser(String nickname) {
		String sql;
		sql = "INSERT INTO users (nickname) VALUE (\"" + nickname + "\")";
		try {
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getNotifications (String nickname) {
		String sql;
		int notifications = -1;
		sql = "SELECT notifications FROM users WHERE nickname = \"" + nickname + "\"";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					notifications = rs.getInt(i);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return notifications;
	}
	
	/* Function which return the status of the given user */
	public String getStatus(String nickname) {
		String sql;
		String status = "";
		sql = "SELECT value FROM status_ref sr,users u WHERE sr.status_ref_id = u.status_id AND nickname = \""
				+ nickname + "\"";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					status = rs.getString(i);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (status.isEmpty()) {
			//logger.error("Status vide");
		}
		return status;
	}
	
	/* Function which notify the given user */
	public void notifyUser(String nickname) {
		String sql;
		sql = "UPDATE users SET notifications = notifications + 1 WHERE nickname =\"" + nickname + "\"";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/* Function which remove an Administrator from the database */
	public void removeAdmin(String nickname) {
		
		if ( nickname.equalsIgnoreCase("Administrator")) {
			//logger.error("Can't delete Administrator");
		}
		String sql;
		sql = "DELETE FROM admins WHERE nickname = \"" + nickname + "\"";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/* Function which update a user status */
	public void setStatus(String nickname, String Status) {
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		myMap.put("Online", 1);
		myMap.put("Busy", 2);
		myMap.put("Absent", 3);
		myMap.put("Offline", 4);
		myMap.put("Banned", 5);
		int status_id = myMap.get(Status);

		String sql;
		sql = "UPDATE users SET status_id = " + status_id + " WHERE nickname = \"" + nickname + "\"";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	
	public static void main(String[] args) {
		//logger.error("Status vide");
		//logger.debug("test");
	}
}
