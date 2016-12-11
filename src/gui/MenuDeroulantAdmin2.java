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
	
	 
	MenuDeroulantAdmin2(String nickname){
		super(nickname);
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent event) {	
		        if(event.getActionCommand()=="Authorize this user"){
		        	// Authorize a user - A implementer
            		System.out.println("Authorize this user :" + nickname);
		        }		        
		      }
		    };
		    
		    JMenuItem authorize=null;
		    this.add(authorize = new JMenuItem("Authorize this user"));
		    authorize.setHorizontalTextPosition(JMenuItem.RIGHT);
		    authorize.addActionListener(menuListener);		    
		   
		    
		    

		   }
}


