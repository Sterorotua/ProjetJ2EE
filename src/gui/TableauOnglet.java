package gui;

import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import client.Client;

public class TableauOnglet extends JTabbedPane {
	public Onglet broadcast=null;
	private Client client;
	private ArrayList<String> listTab;
	
	public TableauOnglet(Client client){
		super(SwingConstants.TOP);
		
		this.client = client;
		listTab = new ArrayList();
		
		broadcast = new Onglet(this.client, "broadcast");
		this.addTab("Broadcast", broadcast);
		this.listTab.add("Broadcast");
	}

	public void addPrivate(String pseudo){
		
		Onglet messagePrivate = new Onglet(this.client, pseudo);
		this.addTab(pseudo, messagePrivate);
		this.listTab.add(pseudo);
	}
	
	public ArrayList getListTabs(){
		return this.listTab;
	}	
}

	