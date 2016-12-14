package GUI;

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

import javax.swing.JButton;


public class Onglet extends Panel implements WindowListener, ActionListener, KeyListener{ 
	
		private TextArea readMessageArea = null;
		
		private JButton button; 
		private TextField writeMessageArea = null;
		public String msgSent = "";
		
		private Panel p = null;
		
		Onglet(TableauOnglet moi){
			super();
			p = new Panel(new FlowLayout());		
			this.setLayout(new BorderLayout());
			
		  
					
			this.readMessageArea = new TextArea();
			this.readMessageArea.setBackground(Color.WHITE);
			this.readMessageArea.setEditable(false);
			this.readMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
			this.add(readMessageArea,BorderLayout.CENTER);
					
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