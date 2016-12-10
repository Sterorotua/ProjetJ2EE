package client;

import java.net.Socket;

public class MainClient {

	public static void main(String[] args) {

		String msgReceived;
		String serverAddr;
		int port = 1984;

		Client client = new Client();
		LoginGUI lg = new LoginGUI(client);
		ClientGUI cg = new ClientGUI(client);
		ClientConfig conf = new ClientConfig();
		
		serverAddr = conf.getServerAddr();

		cg.getTextArea().append("\n[ME] : Asking server for connexion...");
		Socket socket = client.Connect(serverAddr, port);
		
		cg.enablingWriting(true);
		
		msgReceived = client.receiveMessage(socket);
		cg.getTextArea().append("\n"+msgReceived);

		while (!cg.msgSent.equals("quit")) {
			msgReceived = client.receiveMessage(socket);
			cg.getTextArea().append("\n"+msgReceived);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.finalize();
		System.exit(0);

	}
}
