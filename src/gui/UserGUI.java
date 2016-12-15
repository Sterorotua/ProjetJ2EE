package gui;

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
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import client.Client;
import client.InfoUser;

public class UserGUI extends JFrame implements WindowListener, ActionListener, KeyListener, MouseListener {
	private TableauOnglet onglets=null;	
	private JLabel userListLabel= null;
	private JList userList = null;
	private JLabel bannedUserListLabel= null;
	private JList bannedUserList = null;
	private JLabel notifiedUserListLabel= null;
	private JList notifiedUserList = null;
	private JList statusList = null;
	private JButton myStatus = null;		
	private MenuDeroulant popupMenu;
	public MenuDeroulantAdmin1 popupMenuAdmin1;
	public MenuDeroulantAdmin2 popupMenuAdmin2;
	
	private DefaultListModel<String> userConnected = null; // doit être lié a la database
	private DefaultListModel<String> status = null; 
	private DefaultListModel<String> bannedUser = null; // doit être lié a la database
	private DefaultListModel<String> notifiedUser = null; // doit être lié a la database
	
	private Client client = null;
	
	
	public UserGUI(Client client){
		
		
		
		super("Communit'ISEN Administrator: Manage your Community ! Connected as "+client.getNickname());
		this.client = client;
		this.setSize(700,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		userConnected= new DefaultListModel<String>();
		status = new DefaultListModel<String>();
		bannedUser = new DefaultListModel<String>();
		notifiedUser = new DefaultListModel<String>();
		
		userList = new JList(userConnected);
		statusList = new JList(status);
		bannedUserList = new JList(bannedUser);
		notifiedUserList = new JList(notifiedUser);
		popupMenu = new MenuDeroulant(this);
		popupMenuAdmin1 = new MenuDeroulantAdmin1(this);
		popupMenuAdmin2 = new MenuDeroulantAdmin2(this);

		
		bannedUserListLabel = new JLabel("Banned user");
		notifiedUserListLabel = new JLabel("Notified user");
		userListLabel = new JLabel("User connected");
		
		onglets = new TableauOnglet(client);
		myStatus = new JButton();
		
		JPanel borderList = new JPanel();
		borderList.setLayout(new BorderLayout());
		
		JPanel borderUserConnected = new JPanel();
		borderUserConnected.setLayout(new BorderLayout());
		
		JPanel borderStatus = new JPanel();
		borderStatus.setLayout(new BorderLayout());
		
		JPanel borderFinal = new JPanel();
		borderFinal.setLayout(new BorderLayout());
		
		JPanel borderBannedUser = new JPanel();
		borderBannedUser.setLayout(new BorderLayout());
		
		JPanel border = new JPanel();
		border.setLayout(new BorderLayout());
		
		JPanel borderNotifiedUser = new JPanel();
		borderNotifiedUser.setLayout(new BorderLayout());
		
		//initialisation d'une liste d'utilisateur pour tests
		/*userConnected.addElement("paul");
		userConnected.addElement("stephane");
		userConnected.addElement("jordan");
		userConnected.addElement("remy");*/
		
		//Différents status possible
		status.addElement("Available");
		status.addElement("Busy");
		status.addElement("Unavailable");	
		
		//initialisation d'une liste d'utilisateur bannis pour tests
		bannedUser.addElement("Jean");
		bannedUser.addElement("Dupont");
		
		//initialisation d'une liste d'utilisateur bannis pour tests
		notifiedUser.addElement("remy");
		notifiedUser.addElement("stephane");
		
		// Réglage des listes
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setLayoutOrientation(JList.VERTICAL);
		userList.setVisibleRowCount(-1);
		if(this.client.getAdmin()) {
			userList.addMouseListener(new MouseAdapter(){
	            public void mouseReleased(MouseEvent event){
	            	if(event.isPopupTrigger()){
	            		 //Only show the list's popup menu if an item is not selected and positioned where the mouse was right-clicked
	                    int closestIndexToClick = userList.locationToIndex(event.getPoint());
	                    Rectangle cellBounds = userList.getCellBounds(closestIndexToClick, closestIndexToClick); 
	                    if( cellBounds != null && !cellBounds.contains(event.getPoint()) ){
	                    	
	                    	if((popupMenuAdmin1.isVisible())){
	                    		popupMenuAdmin1.setVisible(false);
	                        }
	                    	else if((popupMenuAdmin2.isVisible())){
	                    		popupMenuAdmin2.setVisible(false);
	                        }
	                        	
	                    }
	                    else{
	                    	if(!popupMenuAdmin1.isVisible()){                    		
	                    	
		                    	event.getSource();
		                		Point p = new Point(event.getX(), event.getY());
		                		int indexClick = userList.locationToIndex(p);
		                		popupMenuAdmin1.setUserClicked(userConnected.getElementAt(indexClick));	                		
		                    	popupMenuAdmin1.setVisible(true);
		                    	popupMenuAdmin1.setLocation(event.getLocationOnScreen());
		                    	borderFinal.addMouseListener(new MouseAdapter(){
		                    		public void mouseReleased(MouseEvent event){		                    			 
		                    			if((popupMenuAdmin1.isVisible())){
		    	                    		popupMenuAdmin1.setVisible(false);
		    	                        }
		    	                    	else if((popupMenuAdmin2.isVisible())){
		    	                    		popupMenuAdmin2.setVisible(false);
		    	                        }
		                            }	                               		       	     
		                      	});
	                    	}
	                    	else{
	                    		if((popupMenuAdmin1.isVisible())){
		                    		popupMenuAdmin1.setVisible(false);
		                        }
		                    	else if((popupMenuAdmin2.isVisible())){
		                    		popupMenuAdmin2.setVisible(false);
		                        }
	                    	}
	                    }
	                    
	                }
	                else{                	
	                	if((popupMenuAdmin1.isVisible())){
                    		popupMenuAdmin1.setVisible(false);
                        }
                    	else if((popupMenuAdmin2.isVisible())){
                    		popupMenuAdmin2.setVisible(false);
                        }
	            	}
	            }      
			});
		}
		else {
			userList.addMouseListener(new MouseAdapter(){
	            public void mouseReleased(MouseEvent event){
	            	if(event.isPopupTrigger()){
	            		 //Only show the list's popup menu if an item is not selected and positioned where the mouse was right-clicked
	                    int closestIndexToClick = userList.locationToIndex(event.getPoint());
	                    Rectangle cellBounds = userList.getCellBounds(closestIndexToClick, closestIndexToClick); 
	                    if( cellBounds != null && !cellBounds.contains(event.getPoint()) ){
	                    	
	                    	if((popupMenu.isVisible())){
	                    		popupMenu.setVisible(false);
	                        }
	                    }                        	                   
	                    else{
	                    	if(!popupMenu.isVisible()){                    		
	                    	
		                    	event.getSource();
		                		Point p = new Point(event.getX(), event.getY());
		                		int indexClick = userList.locationToIndex(p);
		                		if (!userConnected.getElementAt(indexClick).equals(client.getNickname())){
			                		popupMenu.setUserClicked(userConnected.getElementAt(indexClick));
			                		popupMenu.setVisible(true);
			                		popupMenu.setLocation(event.getLocationOnScreen());
			                    	borderFinal.addMouseListener(new MouseAdapter(){
			                    		public void mouseReleased(MouseEvent event){		                    			 
			                		        popupMenu.setVisible(false);
			                            }	                               		       	     
			                      	});
		                		}
	                		}
	                    	else{
	                			if((popupMenu.isVisible())){
	                			popupMenu.setVisible(false);
	                			}                        	
	                		}
	                    }
	            	}
	                 else{                	
	                	 	if((popupMenu.isVisible())){
	                	 		popupMenu.setVisible(false);
	                    	}
	                }                                            
	            }      
			});
		}
		
		
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
            		if(indexClick == 0){
            			myStatus.setBackground(java.awt.Color.GREEN);
            			client.sendMessage("/s "+client.getNickname()+" 1");
            		}
            		else if(indexClick == 1){
            			myStatus.setBackground(java.awt.Color.YELLOW);
            			client.sendMessage("/s "+client.getNickname()+" 2");
            		}
            		else if(indexClick == 2){
            			myStatus.setBackground(java.awt.Color.RED);
            			client.sendMessage("/s "+client.getNickname()+" 3");
            		}
            	}
            }
		});
		
		if(this.client.getAdmin()) {
			bannedUserList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			bannedUserList.setLayoutOrientation(JList.VERTICAL);
			bannedUserList.setVisibleRowCount(-1);
			bannedUserList.addMouseListener(new MouseAdapter(){
	            public void mouseReleased(MouseEvent event){
	            	if(event.isPopupTrigger()){
	           		 //Only show the list's popup menu if an item is not selected and positioned where the mouse was right-clicked
	                   int closestIndexToClick = bannedUserList.locationToIndex(event.getPoint());
	                   Rectangle cellBounds = bannedUserList.getCellBounds(closestIndexToClick, closestIndexToClick); 
	                   if( cellBounds != null && !cellBounds.contains(event.getPoint()) ){
	                   	
	                	   if((popupMenuAdmin1.isVisible())){
	                    		popupMenuAdmin1.setVisible(false);
	                        }
	                    	else if((popupMenuAdmin2.isVisible())){
	                    		popupMenuAdmin2.setVisible(false);
	                        }
	                       	
	                   }
	                   else{
	                    	if(!popupMenuAdmin2.isVisible()){                    		
	                   	
		                    	event.getSource();
		                		Point p = new Point(event.getX(), event.getY());
		                		int indexClick = bannedUserList.locationToIndex(p);
		                		popupMenuAdmin2.setUserClicked(bannedUser.getElementAt(indexClick));	                		
		                		popupMenuAdmin2.setVisible(true);
		                    	popupMenuAdmin2.setLocation(event.getLocationOnScreen());
		                    	borderFinal.addMouseListener(new MouseAdapter(){
		                    		public void mouseReleased(MouseEvent event){		                    			 
		                    			if((popupMenuAdmin1.isVisible())){
		    	                    		popupMenuAdmin1.setVisible(false);
		    	                        }
		    	                    	else if((popupMenuAdmin2.isVisible())){
		    	                    		popupMenuAdmin2.setVisible(false);
		    	                        }
		                            }	                               		       	     
		                      	});
	                   	}
	                   	else{
	                   		if((popupMenuAdmin1.isVisible())){
	                    		popupMenuAdmin1.setVisible(false);
	                        }
	                    	else if((popupMenuAdmin2.isVisible())){
	                    		popupMenuAdmin2.setVisible(false);
	                        }
	                   	}
	                   }
	                   
	               }
	               else{                	
	            	   if((popupMenuAdmin1.isVisible())){
                   		popupMenuAdmin1.setVisible(false);
                       }
                   	else if((popupMenuAdmin2.isVisible())){
                   		popupMenuAdmin2.setVisible(false);
                       }
	           	}
	            }      
			});
			
			notifiedUserList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			notifiedUserList.setLayoutOrientation(JList.VERTICAL);
			notifiedUserList.setVisibleRowCount(-1);
			notifiedUserList.addMouseListener(new MouseAdapter(){
	            public void mouseReleased(MouseEvent event){
	            	if(event.isPopupTrigger()){
	           		 //Only show the list's popup menu if an item is not selected and positioned where the mouse was right-clicked
	                   int closestIndexToClick = notifiedUserList.locationToIndex(event.getPoint());
	                   Rectangle cellBounds = notifiedUserList.getCellBounds(closestIndexToClick, closestIndexToClick); 
	                   if( cellBounds != null && !cellBounds.contains(event.getPoint()) ){
	                   	
	                	   if((popupMenuAdmin1.isVisible())){
	                    		popupMenuAdmin1.setVisible(false);
	                        }
	                    	else if((popupMenuAdmin2.isVisible())){
	                    		popupMenuAdmin2.setVisible(false);
	                        }
	                       	
	                   }
	                   else{
	                    	if(!popupMenuAdmin1.isVisible()){                    		
	                   	
		                    	event.getSource();
		                		Point p = new Point(event.getX(), event.getY());
		                		int indexClick = notifiedUserList.locationToIndex(p);
		                		popupMenuAdmin1.setUserClicked(notifiedUser.getElementAt(indexClick));	                		
		                		popupMenuAdmin1.setVisible(true);
		                		popupMenuAdmin1.setLocation(event.getLocationOnScreen());
		                		borderFinal.addMouseListener(new MouseAdapter(){
		                    		public void mouseReleased(MouseEvent event){		                    			 
		                    			if((popupMenuAdmin1.isVisible())){
		    	                    		popupMenuAdmin1.setVisible(false);
		    	                        }
		    	                    	else if((popupMenuAdmin2.isVisible())){
		    	                    		popupMenuAdmin2.setVisible(false);
		    	                        }
		                            }	                               		       	     
		                      	});
	                   	}
	                   	else{
	                   		if((popupMenuAdmin1.isVisible())){
	                    		popupMenuAdmin1.setVisible(false);
	                        }
	                    	else if((popupMenuAdmin2.isVisible())){
	                    		popupMenuAdmin2.setVisible(false);
	                        }
	                   	}
	                   }
	                   
	               }
	               else{                	
	            	   if((popupMenuAdmin1.isVisible())){
                   		popupMenuAdmin1.setVisible(false);
                       }
                   	else if((popupMenuAdmin2.isVisible())){
                   		popupMenuAdmin2.setVisible(false);
                       }
	           	}
	            }      
			});
		}
		
		
		// Réglage couleur bouton
		myStatus.setBackground(java.awt.Color.GREEN);
		
		// Organisation des panneaux
		
		JScrollPane userListScroller = new JScrollPane(userList);
		userListScroller.setPreferredSize(new Dimension(250, 80));
		
		JScrollPane statusListScroller = new JScrollPane(statusList);
		statusListScroller.setPreferredSize(new Dimension(250, 80));		
		
		JScrollPane bannedListScroller = new JScrollPane(bannedUserList);
		bannedListScroller.setPreferredSize(new Dimension(250, 80));	
		
		JScrollPane notifiedListScroller = new JScrollPane(notifiedUserList);
		notifiedListScroller.setPreferredSize(new Dimension(250, 80));	
		
		borderStatus.add(myStatus,BorderLayout.WEST);
		borderStatus.add(statusListScroller,BorderLayout.CENTER);

		
		borderUserConnected.add(userListLabel,BorderLayout.NORTH);
		borderUserConnected.add(userListScroller,BorderLayout.CENTER);	
		
		if(this.client.getAdmin()) {
			borderBannedUser.add(bannedUserListLabel,BorderLayout.NORTH);
			borderBannedUser.add(bannedListScroller,BorderLayout.CENTER);	
			
			borderNotifiedUser.add(notifiedUserListLabel,BorderLayout.NORTH);
			borderNotifiedUser.add(notifiedListScroller,BorderLayout.CENTER);	
			
			border.add(borderNotifiedUser,BorderLayout.NORTH);
			border.add(borderBannedUser,BorderLayout.SOUTH);
		}
		
		borderList.add(borderStatus,BorderLayout.NORTH);
		borderList.add(borderUserConnected,BorderLayout.CENTER);
		borderList.add(border,BorderLayout.SOUTH);
		
		
		borderFinal.add(borderList,BorderLayout.WEST);

		borderFinal.add(onglets,BorderLayout.CENTER);
		
		onglets.setOpaque(true);		
		
		this.add(borderFinal);
		
		if(!this.client.getAdmin()) {
			bannedUserList.setVisible(false);
			notifiedUserList.setVisible(false);
			bannedUserListLabel.setVisible(false);
			notifiedUserListLabel.setVisible(false);
		}

		
		this.setVisible(true);
	}
	
	//Récupére TableauOnglet
	public TableauOnglet getOnglets(){
		return this.onglets;
	}
	
	//Met à jour la liste des utilisateurs / admins connectés
	public void updConnectedList(ArrayList <InfoUser> listUsers){

		userConnected.removeAllElements();
		for(InfoUser user : listUsers){
			String status ="";
			if(user.getStatus() == 1){
				status = " [online]";
			}
			else if (user.getStatus() == 2){
				status = " [busy]";
			}
			else{
				status = " [unavailable]";
			}
			userConnected.addElement(user.getNickname()+status);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*StringTokenizer st = new StringTokenizer(list);
		while(st.hasMoreTokens()){
			String nickname = st.nextToken();
			list = list.replace(nickname+" ", "");
			userConnected.addElement(nickname);	
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
	
	//Met à jour la liste des utilisateurs bannis
	public void updBannedList(String list){
		bannedUser.removeAllElements();
		
		StringTokenizer st = new StringTokenizer(list);
		while(st.hasMoreTokens()){
			String nickname = st.nextToken();
			list = list.replace(nickname+" ", "");
			bannedUser.addElement(nickname);	
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//Met à jour la liste des utilisateurs notifiés
	public void updNotifiedList(String list){
		notifiedUser.removeAllElements();
		
		StringTokenizer st = new StringTokenizer(list);
		while(st.hasMoreTokens()){
			String nickname = st.nextToken();
			list = list.replace(nickname+" ", "");
			notifiedUser.addElement(nickname);	
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void clearBannedList(){
		bannedUser.removeAllElements();
	}
	
	public Client getClient(){
		return this.client;
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


