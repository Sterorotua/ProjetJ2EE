package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class LoginGUI extends JFrame implements WindowListener, ActionListener, KeyListener{

	private JButton validAdmin = null;
	private JButton validUser = null;
	private TextField nickname = null;
	private TextField login = null;
	private JPasswordField password = null;
	private JLabel labelLogin = null;
	private JLabel labelNickname = null;
	private JLabel labelPassword = null;
	private JLabel labelAdmin = null;
	private JLabel labelUser = null;
	private JPanel border = null;
	private JPanel borderAdmin = null;
	private JPanel borderUser = null;
	private JPanel gridAdmin = null;
	private JPanel gridUser = null;
	private JPanel flowAdmin = null;
	private JPanel flowUser = null;
	private JPanel vide = null;
	
	
	private Client client = null;
	public String msgSent = "";
	
	LoginGUI(Client client){
		
		super("Communit'ISEN : Welcome to your Community !");
		this.client = client;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// A l'ouverture, focus sur le champs nickname
		this.addWindowListener( new WindowAdapter() {
		    public void windowOpened( WindowEvent e ){
		        nickname.requestFocus();
		    }
		}); 
		
		
		// Initialisation de la JFrame
		
		this.setSize(700,200);
		this.setResizable(false);
		this.setMinimumSize(new Dimension(700,200));
		this.setMaximumSize(new Dimension(900,400));
		
		//Définition des objets

		validAdmin = new JButton("Log as an admin");
		validUser = new JButton("Log as an user");
		nickname = new TextField();
		login = new TextField();
		password = new JPasswordField();
		labelLogin = new JLabel("Login :");
		labelPassword = new JLabel("Password :");
		labelNickname = new JLabel("Nickname :");
		labelAdmin = new JLabel("Please enter your administrator login and password");
		labelUser = new JLabel("Please enter your nickname");
		border = new JPanel();
		borderAdmin = new JPanel();
		borderUser = new JPanel();
		gridAdmin = new JPanel();
		gridUser = new JPanel();
		vide = new JPanel();
		flowAdmin = new JPanel();
		flowUser = new JPanel();
		
		
		border.setLayout(new GridLayout(1,2));
		borderAdmin.setLayout(new BorderLayout());
		borderUser.setLayout(new BorderLayout());
		gridAdmin.setLayout(new GridLayout(5,1));
		gridUser.setLayout(new GridLayout(3,1));
		flowAdmin.setLayout(new FlowLayout());
		flowUser.setLayout(new FlowLayout());
		
			//Placement des éléments
		
		//réglage taille objet
		labelAdmin.setSize(5, 1);
		labelUser.setSize(5, 1);
		labelNickname.setSize(5, 1);
		labelPassword.setSize(5, 1);
		labelLogin.setSize(5, 1);
		login.setSize(5, 1);
		password.setSize(5, 1);
		nickname.setSize(5, 1);
		validUser.setSize(5,1);
		validAdmin.setSize(5,1);

		
		
		
		// réglage de la couleur
		
		nickname.setBackground(Color.WHITE);
		login.setBackground(Color.WHITE);
		password.setBackground(Color.WHITE);
		validAdmin.setBackground(Color.RED);
		validUser.setBackground(Color.RED);
		
		
		// Ne peut pas être modifier
		this.nickname.setEditable(false);
		this.login.setEditable(false);
		this.password.setEditable(false);
		
		// Règle police
		nickname.setFont(new Font("Arial", Font.PLAIN, 12));
		login.setFont(new Font("Arial", Font.PLAIN, 12));
		password.setFont(new Font("Arial", Font.PLAIN, 12));
		password.setFont(new Font("Arial", Font.PLAIN, 12));
		password.setFont(new Font("Arial", Font.PLAIN, 12));
		

		//Ajout des objets dans les layouts
		gridAdmin.add(labelAdmin);
		gridAdmin.add(labelLogin);
		gridAdmin.add(login);
		gridAdmin.add(labelPassword);
		gridAdmin.add(password);
		
		gridUser.add(labelUser);
		gridUser.add(labelNickname);
		gridUser.add(nickname);
		
		flowAdmin.add(validAdmin);
		flowUser.add(validUser);
		
		borderAdmin.add(gridAdmin,BorderLayout.NORTH);
		borderAdmin.add(vide,BorderLayout.CENTER);
		borderAdmin.add(flowAdmin,BorderLayout.SOUTH);
		
		borderUser.add(gridUser,BorderLayout.NORTH);
		borderUser.add(vide,BorderLayout.CENTER);
		borderUser.add(flowUser,BorderLayout.SOUTH);
		
		border.add(borderAdmin);
		
		border.add(borderUser);
		
		
		this.add(border);
		

		
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
}
