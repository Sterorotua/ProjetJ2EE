package client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

import javax.swing.JFrame;


public class ClientGUI extends JFrame implements WindowListener, ActionListener, KeyListener{

	private TextArea readMessageArea = null;
	private TextField writeMessageArea = null;
	private Client client = null;
	public String msgSent = "";
	
	ClientGUI(Client client) {
		
		super("Chat V1.0");
		this.client = client;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		this.addWindowListener( new WindowAdapter() {
		    public void windowOpened( WindowEvent e ){
		        writeMessageArea.requestFocus();
		    }
		}); 
		
		this.setSize(800,600);
		this.setResizable(true);
		this.setMinimumSize(new Dimension(350,400));
		this.setLayout(new BorderLayout());
		
		this.readMessageArea = new TextArea();
		this.readMessageArea.setBackground(Color.WHITE);
		this.readMessageArea.setEditable(false);
		this.add(readMessageArea, "Center");
		this.readMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		
		this.writeMessageArea = new TextField(30);
		this.writeMessageArea.addKeyListener(this);
		this.writeMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
		p.add(writeMessageArea);
		
		Button send = new Button("Send");
		send.addActionListener(this);
		p.add(send);
		
		this.add(p, "South");
		writeMessageArea.requestFocusInWindow();
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("Send")){
			msgSent = writeMessageArea.getText();
			readMessageArea.append("\n[ME] : " + msgSent);
			writeMessageArea.setText("");
			client.envoiMessage(client.socket, msgSent);
			writeMessageArea.requestFocus();
		}
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode()==KeyEvent.VK_ENTER){
			msgSent = writeMessageArea.getText();
			readMessageArea.append("\n[ME] : " + msgSent);
			writeMessageArea.setText("");
			client.envoiMessage(client.socket, msgSent);
			writeMessageArea.requestFocus();	
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		client.envoiMessage(client.socket, "quit");

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		client.envoiMessage(client.socket, "quit");
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
	public void windowOpened(WindowEvent we) {
		
	}
	
	public TextArea getTextArea(){
		return readMessageArea;
		
	}

}
