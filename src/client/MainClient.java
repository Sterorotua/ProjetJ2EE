package client;

import java.net.Socket;

public class MainClient {

	public static void main(String[] args) {

		String msgReceived = "";

		Client client = new Client();
		ClientGUI cg = new ClientGUI(client);

		cg.getTextArea().append("\n[ME] : Asking server for connexion...");
		Socket socket = client.Connect(1984);
		
		cg.enablingWriting(true);
		
		msgReceived = client.receptionMessage(socket);
		cg.getTextArea().append("\n"+msgReceived);

		while (!cg.msgSent.equals("quit")) {
			msgReceived = client.receptionMessage(socket);
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
