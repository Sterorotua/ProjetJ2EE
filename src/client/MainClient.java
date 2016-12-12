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
		
		//Réception de la vérification des info de connexion
		while(connectionGranted == false){
			msgReceived = client.receiveMessage();
			System.out.println(msgReceived);
			if(msgReceived != null && msgReceived.equals("connectionUserGranted")) {
				connectionGranted = true;
				lg.setVisible(false);
				lg.dispose();
			}
			else if(msgReceived != null && msgReceived.equals("connectionAdminGranted")){
				connectionGranted = true;
				client.setAdmin(true);
				lg.setVisible(false);
				lg.dispose();
			}
			else if(msgReceived != null && msgReceived.equals("userBanned")){
				JOptionPane.showMessageDialog(null,"Can't connect as "+client.getNickname()+".\nThis user is banned.", "User Banned",JOptionPane.WARNING_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null,"Can't connect as "+client.getNickname()+".", "Error Login",JOptionPane.INFORMATION_MESSAGE);
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
							break;
				
				case "/updListUsers" : userGUI.updConnectedList(msgReceived);
							break;
				
				default : System.out.println("normal message : "+msgReceived);
			}
		}
	}
}
