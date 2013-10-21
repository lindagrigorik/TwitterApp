package com.codepath.apps.twitterapp.models;

import org.json.JSONObject;

public class User{
    
    private String name;
    private long id;
    private String idStr;
    private String screenName;
    private String profileImageUrl;
    private int numTweets;
    private int followersCount;
    private String createdAt;

    
    public String getName() {
        return name;
    }


    public long getId() {
        return id;
    }


    public String getIdStr() {
        return idStr;
    }


    public String getScreenName() {
        return screenName;
    }


    public String getProfileImageUrl() {
        return profileImageUrl;
    }


    public int getNumTweets() {
        return numTweets;
    }


    public int getFollowersCount() {
        return followersCount;
    }


    public String getCreatedAt() {
        return createdAt;
    }


    public static User fromJson(JSONObject json) {
	User u = new User();
	
	try {
	    u.name = json.getString("name");
	    u.id = json.getLong("id");
	    u.idStr = json.getString("id_str");
	    u.screenName = json.getString("screen_name");
	    u.profileImageUrl = json.getString("profile_image_url");
	    u.numTweets = json.getInt("statuses_count");
	    u.followersCount = json.getInt("friends_count");
	    u.createdAt = json.getString("created_at");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return u;
    }
    
}
