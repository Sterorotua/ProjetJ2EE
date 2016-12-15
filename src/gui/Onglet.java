package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import client.Client;

public class Onglet extends Panel implements WindowListener, ActionListener, KeyListener{ 

	private Client client;
	private String receiver;
	
	private TextArea readMessageArea = null;
	private TextField writeMessageArea = null;
	public String msgSent = "";
	
	private Panel p = null;
	
	public Onglet(Client client, String receiver){
		super();
		this.client = client;
		this.receiver = receiver;
		
		p = new Panel(new FlowLayout());		
		this.setLayout(new BorderLayout());
				
		this.readMessageArea = new TextArea();
		this.readMessageArea.setBackground(Color.WHITE);
		this.readMessageArea.setEditable(false);
		this.readMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(readMessageArea);
				
		this.writeMessageArea = new TextField(30);
		this.writeMessageArea.addKeyListener(this);
		this.writeMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
		this.writeMessageArea.setEnabled(false);
		p.add(writeMessageArea,FlowLayout.LEFT);
		
		enablingWriting(true);
		
		Button send = new Button("Send");
		send.addActionListener(this);
		p.add(send);
		this.add(p,BorderLayout.SOUTH);
	}
	
	public void getHistory(String dest){
		client.sendMessage("/getHisto "+dest);
	}
	
	public void setHistory(String history){
		System.out.println("historique "+history);

		String[] historySplitted = history.split("\\|\\|\\|\\|\\|");
		for(String split : historySplitted){
			System.out.println(split);
			this.readMessageArea.append("\n"+split);
		}
	}

	public TextArea getReadMessageArea() {
		return readMessageArea;
	}


	public void setReadMessageArea(TextArea readMessageArea) {
		this.readMessageArea = readMessageArea;
	}


	public TextField getWriteMessageArea() {
		return writeMessageArea;
	}


	public void setWriteMessageArea(TextField writeMessageArea) {
		this.writeMessageArea = writeMessageArea;
	}
	
	public void enablingWriting(boolean enabled){
		this.writeMessageArea.setEnabled(enabled);
	}
	
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode()==KeyEvent.VK_ENTER){
			msgSent = writeMessageArea.getText();
			if(!msgSent.trim().isEmpty() || !msgSent.trim().equals("")){
				readMessageArea.append("\n[ME] : " + msgSent);
				writeMessageArea.setText("");
				if(receiver.equals("broadcast")){
					client.sendMessage("/b "+msgSent);
					System.out.println("/b "+msgSent);
				}
				else{
					client.sendMessage("/w "+receiver+" "+msgSent);
					System.out.println("/w "+receiver+" "+msgSent);
				}
				writeMessageArea.requestFocus();	
			}
		}
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
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("Send")){
			msgSent = writeMessageArea.getText();
			if(!msgSent.trim().isEmpty() || !msgSent.trim().equals("")){
				readMessageArea.append("\n[ME] : " + msgSent);
				writeMessageArea.setText("");
				if(receiver.equals("broadcast")){
					client.sendMessage("/b "+msgSent);
					System.out.println("/b "+msgSent);
				}
				else{
					client.sendMessage("/w "+receiver+" "+msgSent);
					System.out.println("/w "+receiver+" "+msgSent);
				}
				writeMessageArea.requestFocus();
			}
		}
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