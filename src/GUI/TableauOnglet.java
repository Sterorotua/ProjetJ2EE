package GUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;


public class TableauOnglet extends JTabbedPane {
	public Onglet broadcast=null;
	public TableauOnglet moi = null;
	public int i;
	
	TableauOnglet(){
		super(SwingConstants.TOP);
		moi = this;
		broadcast = new Onglet();		
		i = this.getComponentCount();		
		
		this.addTab("Broadcast", broadcast);

	}
	
	public void addPrivate(String pseudo){
		
		Onglet messagePrivate = new Onglet();
		
		this.addTab(pseudo, messagePrivate);
		super.setTabComponentAt((this.getComponentCount()-1), new CloseTabPanel("Broadcast"));
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));		
		
	}

	class CloseTabPanel extends JPanel{
        JButton button; 
 
	//constructeur sans boolean  qui de base met un bouton close
	    public CloseTabPanel(String titre) {
	            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
	            setOpaque(false);
	            JLabel label = new JLabel(titre);
	            add(label);
	            button = new TabButton();
	            add(button);
	            setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		}
	}    

//constructeur sans boolean  qui de base met un bouton close

class TabButton extends JButton implements ActionListener {
    public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("Fermer cet onglet");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Rends le bouton transparent
            setContentAreaFilled(false);
            //pas besoin d'avoir le focus
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            addActionListener(this);            
        }

		/*
		* fonction qui ferme l'onglet du bouton close sur lequel on a cliqué
		*/
    public void actionPerformed(ActionEvent e) {
            int X = new Double(((JButton)e.getSource()).getMousePosition().getX()).intValue();
            int Y = new Double(((JButton)e.getSource()).getMousePosition().getY()).intValue();
 
            int i = moi.getUI().tabForCoordinate((JTabbedPane)moi, X,Y);
            if (i != -1) {
                moi.remove(i);
            }
        }
 
        //we don't want to update UI for this button
        public void updateUI() {
        }
 
        //dessine la croix dans le bouton
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            } 
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }            
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }


	class Onglet extends Panel implements WindowListener, ActionListener, KeyListener{ 
	
		private TextArea readMessageArea = null;
		
		private JButton button; 
		private TextField writeMessageArea = null;
		public String msgSent = "";
		
		private Panel p = null;
		
		Onglet(){
			super();
			p = new Panel(new FlowLayout());		
			this.setLayout(new BorderLayout());
			
			 button = new TabButton();
		     add(button);
		  
					
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
}
	