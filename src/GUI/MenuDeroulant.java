package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MenuDeroulant extends JPopupMenu{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 
	MenuDeroulant(String nickname){
		super(nickname);
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent event) {	
		        if(event.getActionCommand()=="Notify for bad language"){
		        	// Notifier pour mauvais language - A implementer
            		System.out.println("bad language :" + nickname);
		        }
		        else if(event.getActionCommand()=="Private Message"){
		        	// Lancer une nouvelle conversation privée - A implementer
            		System.out.println("message privé :" + nickname);
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
}
