package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

public class TabButton extends JButton implements ActionListener, MouseListener {
    
	public TableauOnglet moi = null;
	public TabButton but = null;
	
	public TabButton(TableauOnglet moi,String titre) {
        int size = 17;
        this.moi = moi;
        but = this;
        setPreferredSize(new Dimension(size, size));
        setToolTipText("Fermer cet onglet");
        //Make the button looks the same for all Laf's
        setUI(new BasicButtonUI());
        
        //Rends le bouton transparent
        setContentAreaFilled(false);
        //pas besoin d'avoir le focus
        setFocusable(false);
        setBorder(BorderFactory.createEtchedBorder());
        this.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				setBorder( BorderFactory.createEtchedBorder() );
				validate();
			}

			public void mouseExited(MouseEvent e)
			{
				setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );
				validate(); 
			}

			public void mouseClicked(MouseEvent e)
			{
				
				for(int i=0;i<moi.getTabCount();i++){
					
				
					if(moi.getTitleAt(i).equals(titre)&&(!moi.getTitleAt(i).equals("Broadcast"))){
							moi.removeTabAt(i);
							moi.getListTabs().remove(titre);
		            System.gc();
					}
				}
			}

		} );
    }
    /*
     * fonction qui ferme l'onglet du bouton close sur lequel on a cliqué
     */
   
    

	/*
	* fonction qui ferme l'onglet du bouton close sur lequel on a cliqué
	*/


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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
