package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import client.Client;

public class TableauOnglet extends JTabbedPane {
	public Onglet broadcast=null;
	private Client client;
	private HashMap<String,Onglet> listTab;
	private TableauOnglet tableauOnglet;
	
	public TableauOnglet(Client client){
		super(SwingConstants.TOP);
		tableauOnglet = this;
		
		this.client = client;
		listTab = new HashMap<String,Onglet>();
		
		
		//création de l'onglet de base Broadcast
		broadcast = new Onglet(this.client, "broadcast");
		this.addTab("Broadcast", broadcast);
		this.setTabComponentAt((this.getComponentCount()-1), new CloseTabPanel("Broadcast", this, false));
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		
		this.listTab.put("Broadcast",broadcast);
		
	}

	//Ajoute un onglet pour les message privé
	public void addPrivate(String nickname){		
		Onglet messagePrivate = new Onglet(this.client, nickname);
		this.addTab(nickname, messagePrivate);
		this.tableauOnglet.setTabComponentAt((this.getComponentCount()-2), new CloseTabPanel(nickname, this, true)); //Ajout de la croix pour supprimer l'onglet
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		
		this.listTab.put(nickname,messagePrivate);//On ajout dans la Map <nom corespondant ; onglet>	
	}
	
	
	
	public HashMap<String,Onglet> getListTabs(){
		return this.listTab;
	}	
}

	