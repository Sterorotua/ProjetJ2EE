package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class CloseTabPanel extends JPanel{
    JButton button; 

//constructeur sans boolean  qui de base met un bouton close
    public CloseTabPanel(String titre, TableauOnglet moi) {
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
            setOpaque(false);
            JLabel label = new JLabel(titre);
            add(label);
            button = new TabButton(moi);
            add(button);
            setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}
}    
