package server;

public class InfoMessage {

	String msg = "";
	String sender = "";
	String receiver = "";
	
	public InfoMessage(){
		
	}
	
	public InfoMessage(String msg, String sender, String receiver){
		this.msg = msg;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public String getMsg(){
		return this.msg;
	}
	public String getSender(){
		return this.sender;
	}
	public String getReceiver(){
		return this.receiver;
	}
}
