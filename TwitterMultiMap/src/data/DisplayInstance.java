package data;

import de.fhpotsdam.unfolding.geo.Location;

public class DisplayInstance {
	Location lmLocation;
	Location hmLocation;
	int lmZoom;
	int hmZoom;
	
	public DisplayInstance(Location lmLocation, Location hmLocation, int lmZoom, int hmZoom){
		this.lmLocation = lmLocation;
		this.hmLocation = hmLocation;
		this.lmZoom = lmZoom;
		this.hmZoom = hmZoom;
	}
	
	public Location getLmLocation(){
		return lmLocation;
	}
	
	public Location getHmLocation(){
		return hmLocation;
	}
	
	public int getLmZoom(){
		return lmZoom;
	}
	
	public int getHmZoom(){
		return hmZoom;
	}
	
	
	
}
