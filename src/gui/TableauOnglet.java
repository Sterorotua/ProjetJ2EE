package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import client.Client;

public class TableauOnglet extends JTabbedPane {
	public Onglet broadcast=null;
	private Client client;
	private HashMap<String,Onglet> listTab;
	
	public TableauOnglet(Client client){
		super(SwingConstants.TOP);
		
		this.client = client;
		listTab = new HashMap<String,Onglet>();
		
		broadcast = new Onglet(this.client, "broadcast");
		this.addTab("Broadcast", broadcast);
		this.listTab.put("Broadcast",broadcast);
	}

	public void addPrivate(String pseudo){
		
		Onglet messagePrivate = new Onglet(this.client, pseudo);
		this.addTab(pseudo, messagePrivate);
		this.listTab.put(pseudo,messagePrivate);
	}
	
	public HashMap<String,Onglet> getListTabs(){
		return this.listTab;
	}	
}

	