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
	
	// *******************************************************
	// Essaie de se connecter sur le 1er serveur sinon sur le 2eme
	public void connect(int numServer) {
		String serverAddr = this.serversAddr.get(numServer);
		int serverPort = this.serversPort.get(numServer);
		System.out.println("Connecting to "+serverAddr+" on port "+serverPort);
		if(numServer == 0){
			this.server1Tried = true; //A tenté de se connecté au serveur 1
		}
		else {
			this.server2Tried = true; //A tenté de se connecté au serveur 2
		}
		try{
			this.socket = new Socket(serverAddr, serverPort);
			if(numServer == 0){
				this.server1Found = true;
				System.out.println("Connected on "+serverAddr+" on port "+serverPort);
			}
			else {
				this.server2Found = true;
				System.out.println("Connected on "+serverAddr+" on port "+serverPort);
			}		
		} catch (SocketException exp){
			//if(!this.socket.isConnected()){
				if(numServer == 0){ //Si on ne peut pas se co sur le 1er serveur , on essaie le 2nd
					System.out.println("Can't connect on "+serverAddr+" on port "+serverPort);
					if(this.server2Tried == false){
						this.connect(1);
						JOptionPane.showMessageDialog(null,"Connected on server 2.\n", "Connection established",JOptionPane.INFORMATION_MESSAGE);
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
	
	// *******************************************************
	//Choisis le serveur sur lequel se connecter
	public void chooseServer(){
		this.connect(0);
		if(this.server1Found == true){
			this.sendMessage("/nbClient"); //Demande combien de client co
			int nbUserServer1 = Integer.parseInt(this.receiveMessage())-1;
			System.out.println("nbUser server1 = "+nbUserServer1);
			if(nbUserServer1 == 0){
				System.out.println("No other user on "+this.serversAddr.get(0)+" on port "+this.serversPort.get(0));
				try {
					this.sendMessage("/c");
					this.socket.close();
					System.out.println("Disctonnecting from server "+this.serversAddr.get(0)+" on port "+this.serversPort.get(0));
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Trying to connect on server "+this.serversAddr.get(1)+" on port "+this.serversPort.get(1));
				this.connect(1);
				if(this.server2Found == true){
					this.sendMessage("/nbClient");
					int nbUserServer2 = Integer.parseInt(this.receiveMessage())-1;
					if(nbUserServer2 == 0){
						System.out.println("No other user on "+this.serversAddr.get(1)+" on port "+this.serversPort.get(1));
						try {
							this.sendMessage("/c");
							this.socket.close();
							System.out.println("Disctonnecting from server "+this.serversAddr.get(1)+" on port "+this.serversPort.get(1));
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("Going back on server "+this.serversAddr.get(0)+" on port "+this.serversPort.get(0));
						this.connect(0);
						JOptionPane.showMessageDialog(null,"Connected on server 1.\n", "Connection established",JOptionPane.INFORMATION_MESSAGE);
						this.sendMessage("/clearCoDB");
					}
					else{
						JOptionPane.showMessageDialog(null,"Connected on server 2.\n", "Connection established",JOptionPane.INFORMATION_MESSAGE);
						System.out.println("Other users found on server "+this.serversAddr.get(1)+" on port "+this.serversPort.get(1));
						System.out.println("Staying on server "+this.serversAddr.get(1)+" on port "+this.serversPort.get(1));
					}
				}
				else{
					System.out.println("Going back on server "+this.serversAddr.get(0)+" on port "+this.serversPort.get(0));
					this.connect(0);
					JOptionPane.showMessageDialog(null,"Connected on server 1.\n", "Connection established",JOptionPane.INFORMATION_MESSAGE);
					this.sendMessage("/clearCoDB");
				}
			}
			else{
				JOptionPane.showMessageDialog(null,"Connected on server 1.\n", "Connection established",JOptionPane.INFORMATION_MESSAGE);
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