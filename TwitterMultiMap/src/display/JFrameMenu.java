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
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JFrameMenu extends JFrame{
	JPanel screenSelect;
	JPanel displayInstances;
	JPanel addMapPanel;
	JPanel contentPane;
	MapFrame mapFrame;
	Integer cycleSeconds;
	
	public JFrameMenu(){
		contentPane = new JPanel();
		this.setContentPane(contentPane);
		screenSelect = new JPanel();
		displayInstances = new JPanel();
		addMapPanel = new JPanel();
		screenSelectRefresh();
		addDisplayInstancesPanel();
		addMapListen();
		contentPane.add(screenSelect);
		contentPane.add(displayInstances);
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
	
	public void addDisplayInstancesPanel(){
		JButton button = new JButton("Save Display Instance");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mapFrame.saveInstance();
			}
		});
		
		JButton button2 = new JButton("Load Last Display Instance");
		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mapFrame.loadInstance();
			}
		});
		
		JButton button3 = new JButton("Cycle Through Instances");
		button3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mapFrame.cycleInstances(cycleSeconds);
			}
		});
		
		int maxCycleTime = 20;
		int minCycleTime = 3;
		int cycleTimeInit = 10;
		
		JSlider chooseCycleTime = new JSlider(JSlider.HORIZONTAL,minCycleTime, maxCycleTime,cycleTimeInit );
		
				
		class SliderListener implements ChangeListener{
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					cycleSeconds = (Integer)source.getValue() *1000;
				}
			}
		}
		
		chooseCycleTime.addChangeListener(new SliderListener());
		
		displayInstances.add(button);
		displayInstances.add(button2);
		displayInstances.add(button3);
		displayInstances.add(chooseCycleTime);
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
