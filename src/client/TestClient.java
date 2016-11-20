package client;

import java.net.Socket;

public class TestClient {

	public static void main(String[] args){
		Client c1 = new Client();
		Socket socket = c1.Connect(1984);
		c1.Message(socket);	
		c1.finalize();
		
	}
}
