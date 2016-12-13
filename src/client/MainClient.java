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
			else if (msgReceived != null && msgReceived.equals("usedByAdmin")){
				JOptionPane.showMessageDialog(null,"An admin is already named "+client.getNickname()+".\nChoose an other nickname.", "Error Nickname",JOptionPane.WARNING_MESSAGE);
			}
			else if(msgReceived != null && msgReceived.equals("userBanned")){
				JOptionPane.showMessageDialog(null,"Can't connect as "+client.getNickname()+".\nThis user is banned.", "User Banned",JOptionPane.WARNING_MESSAGE);
			}
			else if (msgReceived != null && msgReceived.equals("userAlreadyConnected")){
				JOptionPane.showMessageDialog(null,"User "+client.getNickname()+" is already connected.", "User already connected",JOptionPane.WARNING_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null,"Can't connect as "+client.getNickname()+".", "Error Login",JOptionPane.INFORMATION_MESSAGE);
			}	
		}
		
		
		UserGUI userGUI = new UserGUI(client);			

		scanner : while (true) {
			msgReceived = client.receiveMessage();
			System.out.println(msgReceived);
			StringTokenizer st = new StringTokenizer(msgReceived);
			String cmd = st.nextToken();

			msgReceived = msgReceived.replace(cmd+" ", "");
			
			switch(cmd) {
				case "/b" : Onglet ongletBroad = (Onglet) userGUI.getOnglets().getComponentAt(0);
							ongletBroad.getReadMessageArea().append("\n"+msgReceived);
							break;
							
				case "/w" : HashMap<String,Onglet> listPM = userGUI.getOnglets().getListTabs();
							String sender = st.nextToken();
							msgReceived = msgReceived.replace(sender+" ", "");
							if(listPM.get(sender) == null){
								userGUI.getOnglets().addPrivate(sender);
							}
							Onglet ongletPriv = listPM.get(sender);
							ongletPriv.getReadMessageArea().append("\n"+msgReceived);
							break;
							
				case "/banned" :client.sendMessage("/banned");
								JOptionPane.showMessageDialog(null,"You have been BANNED.", "Banned",JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
								break;
								
				case "/kicked" :client.sendMessage("/kicked");
								JOptionPane.showMessageDialog(null,"You have been KICKED.", "Kicked",JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
								break;
				
				case "/updIHM" : String[] toUpdate = msgReceived.split("/banned");
							userGUI.updConnectedList(toUpdate[0]);
							if (client.getAdmin()){
								toUpdate = toUpdate[1].split("/notified");
								userGUI.updBannedList(toUpdate[0]);
								userGUI.updNotifiedList(toUpdate[1]); 
							}
							
							/*else {
								userGUI.clearBannedList();
							}*/

							//userGUI.updConnectedList(msgReceived);
							break;
				
				default : System.out.println("normal message : "+msgReceived);
			}
		}
	}
}
