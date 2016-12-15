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
		LoginGUI loginGUI = new LoginGUI(client);
		ClientProcess clientProcess;

		client.chooseServer();
		
		//R�ception de la v�rification des info de connexion
		while(connectionGranted == false){
			msgReceived = client.receiveMessage();
			System.out.println(msgReceived);
			if(msgReceived != null && msgReceived.equals("connectionUserGranted")) {
				connectionGranted = true;
				loginGUI.setVisible(false);
				loginGUI.dispose();
			}
			else if(msgReceived != null && msgReceived.equals("connectionAdminGranted")){
				connectionGranted = true;
				client.setAdmin(true);
				loginGUI.setVisible(false);
				loginGUI.dispose();
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
		
		//Thread qui lance l'IHM et g�re les commandes re�ues
		clientProcess = new ClientProcess(client);
		clientProcess.start();
		
		
	}
}
