package com.codepath.apps.twitterapp.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name="Tweet")
public class Tweet extends Model{

    @Column(name="User")
    public User user;
    @Column(name="Body")
    public String body;
    @Column(name="IdStr")
    public String idStr;
    @Column(name="IsFavorited")
    public boolean isFavorited;
    @Column(name="CreatedAt")
    public String createdAt;
    
    public String getCreatedAt(){
	return createdAt;
    }
    
    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public String getIdStr() {
        return idStr;
    }
    
    public boolean isFavorited() {
        return isFavorited;
    }

    public static Tweet fromJson(JSONObject json){
	Tweet tweet = new Tweet();
	
	try {
	    tweet.body = json.getString("text");
	    tweet.idStr = json.getString("id_str");
	    tweet.isFavorited = json.getBoolean("favorited");
	    tweet.createdAt = json.getString("created_at");
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
		tweet.save();
	    }
	}
	return tweets;
    }
    
    public static List<Tweet> getMostRecent(){
	return new Select().from(Tweet.class)
			.orderBy("CreatedAt ASC").execute();
    }
}
