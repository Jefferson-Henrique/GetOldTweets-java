package me.jhenrique.main;

import me.jhenrique.manager.TweetManager;
import me.jhenrique.model.Tweet;

public class Main {
	public static void main(String[] args) {
		Tweet t = TweetManager.getTweets("jimmyfallon", "2015-01-01", "2015-01-02").get(0);
		System.out.println(t.getUsername());
		System.out.println(t.getRetweets());
		System.out.println(t.getText());
	}
}