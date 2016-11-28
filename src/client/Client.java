package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
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
		} catch(IOException e){
			e.printStackTrace();
		}
		return socket;
	}
	
	public void envoiMessage(Socket socket){
		try{
			Scanner sc = new Scanner(System.in);
			String str = sc.nextLine();
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(str);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean receptionMessage(Socket socket){
		String msg = null;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			msg = br.readLine();
			System.out.println(msg);
		}catch(Exception e){
			e.printStackTrace();
		}
		if (msg.equals("quit"))
			return true;
		else
			return false;
	}
	
	public void finalize(){
		try{
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}