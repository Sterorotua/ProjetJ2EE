package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

public class TabButton extends JButton implements ActionListener {
    
	public TableauOnglet moi = null;
	
	public TabButton(TableauOnglet moi) {
        int size = 17;
        this.moi = moi;
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
