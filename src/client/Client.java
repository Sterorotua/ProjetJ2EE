package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	Socket socket = null;
	
	public Socket Connect(int port) {
		try{
			socket = new Socket(InetAddress.getLocalHost(),port);
			System.out.println("[Client] Asking for connexion...");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("[Client] "+br.readLine());
		} catch(IOException e){
			e.printStackTrace();
		}
		return socket;
	}
	
	public void Message(Socket socket){
		try{
			Scanner sc = new Scanner(System.in);
			String str = sc.nextLine();
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(str);
			sc.close();
		}catch(Exception e){
			e.printStackTrace();
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