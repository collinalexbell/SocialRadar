package data;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

import processing.core.PApplet;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.utils.ScreenPosition;

public class HeatMap extends UnfoldingMap{
	Double resolution;
	ArrayList<ArrayList<HeatBin>> bins; // Used to index bins when needed by geo-order
	TreeMap<Integer, ArrayList<HeatBin>> sortedBins; //Used to index bins based off of tweet count
	Integer numLat, numLon;
	ArrayList<ArrayList<Point2D>> debins;
	LiveMap liveMap;
	
	public HeatMap(PApplet parent,float a, float b, float c, float d, AbstractMapProvider prov, Double res, LiveMap l){
		super(parent,a,b,c,d,prov);
		resolution = res;
		buildBins();
		liveMap = l;
	}
	
	public void buildBins(){
		numLat = (int) (180/resolution); 
		numLon = (int) (360/resolution);
		
		
		bins = new ArrayList<ArrayList<HeatBin>>();
		debins = new ArrayList<ArrayList<Point2D>>();
		for (int i = 0; i < numLat; i++){
			bins.add(new ArrayList<HeatBin>());
			debins.add(new ArrayList<Point2D>());
			for (int j = 0; j < numLon; j++){
				bins.get(i).add(new HeatBin(this));
				debins.get(i).add(debinify(i,j));
			}
		}
	}
	
	public void print(){
		//System.out.println("HeatMap totalSize: " + bins.size()*bins.get(0).size());
	}
	
	public void addTweet(Tweet tweet){
		
		int indices[] = binify(tweet.location.x, tweet.location.y);
		bins.get(indices[0]).get(indices[1]).addTweet(tweet);
	}
	
	public void drawMe(){
		for (int i = 0; i < bins.size(); i++){
			for (int j = 0; j < bins.get(0).size(); j++){
				if (bins.get(i).get(j).getSize() > 0){
					Location loc = new Location (debins.get(i).get(j).getX(),debins.get(i).get(j).getY());
					ScreenPosition pos = getScreenPosition(loc);
					p.stroke(255,255,255);
					p.fill(255,255,255);
					p.ellipse(pos.x,pos.y, 2, 2);
				}
			}
		}
		drawLiveBox();
	}
	
	public void drawLiveBox(){
		// Used to find bound box
		Location topLeft = liveMap.getTopLeftBorder();
		Location botRight = liveMap.getBottomRightBorder();
		
		//Convert bound box to pixels
		ScreenPosition topLeftPos = this.getScreenPosition(topLeft);
		ScreenPosition botRightPos = this.getScreenPosition(botRight);
		
		//Translate two points to a point and a width
		float boxWidth = botRightPos.x-topLeftPos.x;
		float boxHeight = botRightPos.y-topLeftPos.y;
		
		p.stroke(119,255,115);
		p.fill(255,255,255,0);
		p.rect(topLeftPos.x, topLeftPos.y, boxWidth, boxHeight);
		
		
	}

	
	public int[] binify(float tweetLat, float tweetLon){
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
		
		int rv[] = {latIndex, lonIndex};
		return rv;
		//System.out.print("Latitude Index:" + latIndex);
		//System.out.print("Longitude Index:" + lonIndex);
		//bins.get(latIndex).get(lonIndex).addTweet(tweet);
	}
	
	public Point2D debinify(int tweetLat, int tweetLon){
		double lat = tweetLat * resolution;
		double lon = tweetLon * resolution;
		
		//Handle Negatives
		if (lat > 90){
			lat = -1*(lat-90);
		}
		if (lon > 180){
			lon = -1*(lon-180);
		}
		
		
		Point2D rv = new Point2D.Double(lat, lon);
		return rv;
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
