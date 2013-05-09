package display;

import java.util.ArrayList;

import data.HeatMap;
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
	
	// PARENT FRAME and INDEX in that FRAME
	MapPanel parent;
	
	// DIMENSIONS
	Integer w, h;
	
	// Twitter Connection (2 connections in one)
	Connection connection;
	
	MapDisplay(MapPanel p, Integer _w, Integer _h){
		// INITIALIZATIION LIST
		parent = p;
		w = _w;
		h = _h;
		maps = new ArrayList<UnfoldingMap>();
		
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
		background(0);
		for (UnfoldingMap map:maps){
			map.draw();
			if(map instanceof HeatMap){
				((HeatMap)map).drawMe();
			}
		}
	}
	
	public void addMap(){
			//------ADD HEAT MAP- last arg is resolution
			maps.add(new HeatMap(this, 0,2*h/3,w/2,h/3,new OpenStreetMap.CloudmadeProvider("8e87064c01204cf4a69d66fb7cd07f8a", 91577),.5));
			maps.get(0).zoomAndPanTo(new Location(15,10), 2);
			maps.add(new UnfoldingMap(this,"map2", w/2,2*h/3,w/2,h/3));
			Location knoxville = new Location(35.972778, -83.942222);
			maps.get(1).zoomAndPanTo(knoxville, 10);
			maps.add(new UnfoldingMap(this, 0,0,w,2*h/3,new OpenStreetMap.CloudmadeProvider("8e87064c01204cf4a69d66fb7cd07f8a", 73072)));
			maps.get(2).zoomAndPanTo(new Location(39.099, -94.578), 5);
		for (UnfoldingMap map: maps){
			MapUtils.createDefaultEventDispatcher(this, map);
		}
		connection = new Connection((HeatMap)maps.get(0));
		
	}
}
