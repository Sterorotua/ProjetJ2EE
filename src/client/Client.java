package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Client {
	
	private ArrayList <String> serversAddr;
	private ArrayList <Integer> serversPort;
	private Socket socket;
	private boolean server1Tried;
	private boolean server2Tried;
	private boolean server1Found;
	private boolean server2Found;
	private boolean admin;
	
	private String nickname;

	public Client(){
		ClientConfig conf = new ClientConfig();
		this.serversAddr = conf.getServersAddr();
		this.serversPort = conf.getServersPort();
		this.socket = new Socket();
		this.admin = false;
	}
	
	public void connect(int numServer) {
		String serverAddr = this.serversAddr.get(numServer);
		int serverPort = this.serversPort.get(numServer);
		System.out.println("Connecting to "+serverAddr+" on port "+serverPort);
		if(numServer == 0){
			this.server1Tried = true;
			System.out.println("server1Tried");
		}
		else {
			this.server2Tried = true;
			System.out.println("server2Tried");
		}
		try{
			this.socket = new Socket(serverAddr, serverPort);
			if(numServer == 0){
				this.server1Found = true;
				System.out.println("server1Found");
			}
			else {
				this.server2Found = true;
				System.out.println("server2Found");
			}		
		} catch (SocketException exp){
			//if(!this.socket.isConnected()){
				if(numServer == 0){
					if(this.server2Tried == false){
						System.out.println("Trying server2");
						this.connect(1);
						this.sendMessage("/nbClient");
						if(Integer.parseInt(this.receiveMessage())-1 == 0){
							this.sendMessage("/clearCoDB");
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"Can't established connexion with both servers.\n", "Error",JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				}
				else if((this.server2Tried == true && !this.server2Found) && (this.server1Tried == true && !this.server1Found)){
					JOptionPane.showMessageDialog(null,"Can't established connexion with both servers.\n", "Error",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}

			//}
			/*else {
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
	
	public void chooseServer(){
		this.connect(0);
		if(this.server1Found == true){
			this.sendMessage("/nbClient");
			int nbUserServer1 = Integer.parseInt(this.receiveMessage())-1;
			System.out.println("nbUser server1 = "+nbUserServer1);
			if(nbUserServer1 == 0){
				try {
					System.out.println("disconnect from server1");
					this.sendMessage("/c");
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.connect(1);
				if(this.server2Found == true){
					this.sendMessage("/nbClient");
					int nbUserServer2 = Integer.parseInt(this.receiveMessage())-1;
					System.out.println("nbUser server2 = "+nbUserServer2);

					if(nbUserServer2 == 0){
						try {
							System.out.println("disconnect from server2");
							this.sendMessage("/c");
							this.socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						this.connect(0);
						this.sendMessage("/clearCoDB");
					}
				}
				else{
					this.connect(0);
					this.sendMessage("/clearCoDB");
				}
			}
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