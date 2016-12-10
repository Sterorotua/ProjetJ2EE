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
	
	 
	MenuDeroulant(){
		super();
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent event) {
		        System.out.println("Popup menu item ["
		            + event.getActionCommand() + "] was pressed.");
		      }
		    };
		    
		    JMenuItem item=null;
		    this.add(item = new JMenuItem("Private Message"));
		    item.setHorizontalTextPosition(JMenuItem.RIGHT);
		    item.addActionListener(menuListener);
		    this.addSeparator();
		    this.add(item = new JMenuItem("Notify for bad language"));
		    item.setHorizontalTextPosition(JMenuItem.RIGHT);
		    item.addActionListener(menuListener);

		    this.setLabel("Justification");
		    
		    
		    

		   }
}
