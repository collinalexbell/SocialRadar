package twitter;

import data.HeatMap;
import data.Tweet;
import twitter4j.conf.*;
import twitter4j.internal.async.*;
import twitter4j.internal.org.json.*;
import twitter4j.internal.logging.*;
import twitter4j.api.*;
import twitter4j.util.*;
import twitter4j.internal.http.*;
import twitter4j.*;

public class Connection {
	
	TwitterStream twitterStream;
	TwitterStream heatMapStream;
	FilterQuery heatMapFilter;
	HeatMap heatMap;
	static String OAuthConsumerKey = "5ZgQXBGYo4YSKLYxqM1XEA";
	static String OAuthConsumerSecret = "E9nSuU2uJ2IAz0YavqdfT4fIJAhMcXC4gJkD94qVAs";
	static String AccessToken  = "426783140-ffLY7B4oRWwjNil6lXDdGtfKLxC5wg1hWtuYJNfy";
    static String AccessTokenSecret = "xso5cpcuuU9FA4mQEm9OBzDhx83p0DMMZDJiaXuWTBM";
    
	static String HMOAuthConsumerKey = "oPdD4Cy86QEB6fqOcoHLg";
	static String HMOAuthConsumerSecret = "t1motuuDz6VLDTPJIrh4Tl0ShuNwxhVWRdLi0go";
	static String HMAccessToken  = "1413076735-xVLFflaLmcsYqAJZX34neUnmP2zxQGFofbK8Q3k";
    static String HMAccessTokenSecret = "kNiUFCxREtL1aQ6hPuG5i5i3uRPlIgzEQiTxBp4E";

    public Connection(HeatMap h){
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	ConfigurationBuilder hm = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(OAuthConsumerKey);
		cb.setOAuthConsumerSecret(OAuthConsumerSecret);
		cb.setOAuthAccessToken(AccessToken);
		cb.setOAuthAccessTokenSecret(AccessTokenSecret);
		
		hm.setDebugEnabled(true);
		hm.setOAuthConsumerKey(HMOAuthConsumerKey);
		hm.setOAuthConsumerSecret(HMOAuthConsumerSecret);
		hm.setOAuthAccessToken(HMAccessToken);
		hm.setOAuthAccessTokenSecret(HMAccessTokenSecret);
		
		heatMap = h;
		
		
		
		
		
		
		
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		heatMapStream = new TwitterStreamFactory(hm.build()).getInstance();
		
		
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				//                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				//                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				//                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}

		};
		
		StatusListener heatMapListener = new StatusListener() {
			public void onStatus(Status status) {
				GeoLocation location = status.getGeoLocation();
				if (location != null){
					heatMap.addTweet(new Tweet(new de.fhpotsdam.unfolding.geo.Location(location.getLatitude(),location.getLongitude()),status.getText()));
				}
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				//                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				//                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				//                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}

		};

		twitterStream.addListener(listener);
		heatMapStream.addListener(heatMapListener);
		
		double[][] world ={{-180, -90}, {180, 90}};
		heatMapFilter = new FilterQuery();
		heatMapFilter.locations(world);
		
		heatMapStream.filter(heatMapFilter);
		

    }
}
