package me.jhenrique.model;

import java.util.Date;

public class Tweet {
	
	private String username;
	
	private String text;
	
	private Date date;
	
	private int retweets;

	private int favorites;
	
	public Tweet() {
	}

	public Tweet(String username, String text, Date date, int retweets,
			int favorites) {
		this.username = username;
		this.text = text;
		this.date = date;
		this.retweets = retweets;
		this.favorites = favorites;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRetweets() {
		return retweets;
	}

	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}

	public int getFavorites() {
		return favorites;
	}

	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}

}