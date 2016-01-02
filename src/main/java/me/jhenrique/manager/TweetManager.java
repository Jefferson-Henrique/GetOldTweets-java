package me.jhenrique.manager;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.jhenrique.model.Tweet;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class to getting tweets based on username and optional time constraints
 * 
 * @author Jefferson Henrique
 */
public class TweetManager {
	
	private static final HttpClient defaultHttpClient = HttpClients.createDefault();
	
	static {
		Logger.getLogger("org.apache.http").setLevel(Level.OFF);
	}

	/**
	 * @param username A specific username (without @)
	 * @param since Lower bound date (yyyy-mm-dd)
	 * @param until Upper bound date (yyyy-mm-dd)
	 * @param scrollCursor (Parameter used by Twitter to do pagination of results)
	 * @return JSON response used by Twitter to build its results
	 * @throws Exception
	 */
	private static String getURLResponse(String username, String since, String until, String querySearch, String scrollCursor) throws Exception {
		String appendQuery = "";
		if (username != null) {
			appendQuery += "from:"+username;
		}
		if (since != null) {
			appendQuery += " since:"+since;
		}
		if (until != null) {
			appendQuery += " until:"+until;
		}
		if (querySearch != null) {
			appendQuery += " "+querySearch;
		}
		
		String url = String.format("https://twitter.com/i/search/timeline?f=realtime&q=%s&src=typd&max_position=%s", URLEncoder.encode(appendQuery, "UTF-8"), scrollCursor);
		
		HttpGet httpGet = new HttpGet(url);
		HttpEntity resp = defaultHttpClient.execute(httpGet).getEntity();
		
		return EntityUtils.toString(resp);
	}
	
	/**
	 * @param criteria An object of the class {@link TwitterCriteria} to indicate how tweets must be searched
	 * 
	 * @return A list of all tweets found
	 */
	public static List<Tweet> getTweets(TwitterCriteria criteria) {
		List<Tweet> results = new ArrayList<Tweet>();
		
		try {
			String refreshCursor = null;
			outerLace: while (true) {
				JSONObject json = new JSONObject(getURLResponse(criteria.getUsername(), criteria.getSince(), criteria.getUntil(), criteria.getQuerySearch(), refreshCursor));
				refreshCursor = json.getString("min_position");
				Document doc = Jsoup.parse((String) json.get("items_html"));
				Elements tweets = doc.select("div.js-stream-tweet");
				
				if (tweets.size() == 0) {
					break;
				}
			
				for (Element tweet : tweets) {
					String usernameTweet = tweet.select("span.username.js-action-profile-name b").text();
					String txt = tweet.select("p.js-tweet-text").text().replaceAll("[^\\u0000-\\uFFFF]", "");
					int retweets = Integer.valueOf(tweet.select("span.ProfileTweet-action--retweet span.ProfileTweet-actionCount").attr("data-tweet-stat-count").replaceAll(",", ""));
					int favorites = Integer.valueOf(tweet.select("span.ProfileTweet-action--favorite span.ProfileTweet-actionCount").attr("data-tweet-stat-count").replaceAll(",", ""));
					long dateMs = Long.valueOf(tweet.select("small.time span.js-short-timestamp").attr("data-time-ms"));
					String id = tweet.attr("data-tweet-id");
					String permalink = tweet.attr("data-permalink-path");
					
					String geo = "";
					Elements geoElement = tweet.select("span.Tweet-geo");
					if (geoElement.size() > 0) {
						geo = geoElement.attr("title");
					}

					Date date = new Date(dateMs);
					
					Tweet t = new Tweet();
					t.setId(id);
					t.setPermalink("https://twitter.com"+permalink);
					t.setUsername(usernameTweet);
					t.setText(txt);
					t.setDate(date);
					t.setRetweets(retweets);
					t.setFavorites(favorites);
					t.setMentions(processTerms("(@\\w*)", txt));
					t.setHashtags(processTerms("(#\\w*)", txt));
					t.setGeo(geo);
					
					results.add(t);
					
					if (criteria.getMaxTweets() > 0 && results.size() >= criteria.getMaxTweets()) {
						break outerLace;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	private static String processTerms(String patternS, String tweetText) {
		StringBuilder sb = new StringBuilder();
		Matcher matcher = Pattern.compile(patternS).matcher(tweetText);
		while (matcher.find()) {
			sb.append(matcher.group());
			sb.append(" ");
		}
		
		return sb.toString().trim();
	}
	
}