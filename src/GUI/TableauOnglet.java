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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class TableauOnglet extends JTabbedPane implements WindowListener, ActionListener, KeyListener{
	private TextArea readMessageArea = null;
	private TextField writeMessageArea = null;
	public String msgSent = "";
	
	TableauOnglet(){
		super(SwingConstants.TOP);
		
		Panel p = new Panel();
		Panel areaText = new Panel();
		Panel pfinal = new Panel();
		
		p.setLayout(new FlowLayout());
				
		this.readMessageArea = new TextArea();
		this.readMessageArea.setBackground(Color.WHITE);
		this.readMessageArea.setEditable(false);
		this.readMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
		areaText.add(readMessageArea);
				
		this.writeMessageArea = new TextField(30);
		this.writeMessageArea.addKeyListener(this);
		this.writeMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
		this.writeMessageArea.setEnabled(false);
		p.add(writeMessageArea);
		
		Button send = new Button("Send");
		send.addActionListener(this);
		p.add(send);
		pfinal.add(areaText);
		pfinal.add(p);
		
		this.addTab("Broadcast", pfinal);
		
		
	}

	public void addPrivate(String pseudo){
		Panel p = new Panel();
		p.setLayout(new BorderLayout());
				
		this.readMessageArea = new TextArea();
		this.readMessageArea.setBackground(Color.WHITE);
		this.readMessageArea.setEditable(false);
		this.readMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
		this.readMessageArea.setSize(200,200);
		p.add(readMessageArea, BorderLayout.CENTER);
		
		
		this.writeMessageArea = new TextField(30);
		this.writeMessageArea.addKeyListener(this);
		this.writeMessageArea.setFont(new Font("Arial", Font.PLAIN, 12));
		this.writeMessageArea.setEnabled(false);
		p.add(writeMessageArea, BorderLayout.SOUTH,50);
		
		Button send = new Button("Send");
		send.addActionListener(this);
		p.add(send,BorderLayout.SOUTH);
		
		this.addTab(pseudo, p);
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
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

	