/**********************************
 * Social Radar
 * 
 * (C) Copyright 2013
 * 
 * Collin Bell
 * Oak Ridge National Lab
 * University of Tennessee
 * 
 * All rights reserved
 */


package twitter;


import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

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
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.texture.*;
import de.fhpotsdam.unfolding.events.*;
import de.fhpotsdam.utils.*;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.providers.OpenStreetMap.CloudmadeProvider;

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
	MarkerManager<Marker> markerManager = new MarkerManager<Marker>();
	static String OAuthConsumerKey = "5ZgQXBGYo4YSKLYxqM1XEA";
	static String OAuthConsumerSecret = "E9nSuU2uJ2IAz0YavqdfT4fIJAhMcXC4gJkD94qVAs";
	static String AccessToken  = "426783140-ffLY7B4oRWwjNil6lXDdGtfKLxC5wg1hWtuYJNfy";
    static String AccessTokenSecret = "xso5cpcuuU9FA4mQEm9OBzDhx83p0DMMZDJiaXuWTBM";

	//Twitter twitter = new TwitterFactory().getInstance();

	List<PingMarker> markerstodisplay;


	UnfoldingMap map;
	TweetMap tweetMap;
	MainJFrame parent;
	TwitterStream twitterStream;
	FilterQuery filterQuery;
	Dimension screenSize;

	Twitter(MainJFrame p, Dimension ss){
		parent = p;
		markerManager = new MarkerManager<Marker>();
		tweetMap = new TweetMap(this, markerManager, parent);
		screenSize = ss;
	}



	public void removeIt(PingMarker marker){
		markerManager.removeMarker(marker); 
	}





	public TweetMap getTweetMap(){
		return tweetMap;
	}

	public void setup() {
		size(((screenSize.width-100)/2), ((screenSize.width-100))/2);






		map = new UnfoldingMap(this, "map1");
		map.addMarkerManager(markerManager);
		//map.setZoomRange(2,6);
		MapUtils.createDefaultEventDispatcher(this, map);



		//OAuthConsumerKey = "CKey";
		//OAuthConsumerSecret = "CSecret";
		//AccessToken ="AToken";
		//AccessTokenSecret = "ATSecret";




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

		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		//        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				tweetMap.sum += tweetMap.rate;
				//            	if (status.getUser().getLang().equals("en")) {
				if(status.getGeoLocation()!= null && tweetMap.sum >= 1){
					tweetMap.addStatus(status);
					tweetMap.sum = 0.0;
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

		//      filterQuery.locations(locations);

		filterQuery = new FilterQuery();
		double[] knoxville = {-83.942222, 35.972778};
	//	double[][] locations ={{-180, -90}, {180, 90}};
		double[][] locations ={{knoxville[0]-.1 , knoxville[1]-.1}, {knoxville[0]+.1, knoxville[1]+.1}};
		
		
		filterQuery.locations(locations);
		

		twitterStream.filter(filterQuery);
		//twitterStream.sample();
	}








	public void draw() {
		float ymax, ymin, xmax, xmin;
		Status currentStatus;



		map.draw();
		tweetMap.draw();

		int count = 0;
		while (tweetMap.statusAvailable()){
			currentStatus = tweetMap.getStatus();
			GeoLocation location = currentStatus.getGeoLocation();
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			Color color = tweetMap.getStatusColor(currentStatus);

			de.fhpotsdam.unfolding.geo.Location newLocation = new de.fhpotsdam.unfolding.geo.Location(lat, lon);
			PingMarker tweetMarker = new PingMarker(this, newLocation, tweetMap, currentStatus, color);
			tweetMarker.status = currentStatus;
			markerManager.addMarker(tweetMarker);
			if(count == 5){
				break;
			}
			count++;
		}





		tweetMap.deleteMarkers();

		List<Marker> markers;
		markers = markerManager.getMarkers();
		de.fhpotsdam.unfolding.geo.Location location = map.getLocation(mouseX, mouseY);
		for (int i = 0; i < markers.size(); i++){
			de.fhpotsdam.unfolding.geo.Location markerLocation = markers.get(i).getLocation();
			ScreenPosition loc = map.getScreenPosition(markerLocation);
			
			ymax = loc.y+5;
			ymin = loc.y-5;
			xmax = loc.x+5;
			xmin = loc.x-5;
			if (mouseY < ymax && mouseY > ymin && mouseX < xmax && mouseX > xmin){
				markers.get(i).setSelected(true);
			}
			else{ 
				markers.get(i).setSelected(false);
			}
		}

		tweetMap.draw();
		tweetMap.markerstodisplay.clear();


	}
	
	public void updateSearchTerm(String [] terms){
		if (terms.length > 0){
			FilterQuery filterQuery = new FilterQuery();
			filterQuery.track(terms);
			twitterStream.filter(filterQuery);
		}
		else{
			twitterStream.sample();
		}
	}



	public void addBoundLocation(Integer x1, Integer y1, Integer width, Integer height) {
		Location bottomLeft = map.getLocation(x1, y1+height);
		Location topRight = map.getLocation(x1+width, y1);
		FilterQuery filterQuery = new FilterQuery();
		double [][] locations = {{bottomLeft.getLon(), bottomLeft.getLat()}, {topRight.getLon(), topRight.getLat()}};
		filterQuery.locations(locations);
		twitterStream.filter(filterQuery);
	}
}