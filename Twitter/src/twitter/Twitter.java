package twitter;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.mapdisplay.*;
import de.fhpotsdam.unfolding.utils.*;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.tiles.*;
import de.fhpotsdam.unfolding.interactions.*;
import de.fhpotsdam.unfolding.ui.*;
import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.core.*;
import de.fhpotsdam.unfolding.data.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.texture.*;
import de.fhpotsdam.unfolding.events.*;
import de.fhpotsdam.utils.*;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.marker.MarkerManager;

import processing.core.*;


import processing.opengl.*;
import codeanticode.glgraphics.*;

import twitter4j.conf.*;
import twitter4j.internal.async.*;
import twitter4j.internal.org.json.*;
import twitter4j.internal.logging.*;
import twitter4j.api.*;
import twitter4j.util.*;
import twitter4j.internal.http.*;
import twitter4j.*;

public class Twitter extends PApplet{
MarkerManager<Marker> markerManager; //= new MarkerManager<Marker>();
static String OAuthConsumerKey;// = "5ZgQXBGYo4YSKLYxqM1XEA";
static String OAuthConsumerSecret;// = "E9nSuU2uJ2IAz0YavqdfT4fIJAhMcXC4gJkD94qVAs";
static String AccessToken;// = "426783140-ffLY7B4oRWwjNil6lXDdGtfKLxC5wg1hWtuYJNfy";
static String AccessTokenSecret;// = "xso5cpcuuU9FA4mQEm9OBzDhx83p0DMMZDJiaXuWTBM";

//Twitter twitter = new TwitterFactory().getInstance();

List<PingMarker> markerstodisplay;


UnfoldingMap map;
TweetMap tweetMap;
Menu bottomMenu;
	
	 public static void main(String args[]) {
		    PApplet.main(new String[] { "twitter.Twitter" });
		  }

public void removeIt(PingMarker marker){
 markerManager.removeMarker(marker); 
}







public void setup() {
  size(1700,900);
 
  

  
  markerManager = new MarkerManager<Marker>();
  bottomMenu = new Menu(this);

  map = new UnfoldingMap(this, "map", 750,0,950,700);
  map.addMarkerManager(markerManager);
  map.setZoomRange(2,6);
  MapUtils.createDefaultEventDispatcher(this, map);
  
  tweetMap = new TweetMap(this, markerManager);
  
  OAuthConsumerKey = "5ZgQXBGYo4YSKLYxqM1XEA";
  OAuthConsumerSecret = "E9nSuU2uJ2IAz0YavqdfT4fIJAhMcXC4gJkD94qVAs";
  AccessToken = "426783140-ffLY7B4oRWwjNil6lXDdGtfKLxC5wg1hWtuYJNfy";
  AccessTokenSecret = "xso5cpcuuU9FA4mQEm9OBzDhx83p0DMMZDJiaXuWTBM";
  
  


  de.fhpotsdam.unfolding.geo.Location berlinLocation = new de.fhpotsdam.unfolding.geo.Location(52.5, 13.4);
  de.fhpotsdam.unfolding.geo.Location dublinLocation = new de.fhpotsdam.unfolding.geo.Location(53.35, -6.26);

  // Create point markers for locations
  SimplePointMarker berlinMarker = new SimplePointMarker(berlinLocation);
  
  berlinMarker.setColor(color(255,0,0,100));
  berlinMarker.setStrokeWeight(1);

  // Add markers to the maps 

  

  
  ConfigurationBuilder cb = new ConfigurationBuilder();
  cb.setDebugEnabled(true);
  cb.setOAuthConsumerKey(OAuthConsumerKey);
  cb.setOAuthConsumerSecret(OAuthConsumerSecret);
  cb.setOAuthAccessToken(AccessToken);
  cb.setOAuthAccessTokenSecret(AccessTokenSecret);
  
      	TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
//        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
//            	if (status.getUser().getLang().equals("en")) {
            	 if(status.getGeoLocation()!= null){
                   tweetMap.addStatus(status);
                 }	
//            	} else {
//            		System.out.println("NON-ENGLISH");
//            		System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
//            	}
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
//                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            public void onScrubGeo(long userId, long upToStatusId) {
//                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
           
            public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

        };
        
        twitterStream.addListener(listener);
        twitterStream.sample();
  
}








public void draw() {
  float ymax, ymin, xmax, xmin;
  Status currentStatus;
  
  

  map.draw();
  tweetMap.draw();
  
  
  if (tweetMap.statusAvailable()){
    currentStatus = tweetMap.getStatus();
    GeoLocation location = currentStatus.getGeoLocation();
    double lat = location.getLatitude();
    double lon = location.getLongitude();
    
    de.fhpotsdam.unfolding.geo.Location newLocation = new de.fhpotsdam.unfolding.geo.Location(lat, lon);
    PingMarker tweetMarker = new PingMarker(this, newLocation, tweetMap);
    tweetMarker.status = currentStatus;
    markerManager.addMarker(tweetMarker);
  }
  

  
  
  
  tweetMap.deleteMarkers();
  
  List<Marker> markers;
  markers = markerManager.getMarkers();
  de.fhpotsdam.unfolding.geo.Location location = map.getLocation(mouseX, mouseY);
  for (int i = 0; i < markers.size(); i++){
    de.fhpotsdam.unfolding.geo.Location markerLocation = markers.get(i).getLocation();
    ymax = markerLocation.getLat()+1;
    ymin = markerLocation.getLat()-1;
    xmax = markerLocation.getLon()+1;
    xmin = markerLocation.getLon()-1;
    if (location.getLat() < ymax && location.getLat() > ymin && location.getLon() < xmax && location.getLon() > xmin){
      markers.get(i).setSelected(true);
    }
    else{ 
      markers.get(i).setSelected(false);
    }
  }
  
  tweetMap.draw();
  tweetMap.markerstodisplay.clear();
  
  
}

}