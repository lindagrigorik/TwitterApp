package com.codepath.apps.twitterapp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet{

    private User user;
    private String body;
    private String idStr;
    private long id;
    private boolean isFavorited;
    
    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public String getIdStr() {
        return idStr;
    }

    public long getId() {
        return id;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public static Tweet fromJson(JSONObject json){
	Tweet tweet = new Tweet();
	
	try {
	    tweet.body = json.getString("text");
	    tweet.idStr = json.getString("id_str");
	    tweet.id = json.getLong("id");
	    tweet.isFavorited = json.getBoolean("favorited");
	    tweet.user = User.fromJson(json.getJSONObject("user"));
	} catch (JSONException e) {
	    e.printStackTrace();
	    return null;
	}
	return tweet;
    }
    
    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
	ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
	for (int i=0; i<jsonArray.length(); i++){
	    JSONObject tweetJson =  null;
	    try{
		tweetJson = jsonArray.getJSONObject(i);
	    }catch (Exception e) {
		e.printStackTrace();
		continue;
	    }
	    
	    Tweet tweet = Tweet.fromJson(tweetJson);
	    if (tweet != null) {
		tweets.add(tweet);
	    }
	}
	return tweets;
	
    }
    
}
