package client;

import java.net.Socket;

public class TestClient {

	public static void main(String[] args){
		String msg = "";
		
		Client c1 = new Client();
		Socket socket = c1.Connect(1984);
		
		while (!msg.equals("quit")){
			msg = c1.envoiMessage(socket);	
			c1.receptionMessage(socket);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c1.finalize();	
	}
}
