package twitter;

import search.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
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

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;


public class MainJFrame extends JFrame {

	private JPanel contentPane;
	JTextPane textPane;
	JTextPane topWords;
	String prevTweet;
	TweetMap tweetMap;
	JLabel timeLabel, rateLabel;
	JPanel rightSide;
	String keyword;
	JLabel searchLabel;
	JTextField keySearch;
	SearchPanel searchPanel;
	JCheckBox indeff;	
	JSlider slider, rateSlider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJFrame frame = new MainJFrame();
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
		JFrame sideFrame = new JFrame();
		sideFrame.setSize(500, 500);
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = g.getScreenDevices();

		Integer mapW = devices[0].getDisplayMode().getWidth();
		Integer mapH = devices[0].getDisplayMode().getHeight();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setSize(mapW, mapH);
		this.setVisible(true);
		sideFrame.setVisible(true);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Dimension screenSize = new Dimension(mapW, mapH);
		System.out.println("X: " + screenSize.width +" Y: " + screenSize.height);
		contentPane.setLayout(new BorderLayout(0, 0));

		javax.swing.JPanel Map = new javax.swing.JPanel();
		contentPane.add(Map, BorderLayout.WEST);
		Twitter sketch = new Twitter(this, screenSize);
		tweetMap = sketch.getTweetMap();
		Map.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//sketch.screen = new Dimension(950, 700);
		Map.add(sketch);
		searchPanel = new SearchPanel(this); 
		rightSide = new JPanel();

		JPanel Menue = new JPanel();

		JPanel menue2 = new JPanel();

		JPanel words = new JPanel();

		JPanel Tweet = new JPanel();

		JPanel SearchFilter = new JPanel();




		textPane = new JTextPane(); 


		textPane.setEditable(false);

		textPane.setBackground(this.getBackground());

		keySearch = new JTextField();
		keySearch.setColumns(10); 
		keySearch.setEditable(true);

		searchLabel = new JLabel ("Search terms:");


		JButton searchButton = new JButton ("Submit");

		ActionListener submitListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String [] terms = new String[1];
				terms[0] = keySearch.getText();
				System.out.println(keySearch.getText());
			}
		};

		searchButton.addActionListener(submitListener);

		SearchFilter.add(searchLabel);
		SearchFilter.add(keySearch);
		SearchFilter.add(searchButton);


		topWords = new JTextPane();
		topWords.setEditable(false);

		topWords.setBackground(this.getBackground());


		Tweet.add(textPane);
		sideFrame.add(Tweet, BorderLayout.SOUTH);
		sideFrame.add(rightSide, BorderLayout.EAST);
		rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
		rightSide.add(Menue);
		rightSide.add(menue2);
		rightSide.add(words);
		rightSide.add(searchPanel);
		words.add(topWords);

		JLabel lblTweetDisplayTime = new JLabel("Tweet Display Time   ");
		JLabel lblTweetRate =        new JLabel("Tweet Percentage Rate");
		menue2.add(lblTweetRate);
		Menue.add(lblTweetDisplayTime);



		slider = new JSlider(0,200,30);
		rateSlider = new JSlider(0, 100, 30);


		slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				//if (!source.getValueIsAdjusting()) {
				int millis = ((int)slider.getValue())*1000;    
				if (!indeff.isSelected()){
					tweetMap.setTimeToDisappear(millis);
				}
				timeLabel.setText(((Integer)(millis/1000)).toString());


				//}
		}
		});
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		Menue.add(slider);

		rateSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				int percent = (int)rateSlider.getValue();
				tweetMap.changeRate(percent);
				rateLabel.setText(percent + "%");
			}
		});
		rateSlider.setMajorTickSpacing(50);
		rateSlider.setMinorTickSpacing(10);
		rateSlider.setPaintTicks(true);
		rateSlider.setPaintLabels(true);

		menue2.add(rateSlider);

		rateLabel = new JLabel("30%");
		timeLabel = new JLabel("30");
		Menue.add(timeLabel);
		menue2.add(rateLabel);
		menue2.add(new JLabel("                        "));

		indeff = new JCheckBox("Indefinitly");

		ActionListener indeffListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (indeff.isSelected()){
					tweetMap.setTimeToDisappear(-1);
				}
				else{
					JSlider source = slider; 
					//if (!source.getValueIsAdjusting()) {
					int millis = ((int)source.getValue())*1000;    
					tweetMap.setTimeToDisappear(millis);
					timeLabel.setText(((Integer)(millis/1000)).toString());
				}

			}
		};

		indeff.addActionListener(indeffListener);

		Menue.add(indeff);
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
		topWords.setText(tweetMap.getTopWords(20));
	}

	public void updateFilter(){
		String terms[] = searchPanel.getTerms().toArray(new String[searchPanel.getTermsSize()]);
		tweetMap.parent.updateSearchTerm(terms);
	}

	public TweetMap getTweetMap(){
		return tweetMap;
	}
}
