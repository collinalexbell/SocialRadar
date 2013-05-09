package data;

import java.util.ArrayList;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;

public class LiveMap extends UnfoldingMap {

	ArrayList<Tweet> tweets;
	
	public LiveMap(PApplet parent, String s, float a, float b, float c, float d){
		super(parent,s, a,b,c,d);
		tweets = new ArrayList<Tweet>();
	}
	
	public void addTweet(Tweet tweet){
		tweets.add(tweet);
	}
	
	public void draw(){
		
	}
}
