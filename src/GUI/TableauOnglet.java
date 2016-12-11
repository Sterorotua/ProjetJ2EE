package GUI;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class TableauOnglet extends JTabbedPane {
	public Onglet broadcast=null;
	
	TableauOnglet(){
		super(SwingConstants.TOP);
		
		broadcast = new Onglet();
		
		this.addTab("Broadcast", broadcast);
		
	}

	public void addPrivate(String pseudo){
		
		Onglet messagePrivate = new Onglet();
		
		this.addTab(pseudo, messagePrivate);
	}
	

	
}

	