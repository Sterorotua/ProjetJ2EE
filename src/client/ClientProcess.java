package client;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import gui.LoginGUI;
import gui.Onglet;
import gui.UserGUI;

public class ClientProcess extends Thread{

	Client client;
	String msgReceived = "";
	
	public ClientProcess(Client client){
		this.client = client;
	}
	
	public void run(){
		
		UserGUI userGUI = new UserGUI(this.client);			

		scanner : while (true) {
			msgReceived = this.client.receiveMessage();
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
							
				case "/banned" :this.client.sendMessage("/banned");
								JOptionPane.showMessageDialog(null,"You have been BANNED.", "Banned",JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
								break;
								
				case "/kicked" :this.client.sendMessage("/kicked");
								JOptionPane.showMessageDialog(null,"You have been KICKED.", "Kicked",JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
								break;
				
				case "/updIHM" : String[] toUpdate = msgReceived.split("/banned");
							userGUI.updConnectedList(toUpdate[0]);
							if (this.client.getAdmin()){
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
