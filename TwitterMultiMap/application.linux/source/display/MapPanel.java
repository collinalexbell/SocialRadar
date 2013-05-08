package display;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MapDisplay display;
	Integer w, h;
	Integer border;
	
	MapPanel(Integer _w, Integer _h, Integer _border){
		super();
		w = _w;
		h = _h;
		border = _border;
		this.setLayout(new FlowLayout(FlowLayout.LEFT, border/2, border/2));
		display = new MapDisplay(this, w-border, h-border);
		display.init();
		add(display);
		
		
	}
}
