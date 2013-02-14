package twitter;

import processing.core.PApplet;
import processing.*;

public class Menu {
	PApplet parent;
	
	public Menu(PApplet p){
		parent = p;
		parent.fill(255);
		parent.rect(0, 700, 1700, 200);
	}
	
	public void clear(){
		parent.fill(255);
		parent.rect(0, 700, 1700, 200);
	}
	
	public void drawTweet(String text, int x, int y, int w, int h){
		
		parent.fill(0);
		parent.text("Tweet: ",x, 700+ y, w, h);
		parent.text(text, x + 50, 700+ y, w, h);
	}
	
	public void drawAuthor(String text, int x, int y, int w, int h){
		
		parent.fill(0);
		parent.text("Author: ",x, 700+ y, w, h);
		parent.text(text, x + 50, 700+ y, w, h);
	}
	
	public void drawTime(String text, int x, int y, int w, int h){
		parent.fill(0);
		parent.text("Time: ",x, 700+ y, w, h);
		parent.text(text, x + 50, 700+ y, w, h);
	}
}
