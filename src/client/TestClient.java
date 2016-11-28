package client;

import java.net.Socket;

public class TestClient {

	public static void main(String[] args){
		Client c1 = new Client();
		Socket socket = c1.Connect(1984);
		while (true){
			c1.envoiMessage(socket);	
			if(c1.receptionMessage(socket))
				break;
		}
		c1.finalize();
		
	}
}
