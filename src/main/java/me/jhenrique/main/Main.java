package me.jhenrique.main;

import me.jhenrique.manager.TweetManager;
import me.jhenrique.manager.TwitterCriteria;
import me.jhenrique.model.Tweet;

import java.util.List;

public class Main {
	private static final String USERNAME = "Username: ";
	private static final String RETWEETS = "Retweets: ";
	private static final String TEXT = "Text: ";
	private static final String MENTIONS = "Mentions: ";
	private static final String HASHTAGS = "Hashtags: ";

	public static void main(String[] args) {
		/**
		 * Reusable objects
		 */
		TwitterCriteria criteria = null;
		Tweet t = null;
		List<Tweet> tweetList = null;
		
		/**
		 *  Example 1 - Get tweets by username
		 **/
		
		criteria = TwitterCriteria.create()
				.setUsername("barackobama")
				.setMaxTweets(1);
		t = TweetManager.getTweets(criteria).get(0);
		
		System.out.println("### Example 1 - Get tweets by username [barackobama]");
		System.out.println(USERNAME + t.getUsername());
		System.out.println(RETWEETS + t.getRetweets());
		System.out.println(TEXT + t.getText());
		System.out.println(MENTIONS + t.getMentions());
		System.out.println(HASHTAGS + t.getHashtags());
		System.out.println();
		
		/**
		 *  Example 2 - Get tweets by query search
		 **/
		criteria = TwitterCriteria.create()
				.setQuerySearch("europe refugees")
				.setSince("2015-05-01")
				.setUntil("2015-09-30")
				.setMaxTweets(1);
		t = TweetManager.getTweets(criteria).get(0);
		
		System.out.println("### Example 2 - Get tweets by query search [europe refugees]");
		System.out.println(USERNAME + t.getUsername());
		System.out.println(RETWEETS + t.getRetweets());
		System.out.println(TEXT + t.getText());
		System.out.println(MENTIONS + t.getMentions());
		System.out.println(HASHTAGS + t.getHashtags());
		System.out.println();
		
		/**
		 *  Example 3 - Get tweets by username and bound dates
		 **/
		criteria = TwitterCriteria.create()
				.setUsername("barackobama")
				.setSince("2015-09-10")
				.setUntil("2015-09-12")
				.setMaxTweets(1);
		t = TweetManager.getTweets(criteria).get(0);
		
		System.out.println("### Example 3 - Get tweets by username and bound dates [barackobama, '2015-09-10', '2015-09-12']");
		System.out.println(USERNAME + t.getUsername());
		System.out.println(RETWEETS + t.getRetweets());
		System.out.println(TEXT + t.getText());
		System.out.println(MENTIONS + t.getMentions());
		System.out.println(HASHTAGS + t.getHashtags());
		System.out.println();


		/**
		 *  Example 4 - Get all possible tweets by username (timeline tweets + advanced search tweets)
		 **/
		System.out.println("### Example 4 - Get all possible tweets by username (timeline tweets + advanced search tweets) [github]");

		System.out.println("Retrieving all tweets from github...");

		criteria = TwitterCriteria.create()
				.setUsername("@github");

		tweetList = TweetManager.getTweets(criteria);

		for (Tweet tweet : tweetList)
			System.out.println("@" + tweet.getUsername() + " : " + tweet.getText() + tweet.getDate());


		System.out.println();
		System.out.println("Printed " + tweetList.size() + " tweets from @github.");

	}
	
}