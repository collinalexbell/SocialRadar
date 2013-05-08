package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

import processing.core.PApplet;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;

public class HeatMap extends UnfoldingMap{
	Double resolution;
	ArrayList<ArrayList<HeatBin>> bins; // Used to index bins when needed by geo-order
	TreeMap<Integer, ArrayList<HeatBin>> sortedBins; //Used to index bins based off of tweet count
	Integer numLat, numLon;
	
	public HeatMap(PApplet parent,float a, float b, float c, float d, AbstractMapProvider prov, Double res){
		super(parent,a,b,c,d,prov);
		resolution = res;
		buildBins();
	}
	
	public void buildBins(){
		numLat = (int) (180/resolution); 
		numLon = (int) (360/resolution);
		
		
		bins = new ArrayList<ArrayList<HeatBin>>();
		for (int i = 0; i < numLat; i++){
			bins.add(new ArrayList<HeatBin>());
			for (int j = 0; j < numLon; j++){
				bins.get(i).add(new HeatBin());
			}
		}
	}
	
	public void print(){
		//System.out.println("HeatMap totalSize: " + bins.size()*bins.get(0).size());
	}
	
	public void addTweet(Tweet tweet){
		float tweetLat = tweet.location.x;
		float tweetLon = tweet.location.y;
		//Handle Negatives
		if (tweetLat < 0){
			tweetLat = Math.abs(tweetLat) + 90;
		}
		if (tweetLon < 0){
			tweetLon = Math.abs(tweetLon) + 180;
		}
		
		int latIndex =(int) (Math.floor((tweetLat + (resolution/2))/resolution));
		int lonIndex =(int) (Math.floor((tweetLon + (resolution/2))/resolution));
		
		//Handle Wraparound
		if (latIndex == 180){latIndex = 0;}
		if (lonIndex == 360){lonIndex = 0;}
		
		//System.out.print("Latitude Index:" + latIndex);
		//System.out.print("Longitude Index:" + lonIndex);
		//bins.get(latIndex).get(lonIndex).addTweet(tweet);
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//HeatMap myHeatMap = new HeatMap(1.0);
		/*while (true){
			System.out.print("Enter Lat: ");
			Double x = Double.parseDouble(br.readLine());
			System.out.print("Enter Lon: ");
			Double y = Double.parseDouble(br.readLine());
			myHeatMap.addTweet(new Tweet(new Location(x, y), "Hello World"));
		}
		*/
	}

}
