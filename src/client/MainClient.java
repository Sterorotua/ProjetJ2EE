package client;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import gui.LoginGUI;
import gui.Onglet;
import gui.UserGUI;

public class MainClient {

	public static void main(String[] args) {

		String msgReceived = "";
		boolean connectionGranted = false;

		Client client = new Client();
		LoginGUI lg = new LoginGUI(client);

		client.connect();
						
		while(connectionGranted == false){
			msgReceived = client.receiveMessage();
			if(msgReceived.equals("connectionUserGranted")) {
				connectionGranted = true;
				lg.setVisible(false);
				lg.dispose();
			}
			else if(msgReceived.equals("connectionAdminGranted")){
				connectionGranted = true;
				client.setAdmin(true);
				lg.setVisible(false);
				lg.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null,"Error Login.", "Can't connect as "+client.getNickname(),JOptionPane.INFORMATION_MESSAGE);
			}	
		}
		

		UserGUI userGUI = new UserGUI(client);			

		HashMap listPM = new HashMap();

		while (true) {
			msgReceived = client.receiveMessage();
			
			StringTokenizer st = new StringTokenizer(msgReceived);
			String cmd = st.nextToken();
			msgReceived = msgReceived.replace(cmd+" ", "");
			
			switch(cmd) {
				case "/b" : Onglet ongletBroad = (Onglet) userGUI.getOnglets().getComponentAt(0);
				ongletBroad.getReadMessageArea().append("\n"+msgReceived);
							break;
							
				case "/w" : if(listPM.get("") == null){
					userGUI.getOnglets().addPrivate("toto");
					Onglet ongletPriv = (Onglet) userGUI.getOnglets().getComponentAt(1);
					ongletPriv.getReadMessageArea().append("\n"+msgReceived);
								
							}
				default : System.out.println("normal message : "+msgReceived);
			}
		}
	}
}
