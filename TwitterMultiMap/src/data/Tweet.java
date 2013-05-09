package data;

import de.fhpotsdam.unfolding.geo.Location;

public class Tweet {
	Location location; // Lat, Lon WITH negatives
	String text;
	int fade;
	
	public Tweet(Location location, String text){
		fade = 0;
		this.location = location;
		this.text = text;
				
	}
	
	public void setFade(int f){
		fade = f;
	}
	
	public int fade(){
		int rv = fade;
		if (fade > 0){
			fade--;
		}
		return rv;
		
	}
}
