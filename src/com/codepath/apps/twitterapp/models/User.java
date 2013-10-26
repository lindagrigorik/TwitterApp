package com.codepath.apps.twitterapp.models;

import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="User")
public class User extends Model{
    @Column(name="Name")
    public String name;
    @Column(name="IdStr")
    public String idStr;
    @Column(name="ScreenName")
    public String screenName;
    @Column(name="ProfileImageUrl")
    public String profileImageUrl;
    @Column(name="NumTweets")
    public int numTweets;
    @Column(name="FollowersCount")
    public int followersCount;
    @Column(name="CreatedAt")
    public String createdAt;

    public User(){
	super();
    }
    
    public String getName() {
        return name;
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
	    u.idStr = json.getString("id_str");
	    u.screenName = json.getString("screen_name");
	    u.profileImageUrl = json.getString("profile_image_url");
	    u.numTweets = json.getInt("statuses_count");
	    u.followersCount = json.getInt("friends_count");
	    u.createdAt = json.getString("created_at");
	    u.save();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return u;
    }
    
}
