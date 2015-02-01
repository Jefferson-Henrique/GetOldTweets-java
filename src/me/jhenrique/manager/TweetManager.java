package me.jhenrique.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.jhenrique.model.Tweet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import twitter4j.JSONObject;

/**
 * Class to getting tweets based on username and optional time constraints
 * 
 * @author Jefferson Henrique
 */
public class TweetManager {

	/**
	 * @param from username (without @)
	 * @param since Lower bound date (yyyy-mm-dd)
	 * @param until Upper bound date (yyyy-mm-dd)
	 * @param scrollCursor (Parameter used by Twitter to do pagination of results)
	 * @return JSON response used by Twitter to build its results
	 * @throws Exception
	 */
	private static String getURLResponse(String from, String since, String until, String scrollCursor) throws Exception {
		String appendQuery = "from:"+from;
		if (since != null) {
			appendQuery += " since:"+since;
		}
		if (until != null) {
			appendQuery += " until:"+until;
		}
		
		String url = String.format("https://twitter.com/i/search/timeline?f=realtime&q=%s&src=typd&scroll_cursor=%s", URLEncoder.encode(appendQuery, "UTF-8"), scrollCursor);
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setRequestMethod("GET");
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		return response.toString();
	}
	
	/**
	 * @param username (without @)
	 * @param since Lower bound date (yyyy-mm-dd)
	 * @param until Upper bound date (yyyy-mm-dd)
	 * @return A list of all tweets found
	 */
	public static List<Tweet> getTweets(String username, String since, String until) {
		List<Tweet> results = new ArrayList<Tweet>();
		
		try {
			String refreshCursor = null;
			while (true) {
				JSONObject json = new JSONObject(getURLResponse(username, since, until, refreshCursor));
				refreshCursor = json.getString("scroll_cursor");
				Document doc = Jsoup.parse((String) json.get("items_html"));
				Elements tweets = doc.select("div.js-stream-tweet");
				
				if (tweets.size() == 0) {
					break;
				}
			
				for (Element tweet : tweets) {
					String txt = tweet.select("p.js-tweet-text").text().replaceAll("[^\\u0000-\\uFFFF]", "");
					int retweets = Integer.valueOf(tweet.select("span.ProfileTweet-action--retweet span.ProfileTweet-actionCount").attr("data-tweet-stat-count").replaceAll(",", ""));
					int favorites = Integer.valueOf(tweet.select("span.ProfileTweet-action--favorite span.ProfileTweet-actionCount").attr("data-tweet-stat-count").replaceAll(",", ""));
					long dateMs = Long.valueOf(tweet.select("small.time span.js-short-timestamp").attr("data-time-ms"));
					Date date = new Date(dateMs);
					
					Tweet t = new Tweet(username, txt, date, retweets, favorites);
					results.add(t);
				}
			}
		} catch (Exception e) {
		}
		
		return results;
	}
	
	
}