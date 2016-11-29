package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
	
	Socket socket = null;
	String serverAddr = "172.20.10.4";
	
	public Socket Connect(int port) {
		try{
			socket = new Socket(InetAddress.getLocalHost(),port);
			System.out.println("[Client] : Asking for connexion...");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(br.readLine());
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		return socket;
	}
	
	public String envoiMessage(Socket socket){
		String msg = "";
		try{
			Scanner sc = new Scanner(System.in);
			msg = sc.nextLine();
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(msg);
		} catch (SocketException exp){
			System.out.println("[Server] Perte de la connexion avec le Server.");
			try {
				socket.close();	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		return msg;
	}
	
	public void receptionMessage(Socket socket){
		String msg = "";
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			msg = br.readLine();
			System.out.println(msg);
		} catch (SocketException exp){
			System.out.println("[Server] Perte de la connexion avec le Server.");
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public void finalize(){
		try{
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}