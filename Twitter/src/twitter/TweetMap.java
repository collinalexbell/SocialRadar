package twitter;

import search.SearchTerm;
import twitter4j.*;

import java.awt.Color;
import java.util.*;

import javax.swing.JFrame;

import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.geo.Location;

import processing.core.*;

public class TweetMap{
	  Twitter parent;
	  ArrayList<Status> statuses;
	  ArrayList<PingMarker> markersToDelete;
	  //ArrayList<Ping> pings;
	  int size;
	  List<PingMarker> markerstodisplay;
	  MarkerManager<Marker> markerManager;
	 MainJFrame frame;
	 int pingTime;
	 TopWord wordfq;
	 List<SearchTerm> terms;
	 Integer x1, x2, y1, y2;
     Integer xreal, yreal, width, height;
     boolean pressed = false;
     public Double rate;
     public Double sum;
	  
	  TweetMap(Twitter p, MarkerManager<Marker> m, MainJFrame f){
		sum = 0.0;
		rate = 0.3;
	    pingTime = 30000;
		parent = p;
	    frame = f;
	    markerstodisplay = new ArrayList<PingMarker>();
		size = 0;
		markerManager = m;
	    statuses = new ArrayList<Status>();
	    markersToDelete = new ArrayList<PingMarker>();
	    wordfq = new TopWord(this);
	    terms = new ArrayList<SearchTerm>();
	    
	    //pings = new ArrayList<Ping>();
	  }
	  void addMarkersToDelete(PingMarker marker){
	    markersToDelete.add(marker);
	  }
	  void deleteMarkers(){
	    for (int i = 0; i < markersToDelete.size(); i++){
	      if(markerManager.removeMarker(markersToDelete.get(i))){
	       //System.out.println("Removed Marker"); 
	       if(statuses.remove(markersToDelete.get(i).status)){
	    	 //  System.out.println("Removed Status");
	    	   size--;
	       }
	      }
	    }
	    markersToDelete.clear();
	  }
	  
	  void addStatus(Status status){
	    statuses.add(status);
	    wordfq.addStatus(status);
	    //wordfq.printTopWords(10);
	    frame.updateTopWord();
	    //de.fhpotsdam.unfolding.geo.Location myloc = new  de.fhpotsdam.unfolding.geo.Location(status.getGeoLocation().getLatitude(), status.getGeoLocation().getLongitude());
	    //ScreenPosition pos = map.getScreenPosition(myloc);
	    //pings.add(new Ping(pos.x, pos.y ,20));
	    //System.out.println(pos.x);
	  }
	  Status getStatus(){
	    if (size < statuses.size() && !statuses.isEmpty()){
	      Status rv = statuses.get(size);
	      size += 1;
	      return rv;
	     
	    }
	    else return null;
	  }
	  
	  boolean statusAvailable(){
	    if (size < statuses.size()){
	      return true;
	    }
	    else{ return false;}
	  }
	  
	  public void displayTweet(List<PingMarker> markers){
		  String time;
		  if (markers.size() == 0) return;
		  //parent.fill(0);
		  
		  frame.updateTweet(markers.get(0).status.getText());
		  //bottomMenu.drawAuthor(markers.get(0).status.getUser().getName()+ ", @" + markers.get(0).status.getUser().getScreenName(), 100, 40, 500, 50);
		  //bottomMenu.drawTime(time, 100, 70, 500, 50);
		  if (markers.size() > 1){
		    //Make a special selection menu
		  }
		}
	  
	  public void draw(){
		  displayTweet(markerstodisplay);
		  drawBoundingBox();
	  }
	  
	  public void setTimeToDisappear(int millsec){
		  if(millsec != 0){
			  pingTime = millsec;
			  List<Marker> l = markerManager.getMarkers();
			  for (Marker m: l){
				  if(m instanceof PingMarker){
					  PingMarker marker = (PingMarker)m;
					  marker.changeTimeToDisappear(millsec);
					 
				  }
			  }
		  }
		  else{
			  pingTime = -1;
			  List<Marker> l = markerManager.getMarkers();
			  for (Marker m: l){
				  if(m instanceof PingMarker){
					  PingMarker marker = (PingMarker)m;
					  marker.changeTimeToDisappear(1000);
					 
				  }
			  }
		  }
	  }
	  
	  public String getTopWords(int i){
		  return wordfq.stringTopWords(i);
	  }
	public Color getStatusColor(Status currentStatus) {
		String text = currentStatus.getText();
		text = text.toLowerCase();
		if (terms.size() == 0){
			return new Color(255, 255, 255, 150);
		}else{
			for (SearchTerm t: terms){
				
				if(text.contains(t.getTerm().toLowerCase())){
					return t.getColor();
				}
			}
		}
		return new Color(255, 255, 255, 150);
	}
	
	public void updateSearchTerms(List<SearchTerm> terms){
		this.terms = terms;
	}
	
	public void drawBoundingBox(){
		if (parent.mousePressed && parent.mouseButton == parent.LEFT && !pressed && parent.keyPressed == true){
			x1 = x2 = parent.mouseX;
			y1 = y2 = parent.mouseY;
			pressed = true;
			parent.map.setPanningRestriction(parent.map.getCenter(), 0);
		}
		
		if (parent.mousePressed && pressed){
			x2 = parent.mouseX;
			y2 = parent.mouseY;

				if (x1 < x2){
					xreal = x1;
					width = x2-x1;
				}else{
					xreal = x2;
					width = x1 - x2;

				}
				if (y1 < y2){
					yreal = y1;
					height = y2-y1;
				}else{
					yreal = y2;
					height = y1-y2;
				}
				parent.noFill();
				parent.stroke(0);
				parent.rect(xreal, yreal, width, height);
		}
		if (!parent.mousePressed && pressed){
			parent.addBoundLocation(xreal,yreal, width, height);
			parent.map.setPanningRestriction(new Location(0,0), 12000);
			pressed = false;
			System.out.println("SetBound");
	    }
	}
	
	public void changeRate(int r){
		rate = ((double)(r))/100;
		System.out.println(r + "   "+ rate);
	}

	/*ArrayList<Ping> getPings(){
	    return pings;
	  }*/
	  
	  /*void drawTweetMap(){
	    for (int i = 0; i < pings.size(); i++){
	      Ping currentPing = pings.get(i);
	      fill(255);
	      ellipse(currentPing.x, currentPing.y, currentPing.r, currentPing.r);
	    }
	    
	  }*/
	  
	  /*void update(){
	    for (int i = 0; i < pings.size(); i++){
	      pings.get(i).animate();
	      if (pings.get(i).r == 0){
	        pings.remove(i);
	      } 
	    }
	  }*/
	  

	}