package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MenuDeroulantAdmin2 extends JPopupMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MenuDeroulantAdmin2 menu;
	String nickname;

	MenuDeroulantAdmin2(UserGUI userGUI){
		super();
		this.menu = this;
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent event) {	
		        if(event.getActionCommand()=="Authorize this user"){
		        	userGUI.getClient().sendMessage("/authorize "+nickname);
		        }	
		        menu.setVisible(false);
		      }
		    };
		    
		    JMenuItem authorize=null;
		    this.add(authorize = new JMenuItem("Authorize this user"));
		    authorize.setHorizontalTextPosition(JMenuItem.RIGHT);
		    authorize.addActionListener(menuListener);		    
		   
		    
		    

		   }
	public void setUserClicked(String nickname){
		String[] split = nickname.split(" ");
		this.nickname = split[0];	}
}


