package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args){
		
		try{
			Socket socket = new Socket(InetAddress.getLocalHost(),1984);
			System.out.println("[Client] Demande de connexion");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("[Client] "+br.readLine());
			
			Scanner sc = new Scanner(System.in);
			String str = sc.nextLine();
			
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(str);
			
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}