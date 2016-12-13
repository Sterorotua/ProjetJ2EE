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
		broadcast = new Onglet(moi);		
		i = this.getComponentCount();		
		
		this.addTab("Broadcast", broadcast);

	}
	
	public void addPrivate(String pseudo){
		
		Onglet messagePrivate = new Onglet(moi);
		
		this.addTab(pseudo, messagePrivate);
		super.setTabComponentAt((this.getComponentCount()-1), new CloseTabPanel("Broadcast",moi));
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));		
		
	}

	






}
	