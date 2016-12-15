package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MenuDeroulant extends JPopupMenu{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;
	private boolean isAdmin;
	private HashMap<String,Onglet> listOnglets;
	 
	MenuDeroulant(UserGUI userGUI){
		super();
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent event) {	
		        if(event.getActionCommand()=="Notify for bad language"){
            		userGUI.getClient().sendMessage("/n "+nickname);
		        }
		        else if(event.getActionCommand()=="Private Message"){
		        	listOnglets = userGUI.getOnglets().getListTabs();
		        	Onglet onglet = listOnglets.get(nickname);
		        	if(onglet == null){
			        	userGUI.getOnglets().addPrivate(nickname);
		        	}
		        	userGUI.getOnglets().getListTabs().get(nickname).getHistory(nickname);
		        }
		        getComponent().setVisible(false);
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
		String[] split = nickname.split(" ");
		this.nickname = split[0];
	}
	public void setIsAdminUserClicked(boolean isAdmin){
		this.isAdmin = isAdmin;
	}
}
