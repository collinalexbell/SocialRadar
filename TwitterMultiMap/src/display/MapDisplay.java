package display;

import java.util.ArrayList;

import data.DisplayInstance;
import data.HeatMap;
import data.LiveMap;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import twitter.Connection;

public class MapDisplay extends PApplet{
	// MAP OBJECT
	ArrayList<UnfoldingMap> maps;
	Integer numMaps = 0;
	ArrayList<DisplayInstance> displayInstances;
	
	// PARENT FRAME and INDEX in that FRAME
	MapPanel parent;
	
	// DIMENSIONS
	Integer w, h;
	
	// Twitter Connection (2 connections in one)
	Connection connection;
	
	//Cycle Variables
	private Integer cycleTime;
	private boolean isCycle;
	long startTime;
	long duration;
	int currentInstance;
	
	MapDisplay(MapPanel p, Integer _w, Integer _h){
		// INITIALIZATIION LIST
		parent = p;
		w = _w;
		h = _h;
		
		// Create arrays
		maps = new ArrayList<UnfoldingMap>();
		displayInstances = new ArrayList<DisplayInstance>();
		
		//Cycle init
		isCycle = false;
		startTime = 0;
		currentInstance = 0;

		
		//INIT connection
		
	}
	
	public void setSize(Integer _w, Integer _h){
		w = _w;
		h = _h;
	}
	
	public void setup(){
		// SET FRAME SIZE as dictated by W and H
		size(w,h);
		numMaps++;
		addMap();
	}
	
	public void draw(){
		if (isCycle){
			nextCycle();
		}
		background(0);
		for (UnfoldingMap map:maps){
			map.draw();
			if(map instanceof HeatMap){
				((HeatMap)map).drawMe();
			}
			if(map instanceof LiveMap){
				((LiveMap)map).drawMe();
			}
		}
	}
	
	public void addMap(){
			//------ADD HEAT MAP- last arg is resolution
			maps.add(new LiveMap(this,"map2", 0,h/2,w,h/2));
			Location knoxville = new Location(35.972778, -83.942222);
			maps.get(0).zoomAndPanTo(knoxville, 9);
			maps.add(new HeatMap(this, 0,0,w,h/2,new OpenStreetMap.CloudmadeProvider("8e87064c01204cf4a69d66fb7cd07f8a", 91577),.5,(LiveMap)maps.get(0)));
			maps.get(1).zoomAndPanTo(new Location(15,10), 2);
			//maps.add(new UnfoldingMap(this, 0,0,w,2*h/3,new OpenStreetMap.CloudmadeProvider("8e87064c01204cf4a69d66fb7cd07f8a", 73072)));
			//maps.get(2).zoomAndPanTo(new Location(39.099, -94.578), 5);
		for (UnfoldingMap map: maps){
			MapUtils.createDefaultEventDispatcher(this, map);
		}
		connection = new Connection((HeatMap)maps.get(1), (LiveMap)maps.get(0));
		
	}
	
	public void saveInstance(){
		Location lmLocation;
		Location hmLocation;
		int lmZoom;
		int hmZoom;	
		
		//Get the Live map Location and Zoom;
		lmLocation = maps.get(0).getCenter();
		lmZoom = maps.get(0).getZoomLevel();
		
		//Get the Heat map Location and Zoom;
		hmLocation = maps.get(1).getCenter();
		hmZoom = maps.get(1).getZoomLevel();
		
		displayInstances.add(new DisplayInstance(lmLocation, hmLocation, lmZoom, hmZoom));
	}
	
	public void loadInstance(){
		Location lmLocation;
		Location hmLocation;
		int lmZoom;
		int hmZoom;	
		
		DisplayInstance current = displayInstances.get((displayInstances.size()-1));
		lmLocation = current.getLmLocation();
		hmLocation = current.getHmLocation();
		lmZoom = current.getLmZoom();
		hmZoom = current.getHmZoom();
		
		maps.get(0).zoomAndPanTo(lmLocation,lmZoom);
		maps.get(1).zoomAndPanTo(hmLocation, hmZoom);
	}
	
	public void loadNextInstance(){
		Location lmLocation;
		Location hmLocation;
		int lmZoom;
		int hmZoom;	
		
		if (currentInstance >= displayInstances.size()){
			currentInstance = 0;
		}
		else{
			DisplayInstance current = displayInstances.get(currentInstance);
			lmLocation = current.getLmLocation();
			hmLocation = current.getHmLocation();
			lmZoom = current.getLmZoom();
			hmZoom = current.getHmZoom();

			maps.get(0).zoomAndPanTo(lmLocation,lmZoom);
			maps.get(1).zoomAndPanTo(hmLocation, hmZoom);
			currentInstance++;
		}
	}
	
	public void cycleInstances(Integer seconds){
		isCycle = true;
		cycleTime = seconds;
	}
	
	public void nextCycle(){
		if (startTime == 0){
			startTime = System.currentTimeMillis();
		}
		duration = System.currentTimeMillis() - startTime;
		if (duration >= cycleTime){
			loadNextInstance();
			startTime = 0;
		}
		
	}
	
	
}
