package client;

import java.net.Socket;

public class TestClient {

	public static void main(String[] args){
		String msg = "";
		
		Client client = new Client();
		ClientGUI cg = new ClientGUI(client);
		Socket socket = client.Connect(1984);
		
		while (!msg.equals("quit")){
			msg = client.envoiMessage(socket);	
			client.receptionMessage(socket);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.finalize();	
	}
}
