package me.jhenrique.manager;

/**
 * A class to guide how the tweets must be searched on {@link TweetManager}
 * 
 * @author Jefferson Henrique
 *
 */
public class TwitterCriteria {
	
	private String username;
	
	private String since;
	
	private String until;
	
	private String querySearch;
	
	private int maxTweets;
	
	private TwitterCriteria() {
	}

	public static TwitterCriteria create() {
		return new TwitterCriteria();
	}

	/**
	 * @param username (whitout @) Username of a specific twitter account
	 * 
	 * @return Current {@link TwitterCriteria}
	 */
	public TwitterCriteria setUsername(String username) {
		this.username = username;
		return this;
	}

	/**
	 * @param since The lower bound date (yyyy-mm-aa)
	 * 
	 * @return Current {@link TwitterCriteria}
	 */
	public TwitterCriteria setSince(String since) {
		this.since = since;
		return this;
	}

	/**
	 * @param until The upper bound date (yyyy-mm-aa)
	 * 
	 * @return Current {@link TwitterCriteria}
	 */
	public TwitterCriteria setUntil(String until) {
		this.until = until;
		return this;
	}
	
	/**
	 * @param querySearch A query text to be matched
	 * 
	 * @return Current TwitterCriteria
	 */
	public TwitterCriteria setQuerySearch(String querySearch) {
		this.querySearch = querySearch;
		return this;
	}

	/**
	 * @param maxTweets The maximum number of tweets to retrieve
	 * 
	 * @return Current {@link TwitterCriteria}
	 */
	public TwitterCriteria setMaxTweets(int maxTweets) {
		this.maxTweets = maxTweets;
		return this;
	}

	String getUsername() {
		return username;
	}

	String getSince() {
		return since;
	}

	String getUntil() {
		return until;
	}
	
	String getQuerySearch() {
		return querySearch;
	}

	int getMaxTweets() {
		return maxTweets;
	}
	
}