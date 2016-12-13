package database;

import server.InfoUser;

public class MainData {

	public static void main(String[] args) {
		Database db = new Database();
		

		db.addAdmin("jordan", "aze");
		db.addAdmin("aze", "aze");
		db.addAdmin("remi", "aze");

		
		InfoUser user = db.getUserConnection("test");
		
		System.out.println(user.getNickname()+" - "+user.getStatus()+" - "+user.getNotifications()+" - "+user.isAdmin());
	}
}
