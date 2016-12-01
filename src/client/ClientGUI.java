package client;

import java.awt.BorderLayout;
import java.awt.Button;
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

import javax.swing.JFrame;

public class ClientGUI extends JFrame implements ActionListener, KeyListener{

	private TextArea readMessageArea = null;
	private TextField writeMessageArea = null;
	
	ClientGUI(Client client) {
		
		super("Chat V1.0");
		this.setSize(800,600);
		this.setResizable(true);
		this.setMinimumSize(new Dimension(350,400));
		this.setLayout(new BorderLayout());
		
		readMessageArea = new TextArea();
		readMessageArea.setEditable(false);
		this.add(readMessageArea, "Center");
		readMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		
		writeMessageArea = new TextField(30);
		writeMessageArea.addKeyListener(this);
		writeMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
		p.add(writeMessageArea);
		
		Button send = new Button("Send");
		p.add(send);
		
		this.add(p, "South");
		writeMessageArea.requestFocus();
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
