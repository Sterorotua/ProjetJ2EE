package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.ListSelectionModel;
import client.Client;

public class UserGUI extends JFrame implements WindowListener, ActionListener, KeyListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TableauOnglet onglets=null;	
	private JList<String> userList = null;
	private JList<String> statusList = null;
	private JButton myStatus = null;		
	public MenuDeroulant popupMenu = null;
	
	private DefaultListModel<String> userConnected = null; // doit �tre li� a la database
	private DefaultListModel<String> status = null; 
	
	private Client client = null;
	
	
	UserGUI(Client client){
		
		
		
		super("Communit'ISEN : Share with your Community !");
		this.client = client;
		this.setSize(700,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		userConnected= new DefaultListModel<String>();
		status = new DefaultListModel<String>();
		userList = new JList<String>(userConnected);
		statusList = new JList<String>(status);
		onglets = new TableauOnglet();
		myStatus = new JButton();
		
		JPanel borderList = new JPanel();
		borderList.setLayout(new BorderLayout());
		
		JPanel borderStatus = new JPanel();
		borderStatus.setLayout(new BorderLayout());
		
		JPanel borderFinal = new JPanel();
		borderFinal.setLayout(new BorderLayout());
		
		//initialisation d'une liste d'utilisateur pour tests
		userConnected.addElement("paul");
		userConnected.addElement("stephane");
		userConnected.addElement("jordan");
		userConnected.addElement("remy");
		
		//Diff�rents status possible
		status.addElement("Available");
		status.addElement("Busy");
		status.addElement("Unavailable");				
		
		// R�glage des listes
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setLayoutOrientation(JList.VERTICAL);
		userList.setVisibleRowCount(-1);
		userList.addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent event){
            	if(event.isPopupTrigger()){
            		 //Only show the list's popup menu if an item is not selected and positioned where the mouse was right-clicked
                    int closestIndexToClick = userList.locationToIndex(event.getPoint());
                    Rectangle cellBounds = userList.getCellBounds(closestIndexToClick, closestIndexToClick); 
                    if( cellBounds != null && !cellBounds.contains(event.getPoint()) ){
                    	
                    	if((popupMenu!=null)){
                    		popupMenu.setVisible(false);
                    		popupMenu=null;
                        }
                    }                        	                   
                    else{
                    	if(popupMenu==null){                    		
                    	
	                    	event.getSource();
	                		Point p = new Point(event.getX(), event.getY());
	                		int indexClick = userList.locationToIndex(p);
	                		popupMenu = new MenuDeroulant(userConnected.getElementAt(indexClick));	                		
	                		popupMenu.setVisible(true);
	                		popupMenu.setLocation(event.getLocationOnScreen());
	                    	borderFinal.addMouseListener(new MouseAdapter(){
	                    		public void mouseReleased(MouseEvent event){		                    			 
	                		        if(event.getComponent()!=popupMenu){
	                		        	if((popupMenu!=null)){
	                		        		popupMenu.setVisible(false);
	                		        		popupMenu=null;
	                                    }	                               		       	     
	                		        }
                    			}
	                    		});
                			}
                    	else{
                			if((popupMenu!=null)){
                			popupMenu.setVisible(false);
                			popupMenu=null;
                			}                        	
                		}
                    }
            	}
                 else{                	
                	 	if((popupMenu!=null)){
                	 		popupMenu.setVisible(false);
                	 		popupMenu=null;
                    	}
                }                                            
            }      
		});
		
		statusList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		statusList.setLayoutOrientation(JList.VERTICAL);
		statusList.setVisibleRowCount(-1);
		statusList.addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent event){
            	if(event.isPopupTrigger());            	
            	else{
            		event.getSource();
            		Point p = new Point(event.getX(), event.getY());
            		int indexClick = statusList.locationToIndex(p);
            		if(indexClick == 0)
            			myStatus.setBackground(java.awt.Color.GREEN);
            		else if(indexClick == 1)
            			myStatus.setBackground(java.awt.Color.YELLOW);
            		else if(indexClick == 2)
            			myStatus.setBackground(java.awt.Color.RED);
            	}
            }
		});
		

		// R�glage couleur bouton
		myStatus.setBackground(java.awt.Color.GREEN);
		
		// Organisation des panneaux
		
		JScrollPane userListScroller = new JScrollPane(userList);
		userListScroller.setPreferredSize(new Dimension(250, 80));
		
		JScrollPane statusListScroller = new JScrollPane(statusList);
		statusListScroller.setPreferredSize(new Dimension(250, 80));		
		
		borderStatus.add(myStatus,BorderLayout.WEST);
		borderStatus.add(statusListScroller,BorderLayout.CENTER);
		
		borderList.add(userListScroller,BorderLayout.CENTER);
		borderList.add(borderStatus,BorderLayout.NORTH);	
		
		borderFinal.add(borderList,BorderLayout.WEST);
		
		onglets.setOpaque(true);
		
		
		borderFinal.add(onglets,BorderLayout.CENTER);
		
		this.add(borderFinal);
		this.setVisible(true);
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
