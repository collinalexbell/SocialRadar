package display;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class MapDisplay extends PApplet{
	// MAP OBJECT
	UnfoldingMap map;
	
	// PARENT FRAME and INDEX in that FRAME
	MapPanel parent;
	
	// DIMENSIONS
	Integer w, h;
	
	MapDisplay(MapPanel p, Integer _w, Integer _h){
		// INITIALIZATIION LIST
		parent = p;
		w = _w;
		h = _h;
		
	}
	
	public void setSize(Integer _w, Integer _h){
		w = _w;
		h = _h;
	}
	
	public void setup(){
		// SET FRAME SIZE as dictated by W and H
		size(w,h);
		map = new UnfoldingMap(this, new OpenStreetMap.CloudmadeProvider("8e87064c01204cf4a69d66fb7cd07f8a", 91577));
		MapUtils.createDefaultEventDispatcher(this, map);
		System.out.println("In setup");
	}
	
	public void draw(){
		map.draw();
	}
}
