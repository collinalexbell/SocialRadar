package data;

import de.fhpotsdam.unfolding.geo.Location;

public class Tweet {
	Location location; // Lat, Lon WITH negatives
	String text;
	
	public Tweet(Location location, String text){
		this.location = location;
		this.text = text;
				
	}
}
