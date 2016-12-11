package GUI;

import client.Client;

public class MainTest {

	public static void main(String[] args) {

		Client client = new Client();
		//LoginGUI login = new LoginGUI(client);
		UserGUI User = new UserGUI(client);
		
		
	}
}
