package client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import gui.Onglet;
import gui.UserGUI;

public class ClientProcess extends Thread{

	Client client;
	UserGUI userGUI;
	String msgReceived = "";
	
	public ClientProcess(Client client){
		this.client = client;
	}
	
	public void run(){
		
		this.userGUI = new UserGUI(this.client);			

		scanner : while (true) {
			msgReceived = this.client.receiveMessage();
			System.out.println(msgReceived);
			StringTokenizer st = new StringTokenizer(msgReceived);
			String cmd = st.nextToken();

			msgReceived = msgReceived.replace(cmd+" ", "");
			
			switch(cmd) {
				//Réception d'un message broadcast
				case "/b" : Onglet ongletBroad = (Onglet) this.userGUI.getOnglets().getComponentAt(0);
							ongletBroad.getReadMessageArea().append("\n"+msgReceived);
							break;
							
				//Réception d'un message privé
				case "/w" : HashMap<String,Onglet> listPM = this.userGUI.getOnglets().getListTabs();
							String sender = st.nextToken();
							msgReceived = msgReceived.replace(sender+" ", "");
							if(listPM.get(sender) == null){
								this.userGUI.getOnglets().addPrivate(sender);
							}
							Onglet ongletPriv = listPM.get(sender);
							ongletPriv.getReadMessageArea().append("\n"+msgReceived);
							break;
							
				//Deconnexion apres reception d'un ban
				case "/banned" :this.client.sendMessage("/banned");
								JOptionPane.showMessageDialog(null,"You have been BANNED.", "Banned",JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
								break;
								
				//Deconnexion apres reception d'un kick
				case "/kicked" :this.client.sendMessage("/kicked");
								JOptionPane.showMessageDialog(null,"You have been KICKED.", "Kicked",JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
								break;
				
				//Ordre de mettre à jour l'IHM
				case "/updIHM" : ArrayList<InfoUser> listUsers = new ArrayList<InfoUser>();
								 String[] toUpdate = msgReceived.split("/status ");
								 String listCo = toUpdate[0];
								 System.out.println(listCo);
								 StringTokenizer stUpd = new StringTokenizer(listCo);
								 while(stUpd.hasMoreTokens()){
									 InfoUser user = new InfoUser();
									 user.setNickname(stUpd.nextToken());
									 listUsers.add(user);
								 }
				
								 if (!this.client.getAdmin()){
									 String listStatus = toUpdate[1];
									 stUpd = new StringTokenizer(listStatus);
									 int i = 0;
									 while(stUpd.hasMoreTokens()){
										 listUsers.get(i).setStatus(Integer.parseInt(stUpd.nextToken()));
										 i++;
									 }
								 }				 
								 else{
									toUpdate = toUpdate[1].split("/banned ");
									String listStatus = toUpdate[0];
									stUpd = new StringTokenizer(listStatus);
									int i = 0;
									while(stUpd.hasMoreTokens()){
										listUsers.get(i).setStatus(Integer.parseInt(stUpd.nextToken()));
										i++;
									}
									toUpdate = toUpdate[1].split("/notified");
									String listBanned = toUpdate[0];
									userGUI.updBannedList(listBanned);
									
									String listNotified = toUpdate[1];
									userGUI.updNotifiedList(listNotified);
								 }
								 
								 this.userGUI.updConnectedList(listUsers);

							break;
				
				default : System.out.println("normal message : "+msgReceived);
						  break;
			}
		}
	}
}
