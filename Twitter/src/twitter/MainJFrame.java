package twitter;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import java.awt.GridLayout;


public class MainJFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJFrame frame = new MainJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setSize(1400, 900);
        contentPane.setLayout(new BorderLayout(0, 0));
        javax.swing.JPanel Map = new javax.swing.JPanel();
        Map.setSize(950,700);
        Map.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        processing.core.PApplet sketch = new Twitter();
        sketch.screen = new Dimension(950, 700);
        Map.add(sketch);
        getContentPane().add(Map, BorderLayout.WEST);
        
        JPanel Menue = new JPanel();
        contentPane.add(Menue, BorderLayout.EAST);
        
        JTextPane txtpnTimeToDisappear = new JTextPane();
        txtpnTimeToDisappear.setText("Time to Disappear");
        Menue.add(txtpnTimeToDisappear);
        
        JSlider slider = new JSlider();
        Menue.add(slider);
        sketch.init(); //this is the function used to start the execution of the sketch
        this.setVisible(true);
	}

}
