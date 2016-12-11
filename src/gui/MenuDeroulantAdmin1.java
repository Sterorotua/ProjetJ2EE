package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MenuDeroulantAdmin1 extends JPopupMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 
	MenuDeroulantAdmin1(String nickname){
		super(nickname);
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent event) {	
		        if(event.getActionCommand()=="Kick this User"){
		        	// Kick a user - A implementer
        		System.out.println("Kick a user :" + nickname);
		        }
		        else if(event.getActionCommand()=="Ban this User"){
		        	// Ban un user - A implementer
        		System.out.println("ban a user: " + nickname);
        		
		        }
		        else if(event.getActionCommand()=="Private Message"){
		        	// Lancer une nouvelle conversation privée - A implementer
        		System.out.println("message privé: " + nickname);
		        }		        
		      }
		    };
		    
		    JMenuItem privateMessage=null;
		    this.add(privateMessage = new JMenuItem("Private Message"));
		    privateMessage.setHorizontalTextPosition(JMenuItem.RIGHT);
		    privateMessage.addActionListener(menuListener);		    
		    
		    this.addSeparator();		    
		    
		    JMenuItem kickUser=null;
		    this.add(kickUser = new JMenuItem("Kick this User"));
		    kickUser.setHorizontalTextPosition(JMenuItem.RIGHT);
		    kickUser.addActionListener(menuListener);
		    
		    JMenuItem banUser=null;
		    this.add(banUser = new JMenuItem("Ban this User"));
		    banUser.setHorizontalTextPosition(JMenuItem.RIGHT);
		    banUser.addActionListener(menuListener);
		 
		   
		    
		    
		   
		    
		    

		   }
}

