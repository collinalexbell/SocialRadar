package search;

import twitter.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bric.swing.ColorPicker;


public class SearchPanel extends JPanel{
	JLabel title;
	List<SearchTerm> searchTerms;
	List<SearchTerm> termsToRemove;
	JButton add, remove;
	JTextField key;
	ActionListener addListener, removeListener;
	MainJFrame parent;
	JPanel termListDisplayPanel;
	TweetMap tweetMap;
	
	public SearchPanel(MainJFrame p){
		setLayout(new BorderLayout());
		JPanel top = new JPanel();
		parent = p;
		title = new JLabel("Search Terms");
		searchTerms = new ArrayList<SearchTerm>();
		termsToRemove = new ArrayList<SearchTerm>();
		add = new JButton("Add");
		remove = new JButton("Remove Selected");
		key = new JTextField();
		key.setColumns(10);
		key.setEditable(true);
		tweetMap = parent.getTweetMap();
        
		addListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            	addTerm();
            }
          };
          
        removeListener = new ActionListener() {
        	public void actionPerformed(ActionEvent actionEven) {
        		termsToRemove.clear();
        		updateTermsToRemove();
        		//remove term
        		for (SearchTerm t : termsToRemove){
        			searchTerms.remove(t);
        		}
        		parent.updateFilter();
        		removeFromTermDisplay();
            	termListDisplayPanel.repaint();
            	revalidate();
            	repaint();
        	}
        };
          
        key.addKeyListener(new KeyAdapter(){


			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER){
					addTerm();
				}
				
			}
        	
        });
        remove.addActionListener(removeListener);
        add.addActionListener(addListener);
        top.add(title);
        top.add(key);
        termListDisplayPanel = new JPanel();
        termListDisplayPanel.setLayout(new WrapLayout());
        termListDisplayPanel.setSize(new Dimension(300, 1));
        updateTermDisplay();
        //Add term box
        top.add(add);
        top.add(remove);
        add(top, BorderLayout.NORTH);
        add(termListDisplayPanel, BorderLayout.CENTER);
        
	}
	
	public ArrayList<String> getTerms(){
		ArrayList<String> rv;
		rv = new ArrayList<String>();
		for (SearchTerm t : searchTerms){
			rv.add(t.term);
			
		}
		return rv;
	}
	
	public int getTermsSize(){
		return searchTerms.size();
	}
	
	private void updateTermDisplay(){
		for(SearchTerm t : searchTerms){
			termListDisplayPanel.add(t.checkBox);
		}
	}

	private void removeFromTermDisplay(){
		for (SearchTerm t: termsToRemove){
			termListDisplayPanel.remove(t.checkBox);
		}
	}
	
	private void addTerm(){
		Color color = ColorPicker.showDialog((JFrame)parent, new Color(255,255,255));
		searchTerms.add(new SearchTerm(key.getText(), parent, color));
		tweetMap.updateSearchTerms(searchTerms);
    	parent.updateFilter();
    	updateTermDisplay();
    	termListDisplayPanel.repaint();
    	revalidate();
    	repaint();
    	key.setText("");
	}
	
	private void updateTermsToRemove(){
		for (SearchTerm t: searchTerms){
			if (t.isChecked()){
				termsToRemove.add(t);
			}
		}
	}
}
