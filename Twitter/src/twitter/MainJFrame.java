package twitter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Highlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;


public class MainJFrame extends JFrame {

	private JPanel contentPane;
	JTextPane textPane;
	JTextPane topWords;
	String prevTweet;
	TweetMap tweetMap;
	JLabel timeLabel;
	JPanel rightSide;

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
        contentPane.add(Map, BorderLayout.WEST);
        Twitter sketch = new Twitter(this);
        tweetMap = sketch.getTweetMap();
        Map.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        sketch.screen = new Dimension(950, 700);
        Map.add(sketch);
        
        rightSide = new JPanel();
        
        JPanel Menue = new JPanel();
        
        JPanel words = new JPanel();
        
        JPanel Tweet = new JPanel();
        
        
        textPane = new JTextPane();
        textPane.setEditable(false);
        
        textPane.setBackground(this.getBackground());
        
        topWords = new JTextPane();
        topWords.setEditable(false);
        
        topWords.setBackground(this.getBackground());

        Tweet.add(textPane);
        contentPane.add(Tweet, BorderLayout.SOUTH);
        contentPane.add(rightSide, BorderLayout.EAST);
        rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
        rightSide.add(Menue);
        rightSide.add(words);
        words.add(topWords);
        
        JLabel lblTweetDisplayTime = new JLabel("Tweet Display Time");
        Menue.add(lblTweetDisplayTime);
        
        
        
        JSlider slider = new JSlider(0,200,30);
        
        
		slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
			    JSlider source = (JSlider)e.getSource();
			    //if (!source.getValueIsAdjusting()) {
			    int millis = ((int)source.getValue())*1000;    
			    tweetMap.setTimeToDisappear(millis);
			    timeLabel.setText(((Integer)(millis/1000)).toString());
			    
			        
			    //}
			}
		});
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
        
        Menue.add(slider);
        
        timeLabel = new JLabel("30");
        Menue.add(timeLabel);
        sketch.init(); //this is the function used to start the execution of the sketch
        this.setVisible(true);
	}

	public void updateTweet(String text){
		if (!text.equals(prevTweet)){
			textPane.setText("Tweet: " + text);
		}
		prevTweet = text;
	}
	
	public void updateTopWord(){
		topWords.setText(tweetMap.getTopWords(10));
	}
}
