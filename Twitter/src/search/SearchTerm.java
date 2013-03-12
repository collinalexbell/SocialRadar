package search;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

import com.bric.swing.ColorPicker;

public class SearchTerm {
	JCheckBox checkBox;
	String term;
	JFrame frame;
	Color color;
	
	SearchTerm(String term, JFrame f, Color c){
		frame = f;
		this.color = c;
		this.term = term;
		checkBox = new JCheckBox(term);
		checkBox.setForeground(color);
		checkBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((JCheckBox)e.getSource()).isSelected()){
					color = ColorPicker.showDialog(frame, color);
					checkBox.setForeground(color);
				}
			}
		});
	}
	
	public boolean isChecked(){
		return checkBox.isSelected();
	}

	public String getTerm() {
		// TODO Auto-generated method stub
		return term;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}
	
	
	
}
