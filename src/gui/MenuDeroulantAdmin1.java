package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MenuDeroulantAdmin1 extends JPopupMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MenuDeroulantAdmin1 menu;
	private String nickname;
	private boolean isAdmin;
	private HashMap<String,Onglet> listOnglets;
	
	MenuDeroulantAdmin1(UserGUI userGUI){
		super();
		this.menu = this;
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent event) {	
		        if(event.getActionCommand()=="Kick this User"){
		        	userGUI.getClient().sendMessage("/kick "+nickname);
		        }
		        else if(event.getActionCommand()=="Ban this User"){
		        	userGUI.getClient().sendMessage("/ban "+nickname);        		
		        }
		        else if(event.getActionCommand()=="Private Message"){
		        	listOnglets = userGUI.getOnglets().getListTabs();
		        	Onglet onglet = listOnglets.get(nickname);
		        	if(onglet == null){
			        	userGUI.getOnglets().addPrivate(nickname);
		        	}
		        	userGUI.getOnglets().getListTabs().get(nickname).getHistory(nickname);
		        }	
		        menu.setVisible(false);
		      }
		    };
		    
		    JMenuItem privateMessage=null;
		    this.add(privateMessage = new JMenuItem("Private Message"));
		    privateMessage.setHorizontalTextPosition(JMenuItem.RIGHT);
		    privateMessage.addActionListener(menuListener);		    
		    
		    
		    
		   // if(userGUI.getUser(nickname).isAdmin()){
			    this.addSeparator();		    
			    
			    JMenuItem kickUser=null;
			    this.add(kickUser = new JMenuItem("Kick this User"));
			    kickUser.setHorizontalTextPosition(JMenuItem.RIGHT);
			    kickUser.addActionListener(menuListener);
			    
			    JMenuItem banUser=null;
			    this.add(banUser = new JMenuItem("Ban this User"));
			    banUser.setHorizontalTextPosition(JMenuItem.RIGHT);
			    banUser.addActionListener(menuListener);
		  //  }
		 
   

		   }
	public void setUserClicked(String nickname){
		String[] split = nickname.split(" ");
		this.nickname = split[0];
	}
}

