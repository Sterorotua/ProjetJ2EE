package GUI;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MenuDeroulant extends JPopupMenu{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 
	MenuDeroulant(){
		super();
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent event) {	
		        if(event.getActionCommand()=="Notify for bad language"){
		        	// Notifier pour mauvais language - A implementer
            		System.out.println("bad language");
		        }
		        else if(event.getActionCommand()=="Private Message"){
		        	// Lancer une nouvelle conversation priv�e - A implementer
            		System.out.println("message priv�");
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

		    this.setLabel("Justification");
		    
		   
		    
		    

		   }
}
