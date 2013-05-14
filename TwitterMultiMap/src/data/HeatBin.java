package data;

import java.util.ArrayList;

public class HeatBin {
	
	HeatMap parent;
	ArrayList<Tweet> tweets;
	
	public HeatBin(HeatMap p){
		tweets = new ArrayList<Tweet>();
		parent = p;
	}
	
	public int getSize(){
		return tweets.size();
	}

	public HeatBin addTweet(Tweet tweet) {
		tweets.add(tweet);
		return this;
	}
}
