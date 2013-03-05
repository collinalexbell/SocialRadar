package twitter;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class TermInfo {
	List<Status> statuses;
	List<Point2D.Double> points;
	int frequency;
	String term;
	
	TermInfo(int fq, String t){
		frequency = fq;
		term = t;
		statuses = new ArrayList<Status>();
		points = new ArrayList<Point2D.Double>();
	}
	
	public void increment(){
		frequency++;
	}
	
	public void addStatus(Status status){
		statuses.add(status);
		points.add(new Point2D.Double(status.getGeoLocation().getLongitude(), status.getGeoLocation().getLatitude()));
	}
	
}
