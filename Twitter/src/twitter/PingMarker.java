package twitter;


import java.awt.Color;
import java.util.Random;

import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.*;

import twitter4j.Status;



public class PingMarker extends SimplePointMarker {
	  int rad;
	  int transperancyRate; //Period of tansperancy point increase
	  int timeToDisapear;
	  int transValue;
	  float startTime;
	  PApplet parent;
	  Status status;
	  TweetMap tweetMap;
	  boolean disapear;
	  Color color;
	  int lineLength;
	  int dx, dy;
	  
	  public PingMarker(PApplet p, de.fhpotsdam.unfolding.geo.Location location, TweetMap t, Status s, Color c) {
	    super(location);
	    disapear = true;
	    status = s;
	    tweetMap = t;
	    parent = p;
	    rad = 10;
	    startTime = parent.millis();
	    timeToDisapear = t.pingTime;
	    transperancyRate = timeToDisapear/255;
	    transValue =250;
	    color = c;
	    lineLength = 9;
	    drawLine();
	    
	  }
	 
	  public void draw(PGraphics pg, float x, float y) {
	    pg.pushStyle();
	    pg.noStroke();

	    if (rad > 0){
	    	pg.fill(color.getRed(), color.getGreen(), color.getBlue(), 100);
	    	pg.ellipse(x, y, rad, rad);
	    	rad -= 1;
	    }
	    if(disapear && timeToDisapear > 0){
	    	if (transValue <= 255 && !isSelected()) transValue = 250-((int)(parent.millis()-startTime))/transperancyRate;
	    	if (transValue <=0){

	    		tweetMap.addMarkersToDelete(this);
	    	}
	    	pg.fill(color.getRed(), color.getGreen(), color.getBlue(), transValue);
	    }
	    else{
	    	pg.fill(color.getRed(), color.getGreen(), color.getBlue(), 250);
	    }
	    if(isSelected()){
	    	pg.fill(255, 136, 0);
	    	tweetMap.markerstodisplay.add(this);
	    }
	    pg.stroke(255,255,255, 15);
	    pg.line(x, y, x+dx, y+dy);
	    pg.stroke(255,255,255, 100);
	    pg.ellipse((int)Math.round(x),(int)Math.round(y), 1, 1);
	    pg.popStyle();

	  }

	  public void changeTimeToDisappear(int t){
		  if (t < 0){
			  disapear = false;
		  }
		  else{
			  disapear = true;
			  timeToDisapear = t;
			  transperancyRate = timeToDisapear/255;
		  }
	  }
	  
	  public void drawLine(){
		  Random random = new Random();
		  int deg = random.nextInt()%360;
		  dx = (int)(Math.cos(Math.toRadians(deg))*lineLength);
		  dy = (int)(Math.sin(Math.toRadians(deg))*lineLength);
		  System.out.println("deg: " + deg + ", dx: " + dx +", dy: " + dy);
	  }
}
