package com.codepath.apps.twitterapp.models;

import org.json.JSONObject;

public class User extends BaseModel {
    public String getName() {
	return getString("name");
    }
    
    public long getId() {
	return getLong("id");
    }
    
    public String getScreenName() {
	return getString("screen_name");
    }
    
    public String getProfileImageUrl(){
	return getString("profile_image_url");
    }
    
    public int getNumTweets(){
	return getInt("statuses_count");
    }
    
    public int getFollowersCount() {
	return getInt("friends_count");
    }
    
    public String getCreatedAt() {
	return getCreatedAt("created_at");
    }
    
    public static User fromJson(JSONObject json) {
	User u = new User();
	
	try {
	    u.jsonObject = json;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return u;
    }
    
}
