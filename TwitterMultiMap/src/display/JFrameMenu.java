package display;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JFrameMenu extends JFrame{
	JPanel screenSelect;
	JPanel addMapPanel;
	JPanel contentPane;
	MapFrame mapFrame;
	
	public JFrameMenu(){
		contentPane = new JPanel();
		this.setContentPane(contentPane);
		screenSelect = new JPanel();
		addMapPanel = new JPanel();
		screenSelectRefresh();
		addMapListen();
		contentPane.add(screenSelect);
		contentPane.add(addMapPanel);
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void screenSelectRefresh(){
		//----Get an array of all the display devices----
		GraphicsEnvironment ge = GraphicsEnvironment
		        .getLocalGraphicsEnvironment();
		final GraphicsDevice[] gs = ge.getScreenDevices();
		
		
		//--Int used to index GraphicsDevice--
		Integer x = 0;
		
		//--Creates buttons for all screens and adds a listener that will move the screen
		screenSelect.add(new JLabel("Screen Select:"));
		for (GraphicsDevice i: gs){
			final Integer pass = x;
			JButton button = new JButton("Use screen #" + x.toString());
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.out.println("Using screen" + pass.toString());
					mapFrame = new MapFrame();
					gs[pass].setFullScreenWindow(mapFrame);
					Rectangle maxBounds = mapFrame.getContentPane().getBounds();
					mapFrame.refreshSize(maxBounds);
				}
			});
			screenSelect.add(button);
			
			x++;
		}
	}
	
	public void addMapListen(){
		JButton button = new JButton("Add Map");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mapFrame.addMap();
			}
		});
		addMapPanel.add(button);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrameMenu menu = new JFrameMenu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
