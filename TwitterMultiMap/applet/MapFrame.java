package display;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class MapFrame extends JFrame{
	Integer mapW, mapH;
	Integer border;


	
	public MapFrame(){
		setUndecorated(true);
		setVisible(true);
		
		
	}
	
	public void addMap(){
		MapPanel map = new MapPanel(mapW-border, mapH-border, border);
		add(map);
	}
	
	public void addMenu(){
		
	}

	public void refreshSize(Rectangle maxBounds) {
		//----GET SCREEN INFO-----
		 
		

		//     to use another display change devices[0] to devices[1]...etc
		mapW = maxBounds.width;
		mapH = maxBounds.height;
		
		//----SET SCREEN SIZE----
		//this.setSize(mapW, mapH);
		
		//----SET EXIT ON CLOSE BUTTON----
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//----SET BORDER SIZE AROUND DISPLAYS
		border = 20;//pixels
		
		//----TURN ON SCREENS----
		addMap();
		
	}
	
	
}
