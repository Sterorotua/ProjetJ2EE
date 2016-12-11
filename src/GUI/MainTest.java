package GUI;

import client.Client;

public class MainTest {

	public static void main(String[] args) {

		Client client = new Client();
		//LoginGUI login = new LoginGUI(client);
		//AdminGUI admin = new AdminGUI(client);
		UserGUI user = new UserGUI(client);
		
	}
}
