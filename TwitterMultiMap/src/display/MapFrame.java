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
	Integer numMaps;
	MapPanel map;


	
	public MapFrame(){
		numMaps = 0;
		setUndecorated(true);
		setVisible(true);
		
		
	}
	
	public void addMap(){
		map = new MapPanel(mapW, mapH, border); 
		add(map);
	}
	
	public void saveInstance(){
		map.saveInstance();
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
		border = 0;//pixels
		
		//----TURN ON SCREENS----
		addMap();
		
	}

	public void loadInstance() {
		map.loadInstance();
		
	}

	public void cycleInstances(Integer seconds) {
		map.cycleInstances(seconds);
		
	}
	
	
}
