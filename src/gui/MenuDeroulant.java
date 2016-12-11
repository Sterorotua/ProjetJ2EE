package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MenuDeroulant extends JPopupMenu{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;
	private ArrayList <String> listOnglets;
	 
	MenuDeroulant(UserGUI userGUI){
		super();
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent event) {	
		        if(event.getActionCommand()=="Notify for bad language"){
		        	// Notifier pour mauvais language - A implementer
            		System.out.println("bad language :" + nickname);
		        }
		        else if(event.getActionCommand()=="Private Message"){
		        	listOnglets = userGUI.getOnglets().getListTabs();
		        	if(!listOnglets.contains(nickname)){
			        	userGUI.getOnglets().addPrivate(nickname);
		        	}
		        	getComponent().setVisible(false);
		        }
		      }
		    };
		    
		    JMenuItem privateMessage=null;
		    this.add(privateMessage = new JMenuItem("Private Message"));
		    privateMessage.setHorizontalTextPosition(JMenuItem.RIGHT);
		    privateMessage.addActionListener(menuListener);		    
		    
		    this.addSeparator();
		    
		    JMenuItem badLanguage=null;
		    this.add(badLanguage = new JMenuItem("Notify for bad language"));
		    badLanguage.setHorizontalTextPosition(JMenuItem.RIGHT);
		    badLanguage.addActionListener(menuListener);		   		    		    		   		    		    

		   }
	public void setUserClicked(String nickname){
		this.nickname = nickname;
	}
}
