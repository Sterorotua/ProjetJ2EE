package client;

import javax.swing.JOptionPane;

public class MainClient {

	public static void main(String[] args) {

		String msgReceived = "";
		boolean connectionGranted = false;

		Client client = new Client();
		LoginGUI lg = new LoginGUI(client);

		client.connect();
						
		while(connectionGranted == false){
			msgReceived = client.receiveMessage();
			if(!msgReceived.equals("connectionGranted")) {
				JOptionPane.showMessageDialog(null,"Error Login.", "Can't connect as "+client.getNickname(),JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null,"Connected as "+client.getNickname()+".", "Connected",JOptionPane.INFORMATION_MESSAGE);
				connectionGranted = true;
				lg.setVisible(false);
				lg.dispose();
			}	
		}
		
		ClientGUI cg = new ClientGUI(client);
		

		while (!cg.msgSent.equals("/q")) {
			msgReceived = client.receiveMessage();
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
