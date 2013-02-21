package twitter;

import twitter4j.*;
import java.util.*;

import javax.swing.JFrame;

import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;

import processing.core.*;

public class TweetMap{
	  PApplet parent;
	  ArrayList<Status> statuses;
	  ArrayList<PingMarker> markersToDelete;
	  //ArrayList<Ping> pings;
	  int size;
	  List<PingMarker> markerstodisplay;
	  MarkerManager<Marker> markerManager;
	 MainJFrame frame;
	 int pingTime;
	 TopWord wordfq;
	  
	  TweetMap(PApplet p, MarkerManager<Marker> m, MainJFrame f){
	    pingTime = 30000;
		parent = p;
	    frame = f;
	    markerstodisplay = new ArrayList<PingMarker>();
		size = 0;
		markerManager = m;
	    statuses = new ArrayList<Status>();
	    markersToDelete = new ArrayList<PingMarker>();
	    wordfq = new TopWord(this);
	    //pings = new ArrayList<Ping>();
	  }
	  void addMarkersToDelete(PingMarker marker){
	    markersToDelete.add(marker);
	  }
	  void deleteMarkers(){
	    for (int i = 0; i < markersToDelete.size(); i++){
	      if(markerManager.removeMarker(markersToDelete.get(i))){
	       System.out.println("Removed Marker"); 
	       if(statuses.remove(markersToDelete.get(i).status)){
	    	   System.out.println("Removed Status");
	       }
	      }
	    }
	    markersToDelete.clear();
	  }
	  
	  void addStatus(Status status){
	    statuses.add(status);
	    wordfq.addStatus(status);
	    wordfq.printTopWords(10);
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
		  parent.fill(0);
		  
		  frame.updateTweet(markers.get(0).status.getText());
		  //bottomMenu.drawAuthor(markers.get(0).status.getUser().getName()+ ", @" + markers.get(0).status.getUser().getScreenName(), 100, 40, 500, 50);
		  //bottomMenu.drawTime(time, 100, 70, 500, 50);
		  if (markers.size() > 1){
		    //Make a special selection menu
		  }
		}
	  
	  public void draw(){
		  displayTweet(markerstodisplay);
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
			  pingTime = 1000;
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