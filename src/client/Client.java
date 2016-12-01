package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class Client {
	
	Socket socket = null;
	String serverAddr = "172.20.10.4";
	
	public Socket Connect(int port) {
		try{
			socket = new Socket(InetAddress.getLocalHost(),port);
		} catch (SocketException exp){
			JOptionPane.showMessageDialog(null,"C'ant established connexion with server.", "Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		return socket;
	}
	
	public String envoiMessage(Socket socket, String msg){
		try{
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(msg);
		} catch (SocketException exp){
			JOptionPane.showMessageDialog(null,"Connexion with the server lost.", "Error",JOptionPane.ERROR_MESSAGE);
			try {
				socket.close();
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
	
	public String receptionMessage(Socket socket){
		String msg = "";
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			msg = br.readLine();
		} catch (SocketException exp){
			JOptionPane.showMessageDialog(null,"Connexion with the server lost.", "Error",JOptionPane.ERROR_MESSAGE);
			try {
				socket.close();
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
	
	public void finalize(){
		try{
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}