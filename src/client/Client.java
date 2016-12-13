package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Client {
	
	private String serverAddr;
	private int port;
	private Socket socket;
	private boolean admin;
	
	private String nickname;

	public Client(){
		ClientConfig conf = new ClientConfig();
		this.serverAddr = conf.getServerAddr();
		this.port = conf.getServerPort();
		this.socket = new Socket();
		this.admin = false;
	}
	
	public void connect() {
				
		try{
			socket = new Socket(this.serverAddr, this.port);
		} catch (SocketException exp){
			//if(!this.socket.isConnected()){
				JOptionPane.showMessageDialog(null,"Can't established connexion with server.\n", "Error",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			/*}
			else {
				 Thread t = new Thread(new Runnable(){
				        public void run(){
				            JOptionPane.showMessageDialog(null,"Can't established connexion with server.\n", "Warning",JOptionPane.WARNING_MESSAGE);;
				        }
				    });
				  t.start();
			}*/
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public void sendMessage(String msg){		
		try{
			PrintStream ps = new PrintStream(this.socket.getOutputStream());
			ps.println(msg);
		} catch (SocketException exp){
			JOptionPane.showMessageDialog(null,"Connexion with the server lost.", "Error",JOptionPane.ERROR_MESSAGE);
			try {
				this.socket.close();
				//this.reconnectionAttempt();
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public String receiveMessage(){
		String msg = "";
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			msg = br.readLine();
		} catch (SocketException exp){
			JOptionPane.showMessageDialog(null,"Connexion with the server lost.", "Error",JOptionPane.ERROR_MESSAGE);
			try {
				this.socket.close();
				//this.reconnectionAttempt();
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		return msg;
	}
	
	public boolean getAdmin(){
		return this.admin;
	}
	public void setAdmin(boolean admin){
		this.admin = admin;
	}
	
	/*public void reconnectionAttempt() {
		for(int i=0 ; i<15 ; i++){
			this.connect();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(this.socket.getInputStream().read() != -1){
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			if(this.socket.getInputStream().read() != -1){
				this.sendMessage("reconecte");
			}
			else {
				try {
					this.socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public void finalize(){
		try{
			this.socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}