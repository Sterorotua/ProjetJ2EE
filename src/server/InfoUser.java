package server;

public class InfoUser {

	private String nickname;
	private int status;
	private int notifications;
	private boolean admin;
	
	public InfoUser(){
	}
	
	public InfoUser(String nickname, int status, int notifications, boolean admin){
		this.nickname = nickname;
		this.status = status;
		this.notifications = notifications;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNotifications() {
		return notifications;
	}

	public void setNotifications(int notifications) {
		this.notifications = notifications;
	}

	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
}
